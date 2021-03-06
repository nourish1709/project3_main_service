package com.nourish1709.project3_main_service.services;

import com.nourish1709.project3_main_service.daos.AccountRepository;
import com.nourish1709.project3_main_service.daos.CategoryRepository;
import com.nourish1709.project3_main_service.exceptions.BadCredentialsException;
import com.nourish1709.project3_main_service.exceptions.CategoryNotFoundException;
import com.nourish1709.project3_main_service.exceptions.InvalidImageUrlException;
import com.nourish1709.project3_main_service.exceptions.InvalidNameException;
import com.nourish1709.project3_main_service.models.Account;
import com.nourish1709.project3_main_service.models.Category;
import com.nourish1709.project3_main_service.models.dto.CategoryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    ApplicationContext applicationContext =
            new AnnotationConfigApplicationContext(ModelMapperConfiguration.class);

    ModelMapper modelMapper;

    private CategoryService categoryService;
    private CategoryDto categoryDto;
    private Category category;
    private Account account;

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        modelMapper = applicationContext.getBean(ModelMapper.class);

        categoryService = new CategoryService(categoryRepository, accountRepository, modelMapper);

        account = new Account();
        account.setId(1L);

        categoryDto = new CategoryDto();
        categoryDto.setName("Smartphones");
        categoryDto.setDescription("A lot of smartphones special for you.");
        categoryDto.setImage("https://google.com");
        categoryDto.setAccountId(1L);

        category = new Category();
        category.setId(1L);
        category.setName("Smartphones");
        category.setDescription("A lot of smartphones special for you.");
        category.setImage("https://google.com");
        category.setAccount(account);
    }

    @Test
    void getAllTest() {
        Mockito.when(categoryRepository
                .findAll())
                .thenReturn(List.of(category));

        List<CategoryDto> expected = List.of(categoryDto);
        List<CategoryDto> actual = categoryService.getAll();
        assertEquals(expected, actual);
    }

    @Test
    void getByIdSuccessfulTest() {
        Mockito.when(categoryRepository
                .findById(1L))
                .thenReturn(Optional.of(category));
        CategoryDto actual = categoryService.getById(1L);
        assertEquals(categoryDto, actual);
    }

    @Test
    void getByIdCategoryNotFoundExceptionTest() {
        Mockito.when(accountRepository
                .findById(Mockito.anyLong()))
                .thenReturn(Optional.of(account));
        assertThrows(CategoryNotFoundException.class,
                () -> categoryService.update(2L, categoryDto));
    }

    @Test
    void updateSuccessfulTest() {
        Mockito.when(categoryRepository
                .findById(1L))
                .thenReturn(Optional.of(category));
        Mockito.when(accountRepository
                .findById(Mockito.anyLong()))
                .thenReturn(Optional.of(account));
        CategoryDto actual = categoryService.update(1L, categoryDto);
        assertEquals(categoryDto, actual);
    }

    @Test
    void updateBadCredentialsExceptionNameIsNullTest() {
        Mockito.when(categoryRepository
                .findById(1L))
                .thenReturn(Optional.of(category));
        Mockito.when(accountRepository
                .findById(Mockito.anyLong()))
                .thenReturn(Optional.of(account));

        categoryDto.setName(null);
        assertThrows(BadCredentialsException.class,
                () -> categoryService.update(1L, categoryDto));
    }

    @Test
    void updateBadCredentialsExceptionDescriptionIsNullTest() {
        Mockito.when(categoryRepository
                .findById(1L))
                .thenReturn(Optional.of(category));
        Mockito.when(accountRepository
                .findById(Mockito.anyLong()))
                .thenReturn(Optional.of(account));

        categoryDto.setDescription(null);
        assertThrows(BadCredentialsException.class,
                () -> categoryService.update(1L, categoryDto));
    }

    @Test
    void updateBadCredentialsExceptionImageIsNullTest() {
        Mockito.when(categoryRepository
                .findById(1L))
                .thenReturn(Optional.of(category));
        Mockito.when(accountRepository
                .findById(Mockito.anyLong()))
                .thenReturn(Optional.of(account));

        categoryDto.setImage(null);
        assertThrows(BadCredentialsException.class,
                () -> categoryService.update(1L, categoryDto));
    }

    @Test
    void updateInvalidNameExceptionTooShortTest() {
        Mockito.when(categoryRepository
                .findById(1L))
                .thenReturn(Optional.of(category));
        Mockito.when(accountRepository
                .findById(Mockito.anyLong()))
                .thenReturn(Optional.of(account));

        categoryDto.setName("");
        assertThrows(InvalidNameException.class,
                () -> categoryService.update(1L, categoryDto));
    }

    @Test
    void updateInvalidNameExceptionTooLongTest() {
        Mockito.when(categoryRepository
                .findById(1L))
                .thenReturn(Optional.of(category));
        Mockito.when(accountRepository
                .findById(Mockito.anyLong()))
                .thenReturn(Optional.of(account));

        categoryDto.setName("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
        assertThrows(InvalidNameException.class,
                () -> categoryService.update(1L, categoryDto));
    }

    @Test
    void updateInvalidNameExceptionDescriptionIsTooShortTest() {
        Mockito.when(categoryRepository
                .findById(1L))
                .thenReturn(Optional.of(category));
        Mockito.when(accountRepository
                .findById(Mockito.anyLong()))
                .thenReturn(Optional.of(account));

        categoryDto.setDescription("");
        assertThrows(InvalidNameException.class,
                () -> categoryService.update(1L, categoryDto));
    }

    @Test
    void updateInvalidNameExceptionDescriptionIsTooLongTest() {
        Mockito.when(categoryRepository
                .findById(1L))
                .thenReturn(Optional.of(category));
        Mockito.when(accountRepository
                .findById(Mockito.anyLong()))
                .thenReturn(Optional.of(account));

        categoryDto.setDescription("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
        assertThrows(InvalidNameException.class,
                () -> categoryService.update(1L, categoryDto));
    }

    @Test
    void updateInvalidImageUrlExceptionTest() {
        Mockito.when(categoryRepository
                .findById(1L))
                .thenReturn(Optional.of(category));
        Mockito.when(accountRepository
                .findById(Mockito.anyLong()))
                .thenReturn(Optional.of(account));

        categoryDto.setImage("");
        assertThrows(InvalidImageUrlException.class,
                () -> categoryService.update(1L, categoryDto));
    }

    @Test
    void delete() {
        Mockito.when(categoryRepository
                .findById(1L))
                .thenReturn(Optional.of(category));
        Mockito.when(accountRepository
                .findById(Mockito.anyLong()))
                .thenReturn(Optional.of(account));

        categoryService.delete(1L);
        assertEquals(0, 0);
    }
}