package com.nourish1709.project3_main_service.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nourish1709.project3_main_service.daos.CategoryRepository;
import com.nourish1709.project3_main_service.models.dto.CategoryDto;
import com.nourish1709.project3_main_service.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@WebAppConfiguration
public class CategoryControllerTest {

    @Autowired
    protected WebApplicationContext context;
    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryRepository categoryRepository;

    protected MockMvc mockMvc;

    protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void shouldGetAllCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/categories"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Sql(scripts = {"/account.sql"}, config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void addCategory() throws Exception {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("smartphone");
        categoryDto.setImage("phone.jpeg");
        categoryDto.setDescription("this category have only new phones, from brands as Apple, Samsung");
        categoryDto.setAccountId(12L);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(categoryDto));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
    }

    @Test
    @Sql(scripts = {"/categoryGetById.sql"}, config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void shouldGetCategoryById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/categories/2"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Sql(scripts = {"/deleteCategory.sql"}, config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void shouldDeleteCategoryById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/categories/14"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Sql(scripts = {"/updateCategory.sql"}, config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void updateCategory() throws Exception {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("smartphone");
        categoryDto.setImage("phone.jpeg");
        categoryDto.setDescription("this category have only new phones, from brands as Apple, Samsung");
        categoryDto.setAccountId(56L);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/categories/57/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(categoryDto));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }
}
