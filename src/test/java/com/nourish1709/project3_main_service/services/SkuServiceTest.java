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
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class SkuServiceTest {
    ApplicationContext applicationContext =
            new AnnotationConfigApplicationContext(ModelMapperConfiguration.class);

    ModelMapper modelMapper;

    private SkuService skuService;

    @Mock
    private SkuRepository skuRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private AccountRepository accountRepository;

    private SkuDto skuDto;
    private Sku sku;
    private Category category;
    private Account account;

    @BeforeEach
    void setUp() {
        modelMapper = applicationContext.getBean(ModelMapper.class);

        skuService = new SkuService(
                skuRepository,
                categoryRepository,
                accountRepository,
                modelMapper);

        skuDto = new SkuDto();
        skuDto.setName("iPhone 12");
        skuDto.setPrice(BigDecimal.valueOf(699.99));
        skuDto.setDescription("Original iPhone");
        skuDto.setAvailability(true);
        skuDto.setCategoryId(1L);
        skuDto.setAccountId(1L);

        category = new Category();
        category.setId(1L);
        account = new Account();
        account.setId(1L);

        sku = new Sku();
        sku.setName("iPhone 12");
        sku.setPrice(BigDecimal.valueOf(699.99));
        sku.setDescription("Original iPhone");
        sku.setAvailability(true);
        sku.setCategory(category);
        sku.setAccount(account);
    }

    @Test
    void createSkuSuccessTest() {
        Mockito.when(accountRepository
                .findById(Mockito.anyLong()))
                .thenReturn(Optional.of(account));
        Mockito.when(categoryRepository
                .findById(Mockito.anyLong()))
                .thenReturn(Optional.of(category));

        Mockito.when(skuRepository
                .save(sku))
                .thenReturn(sku);

        SkuDto savedDkuDto = skuService.create(this.skuDto);
        assertEquals(skuDto, savedDkuDto);
    }

    @Test
    void createSkuBlankNameExpectExceptionTest() {
        skuDto.setName("\t\n\t\t  \n");
        assertThrows(InvalidSkuDataException.class,
                () -> skuService.create(skuDto));
    }

    @Test
    void createSkuLargeNameExpectExceptionTest() {
        skuDto.setName("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
        assertThrows(InvalidSkuDataException.class,
                () -> skuService.create(skuDto));
    }

    @Test
    void createSkuNegativePriceExpectExceptionTest() {
        skuDto.setPrice(BigDecimal.valueOf(-1L));
        assertThrows(InvalidSkuDataException.class,
                () -> skuService.create(skuDto));
    }

    @Test
    void createSkuNoCategoryExpectExceptionTest() {
        skuDto.setCategoryId(null);
        assertThrows(InvalidSkuDataException.class,
                () -> skuService.create(skuDto));
    }

    @Test
    void createSkuNoAccountExpectExceptionTest() {
        skuDto.setAccountId(null);
        assertThrows(InvalidSkuDataException.class,
                () -> skuService.create(skuDto));
    }

    @Test
    void getAllSuccessTest() {
        Mockito.when(
                skuRepository.findAll())
                .thenReturn(List.of(sku));
        assertEquals(skuService.getAll(), List.of(skuDto));
    }

    @Test
    void getByIdSuccessTest() {
        Mockito.when(skuRepository.findById(1L))
                .thenReturn(Optional.of(sku));

        assertEquals(skuService.getById(1L), skuDto);
    }

    @Test
    void getByIdExpectInvalidSkuIdExceptionTest() {
        assertThrows(InvalidSkuIdException.class,
                () -> skuService.getById(-1L));
    }

    @Test
    void updateSuccessTest() {
        Mockito.when(skuRepository
                .findById(1L))
                .thenReturn(Optional.of(sku));
        Mockito.when(accountRepository
                .findById(Mockito.anyLong()))
                .thenReturn(Optional.of(account));
        Mockito.when(categoryRepository
                .findById(Mockito.anyLong()))
                .thenReturn(Optional.of(category));
        Mockito.when(skuRepository
                .save(sku))
                .thenReturn(sku);

        SkuDto savedSku = skuService.update(1L, this.skuDto);
        assertEquals(skuDto, savedSku);
    }

    @Test
    void updateExpectInvalidSkuIdExceptionTest() {
        Mockito.when(categoryRepository
                .findById(Mockito.anyLong()))
                .thenReturn(Optional.of(category));
        Mockito.when(accountRepository
                .findById(Mockito.anyLong()))
                .thenReturn(Optional.of(account));
        assertThrows(InvalidSkuIdException.class,
                () -> skuService.update(-1L, skuDto));
    }

    @Test
    void deleteExpectInvalidSkuIdExceptionTest() {
        assertThrows(InvalidSkuIdException.class,
                () -> skuService.delete(-1L));
    }
}
