package com.nourish1709.project3_main_service.services;

import com.nourish1709.project3_main_service.daos.SkuRepository;
import com.nourish1709.project3_main_service.exceptions.InvalidSkuDataException;
import com.nourish1709.project3_main_service.exceptions.InvalidSkuIdException;
import com.nourish1709.project3_main_service.models.Account;
import com.nourish1709.project3_main_service.models.Category;
import com.nourish1709.project3_main_service.models.Sku;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class SkuServiceTest {

    private SkuService skuService;

    @Mock
    private SkuRepository skuRepository;

    private List<Sku> skus;
    private Sku sku;

    @BeforeAll
    void setUp() {
        sku = new Sku();
        sku.setName("iPhone 12");
        sku.setPrice(BigDecimal.valueOf(699.99));
        sku.setDescription("Original iPhone");
        sku.setAvailability(true);
        sku.setCategory(new Category());
        sku.setAccount(new Account());

        skus = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            skus.add(sku);
        }
    }

    @BeforeEach
    void configure() {
        skuService = new SkuService(skuRepository);
    }

    @Test
    void createSkuSuccessTest() {
        Mockito.when(skuRepository.save(sku)).thenReturn(sku);
        sku.setName("iPhone 12");
        sku.setPrice(BigDecimal.valueOf(699.99));
        sku.setDescription("Original iPhone");
        sku.setAvailability(true);
        sku.setCategory(new Category());
        sku.setAccount(new Account());
        Sku sku = skuService.create(this.sku);
        assertEquals(this.sku, sku);
    }

    @Test
    void createSkuBlankNameExpectExceptionTest() {
        Sku skuTest = this.sku;
        skuTest.setName("\t\n\t\t  \n");
        assertThrows(InvalidSkuDataException.class,
                () -> skuService.create(skuTest));
    }

    @Test
    void createSkuLargeNameExpectExceptionTest() {
        Sku skuTest = this.sku;
        String name = "";

        for (int i = 0; i < 50; i++) {
            name += skuTest.getName();
        }

        skuTest.setName(name);
        assertThrows(InvalidSkuDataException.class,
                () -> skuService.create(skuTest));
    }

    @Test
    void createSkuNegativePriceExpectExceptionTest() {
        Sku sku = this.sku;
        sku.setPrice(BigDecimal.valueOf(-1L));
        assertThrows(InvalidSkuDataException.class,
                () -> skuService.create(sku));
    }

    @Test
    void createSkuNoCategoryExpectExceptionTest() {
        Sku sku = this.sku;
        sku.setCategory(null);
        assertThrows(InvalidSkuDataException.class,
                () -> skuService.create(sku));
    }

    @Test
    void createSkuNoAccountExpectExceptionTest() {
        Sku sku = this.sku;
        sku.setAccount(null);
        assertThrows(InvalidSkuDataException.class,
                () -> skuService.create(sku));
    }

    @Test
    void getAllSuccessTest() {
        Mockito.when(skuRepository.findAll()).thenReturn(skus);
        assertEquals(skuService.getAll(), skus);
    }

    @Test
    void getByIdSuccessTest() {
        Mockito.when(skuRepository.findById(1L)).thenReturn(Optional.of(sku));
        assertEquals(skuService.getById(1L), sku);
    }

    @Test
    void getByIdExpectInvalidSkuIdExceptionTest() {
        assertThrows(InvalidSkuIdException.class,
                () -> skuService.getById(-1L));
    }

    @Test
    void updateSuccessTest() {
        sku.setName("iPhone 12");
        sku.setPrice(BigDecimal.valueOf(699.99));
        sku.setDescription("Original iPhone");
        sku.setAvailability(true);
        sku.setCategory(new Category());
        sku.setAccount(new Account());

        Mockito.when(skuRepository.findById(1L)).thenReturn(Optional.of(sku));
        Sku sku = skuService.update(1L, this.sku);
        assertEquals(this.sku, sku);
    }

    @Test
    void updateExpectInvalidSkuIdExceptionTest() {
        sku.setName("iPhone 12");
        sku.setPrice(BigDecimal.valueOf(699.99));
        sku.setDescription("Original iPhone");
        sku.setAvailability(true);
        sku.setCategory(new Category());
        sku.setAccount(new Account());

        Mockito.when(skuRepository.findById(-1L)).thenThrow(new InvalidSkuIdException(-1L));
        assertThrows(InvalidSkuIdException.class,
                () -> skuService.update(-1L, sku));
    }

    @Test
    void deleteExpectInvalidSkuIdExceptionTest() {
        assertThrows(InvalidSkuIdException.class,
                () -> skuService.delete(-1L));
    }
}
