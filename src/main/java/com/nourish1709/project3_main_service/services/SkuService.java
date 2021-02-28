package com.nourish1709.project3_main_service.services;

import com.nourish1709.project3_main_service.daos.AccountRepository;
import com.nourish1709.project3_main_service.daos.CategoryRepository;
import com.nourish1709.project3_main_service.daos.SkuRepository;
import com.nourish1709.project3_main_service.exceptions.InvalidSkuIdException;
import com.nourish1709.project3_main_service.exceptions.InvalidSkuDataException;
import com.nourish1709.project3_main_service.models.Account;
import com.nourish1709.project3_main_service.models.Category;
import com.nourish1709.project3_main_service.models.Sku;
import com.nourish1709.project3_main_service.models.dto.SkuDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SkuService implements CrudInterface<SkuDto> {

    private final SkuRepository skuRepository;
    private final CategoryRepository categoryRepository;
    private final AccountRepository accountRepository;

    private final ModelMapper modelMapper;

    @Override
    public SkuDto create(SkuDto skuDto) {
        checkSku(skuDto);
        Sku sku = convertToEntity(skuDto);

        return convertToDto(skuRepository.save(sku));
    }

    @Override
    public List<SkuDto> getAll() {
        List<Sku> skus = skuRepository.findAll();

        return skus.stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SkuDto getById(Long id) {
        Sku sku = skuRepository.findById(id)
                .orElseThrow(() -> new InvalidSkuIdException(id));

        return convertToDto(sku);
    }

    @Override
    public SkuDto update(Long id, SkuDto skuDto) {
        checkSku(skuDto);
        Sku sku = convertToEntity(skuDto);

        Sku skuFromDatabase = skuRepository.findById(id)
                .orElseThrow(() -> new InvalidSkuIdException(id));

        Long idOfSkuFromDatabase = skuFromDatabase.getId();
        skuFromDatabase = sku;
        skuFromDatabase.setId(idOfSkuFromDatabase);
        skuRepository.save(skuFromDatabase);
        return convertToDto(skuFromDatabase);
    }

    @Override
    public void delete(Long id) {
        skuRepository.findById(id)
                .orElseThrow(() -> new InvalidSkuIdException(id));

        skuRepository.deleteById(id);
    }

    private void checkSku(SkuDto skuDto) {
        if (skuDto.getName().isBlank() || skuDto.getName().length() > 50
                || skuDto.getName().length() < 2)
            throw new InvalidSkuDataException("Sku's name is invalid");
        else if (skuDto.getPrice().compareTo(BigDecimal.valueOf(0)) < 0)
            throw new InvalidSkuDataException("Sku's price cannot be less then 0");
        else if (skuDto.getCategoryId() == null)
            throw new InvalidSkuDataException("No category was chosen");
        else if (skuDto.getAccountId() == null)
            throw new InvalidSkuDataException("No account was chosen");
    }

    public Sku convertToEntity(SkuDto skuDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Sku sku = modelMapper.map(skuDto, Sku.class);

        Category category = categoryRepository
                .findById(skuDto.getCategoryId())
                .orElseThrow(() -> new InvalidSkuDataException("No category by provided id was found"));
        sku.setCategory(category);

        Account account = accountRepository
                .findById(skuDto.getAccountId())
                .orElseThrow(() -> new InvalidSkuDataException("No account by provided id was found"));
        sku.setAccount(account);

        return sku;
    }

    public SkuDto convertToDto(Sku sku) {
        SkuDto skuDto = modelMapper.map(sku, SkuDto.class);

        skuDto.setCategoryId(sku.getCategory().getId());
        skuDto.setAccountId(sku.getAccount().getId());

        return skuDto;
    }
}
