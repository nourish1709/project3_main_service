package com.nourish1709.project3_main_service.services;

import com.nourish1709.project3_main_service.daos.SkuRepository;
import com.nourish1709.project3_main_service.exceptions.InvalidSkuIdException;
import com.nourish1709.project3_main_service.exceptions.InvalidSkuDataException;
import com.nourish1709.project3_main_service.models.Sku;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class SkuService implements CrudInterface<Sku> {

    private final SkuRepository skuRepository;

    @Override
    public Sku create(Sku sku) {
        checkSku(sku);
        sku = skuRepository.save(sku);
        return sku;
    }

    @Override
    public List<Sku> getAll() {
        return skuRepository.findAll();
    }

    @Override
    public Sku getById(Long id) {
        return skuRepository.findById(id)
                .orElseThrow(() -> new InvalidSkuIdException(id));
    }

    @Override
    public Sku update(Long id, Sku sku) {
        checkSku(sku);

        Sku skuFromDatabase = skuRepository.findById(id)
                .orElseThrow(() -> new InvalidSkuIdException(id));

        Long idOfSkuFromDatabase = skuFromDatabase.getId();
        skuFromDatabase = sku;
        skuFromDatabase.setId(idOfSkuFromDatabase);
        skuRepository.save(skuFromDatabase);
        return skuFromDatabase;
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
}
