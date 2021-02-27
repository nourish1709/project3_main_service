package com.nourish1709.project3_main_service.services;

import com.nourish1709.project3_main_service.daos.AccountRepository;
import com.nourish1709.project3_main_service.daos.CategoryRepository;
import com.nourish1709.project3_main_service.daos.SkuRepository;
import com.nourish1709.project3_main_service.exceptions.InvalidSkuIdException;
import com.nourish1709.project3_main_service.exceptions.InvalidSkuDataException;
import com.nourish1709.project3_main_service.models.Account;
import com.nourish1709.project3_main_service.models.Category;
import com.nourish1709.project3_main_service.models.Sku;
import com.nourish1709.project3_main_service.models.dto.AccountDto;
import com.nourish1709.project3_main_service.models.dto.CategoryDto;
import com.nourish1709.project3_main_service.models.dto.SkuDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkuService implements CrudInterface<SkuDto> {

    private final SkuRepository skuRepository;
    private final CategoryRepository categoryRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public SkuService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        this.modelMapper.typeMap(
                Category.class, Long.class)
                .addMapping(Category::getId,
                        ((categoryDto, o) -> )
                );

        this.modelMapper.typeMap(
                Account.class, AccountDto.class)
                .addMapping(Account::getId,
                        (accountDto, o) -> accountDto.set((Long) o)
                );

        this.modelMapper.typeMap(
                CategoryDto.class, Category.class)
                .addMapping(
                        categoryDto -> this.categoryRepository.findById(categoryDto.getAccountId())
                                .orElseThrow(new InvalidSkuDataException("No category was found")),
                        (category, o) -> new Category((Category) o);
                );
    }

    @Override
    public SkuDto create(SkuDto skuDto) {
        Sku sku = convertToEntity(skuDto);
        checkSku(sku);
        sku = skuRepository.save(sku);
        return convertToDto(sku);
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
        Sku sku = convertToEntity(skuDto);
        checkSku(sku);

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

    private void checkSku(Sku sku) {
        if (sku.getName().isBlank() || sku.getName().length() > 50
                || sku.getName().length() < 2)
            throw new InvalidSkuDataException("Sku's name is invalid");
        else if (sku.getPrice().compareTo(BigDecimal.valueOf(0)) < 0)
            throw new InvalidSkuDataException("Sku's price cannot be less then 0");
        else if (sku.getCategory() == null)
            throw new InvalidSkuDataException("No category was chosen");
        else if (sku.getAccount() == null)
            throw new InvalidSkuDataException("No account was chosen");
    }

    public Sku convertToEntity(SkuDto skuDto) {
        Sku sku = modelMapper.map(skuDto, Sku.class);

        Category category = categoryRepository.findById(
                skuDto.getCategoryId())
                .orElseThrow(() -> new InvalidSkuDataException("No category by provided id was found"));

        Account account = accountRepository.findById(
                skuDto.getAccountId())
                .orElseThrow(() -> new InvalidSkuDataException("No account by provided id was found"));

        sku.setCategory(category);
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
