package com.nourish1709.project3_main_service.services;

import com.nourish1709.project3_main_service.daos.AccountRepository;
import com.nourish1709.project3_main_service.daos.CategoryRepository;
import com.nourish1709.project3_main_service.daos.SkuRepository;
import com.nourish1709.project3_main_service.exceptions.InvalidSkuDataException;
import com.nourish1709.project3_main_service.exceptions.InvalidSkuIdException;
import com.nourish1709.project3_main_service.models.Account;
import com.nourish1709.project3_main_service.models.Category;
import com.nourish1709.project3_main_service.models.Sku;
import com.nourish1709.project3_main_service.models.dto.SkuDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class SkuServiceTest {

    private SkuService skuService;

    @Mock
    private SkuRepository skuRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private AccountRepository accountRepository;

    private List<SkuDto> skuDtos;
    private SkuDto skuDto;
    private Sku sku;

    @BeforeEach
    void setUp() {
        skuDto = new SkuDto();
        skuDto.setName("iPhone 12");
        skuDto.setPrice(BigDecimal.valueOf(699.99));
        skuDto.setDescription("Original iPhone");
        skuDto.setAvailability(true);
        skuDto.setCategoryId(1L);
        skuDto.setAccountId(1L);

        skuDtos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            skuDtos.add(skuDto);
        }

        Category category = new Category();
        category.setId(1L);
        Account account = new Account();
        account.setId(1L);

        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        sku = skuService.convertToEntity(skuDto);
    }

    @BeforeEach
    void configure() {
        skuService = new SkuService(
                skuRepository,
                categoryRepository,
                accountRepository,
                new ModelMapper()
        );
//        skuService = new SkuService();
    }

    @Test
    void createSkuSuccessTest() {
        Mockito.when(skuRepository.save(sku)).thenReturn(sku);

        /*skuDto.setName("iPhone 12");
        skuDto.setPrice(BigDecimal.valueOf(699.99));
        skuDto.setDescription("Original iPhone");
        skuDto.setAvailability(true);
        skuDto.setCategory(new Category());
        skuDto.setAccount(new Account());*/

        SkuDto skuDto = skuService.create(this.skuDto);
        assertEquals(this.skuDto, skuDto);
    }

    @Test
    void createSkuBlankNameExpectExceptionTest() {
        Sku skuTest = this.skuDto;
        skuTest.setName("\t\n\t\t  \n");
        assertThrows(InvalidSkuDataException.class,
                () -> skuService.create(skuTest));
    }final

    @Test
    void createSkuLargeNameExpectExceptionTest() {
        Sku skuTest = this.skuDto;
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
        Sku sku = this.skuDto;
        sku.setPrice(BigDecimal.valueOf(-1L));
        assertThrows(InvalidSkuDataException.class,
                () -> skuService.create(sku));
    }

    @Test
    void createSkuNoCategoryExpectExceptionTest() {
        Sku sku = this.skuDto;
        sku.setCategory(null);
        assertThrows(InvalidSkuDataException.class,
                () -> skuService.create(sku));
    }

    @Test
    void createSkuNoAccountExpectExceptionTest() {
        Sku sku = this.skuDto;
        sku.setAccount(null);
        assertThrows(InvalidSkuDataException.class,
                () -> skuService.create(sku));
    }

    @Test
    void getAllSuccessTest() {
        Mockito.when(
                skuRepository.findAll())
                .thenReturn(skuDtos);
        assertEquals(skuService.getAll(), skuDtos);
    }

    @Test
    void getByIdSuccessTest() {
        Mockito.when(skuRepository.findById(1L))
                .thenReturn(Optional.of(skuDto));

        assertEquals(skuService.getById(1L), skuDto);
    }

    @Test
    void getByIdExpectInvalidSkuIdExceptionTest() {
        assertThrows(InvalidSkuIdException.class,
                () -> skuService.getById(-1L));
    }

    @Test
    void updateSuccessTest() {
        skuDto.setName("iPhone 12");
        skuDto.setPrice(BigDecimal.valueOf(699.99));
        skuDto.setDescription("Original iPhone");
        skuDto.setAvailability(true);
        skuDto.setCategory(new Category());
        skuDto.setAccount(new Account());

        Mockito.when(skuRepository.findById(1L)).thenReturn(Optional.of(skuDto));
        Sku sku = skuService.update(1L, this.skuDto);
        assertEquals(this.skuDto, sku);
    }

    @Test
    void updateExpectInvalidSkuIdExceptionTest() {
        skuDto.setName("iPhone 12");
        skuDto.setPrice(BigDecimal.valueOf(699.99));
        skuDto.setDescription("Original iPhone");
        skuDto.setAvailability(true);
        skuDto.setCategory(new Category());
        skuDto.setAccount(new Account());

        Mockito.when(skuRepository.findById(-1L))
                .thenThrow(new InvalidSkuIdException(-1L));

        assertThrows(InvalidSkuIdException.class, () -> skuService.update(-1L, skuDto));
    }

    @Test
    void deleteExpectInvalidSkuIdExceptionTest() {
        assertThrows(InvalidSkuIdException.class,
                () -> skuService.delete(-1L));
    }
}
