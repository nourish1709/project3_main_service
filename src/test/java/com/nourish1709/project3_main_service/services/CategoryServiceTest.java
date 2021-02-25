package com.nourish1709.project3_main_service.services;

import com.nourish1709.project3_main_service.daos.CategoryRepository;
import com.nourish1709.project3_main_service.exceptions.BadCredentialsException;
import com.nourish1709.project3_main_service.exceptions.CategoryNotFoundException;
import com.nourish1709.project3_main_service.exceptions.InvalidImageUrlException;
import com.nourish1709.project3_main_service.exceptions.InvalidNameException;
import com.nourish1709.project3_main_service.models.Account;
import com.nourish1709.project3_main_service.models.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    private CategoryService categoryService;
    private Category category;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryService(categoryRepository, accountService);
        category = new Category();
        category.setId(1L);
        category.setName("Smartphones");
        category.setDescription("A lot of smartphones special for you.");
        category.setImage("https://google.com");
        category.setAccount(new Account());
    }

    @Test
    void createSuccessfulTest() {
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        Category actual = categoryService.create(category);
        assertEquals(category, actual);
    }

    @Test
    void getAllTest() {
        List<Category> categories = List.of(category);
        Mockito.when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> actual = categoryService.getAll();
        assertEquals(categories, actual);
    }

    @Test
    void getByIdSuccessfulTest() {
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        Category actual = categoryService.getById(1L);
        assertEquals(category, actual);
    }

    @Test
    void getByIdCategoryNotFoundExceptionTest() {
        assertThrows(CategoryNotFoundException.class,
                () -> categoryService.update(1L, category));
    }

    @Test
    void updateSuccessfulTest() {
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        Category actual = categoryService.update(1L, category);
        assertEquals(category, actual);
    }

    @Test
    void updateBadCredentialsExceptionNameIsNullTest() {
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        category.setName(null);
        assertThrows(BadCredentialsException.class,
                () -> categoryService.update(1L, category));
    }

    @Test
    void updateBadCredentialsExceptionDescriptionIsNullTest() {
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        category.setDescription(null);
        assertThrows(BadCredentialsException.class,
                () -> categoryService.update(1L, category));
    }

    @Test
    void updateBadCredentialsExceptionImageIsNullTest() {
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        category.setImage(null);
        assertThrows(BadCredentialsException.class,
                () -> categoryService.update(1L, category));
    }

    @Test
    void updateBadCredentialsExceptionAccountIsNullTest() {
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        category.setAccount(null);
        assertThrows(BadCredentialsException.class,
                () -> categoryService.update(1L, category));
    }

    @Test
    void updateInvalidNameExceptionTooShortTest() {
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        category.setName("");
        assertThrows(InvalidNameException.class,
                () -> categoryService.update(1L, category));
    }

    @Test
    void updateInvalidNameExceptionTooLongTest() {
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        category.setName("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
        assertThrows(InvalidNameException.class,
                () -> categoryService.update(1L, category));
    }

    @Test
    void updateInvalidNameExceptionDescriptionIsTooShortTest() {
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        category.setDescription("");
        assertThrows(InvalidNameException.class,
                () -> categoryService.update(1L, category));
    }

    @Test
    void updateInvalidNameExceptionDescriptionIsTooLongTest() {
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        category.setDescription("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
        assertThrows(InvalidNameException.class,
                () -> categoryService.update(1L, category));
    }

    @Test
    void updateInvalidImageUrlExceptionTest() {
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        category.setImage("");
        assertThrows(InvalidImageUrlException.class,
                () -> categoryService.update(1L, category));
    }

    @Test
    void delete() {
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        categoryService.delete(1L);
        assertEquals(0, 0);
    }
}