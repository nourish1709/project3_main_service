package com.nourish1709.project3_main_service.services;

import com.nourish1709.project3_main_service.daos.AccountRepository;
import com.nourish1709.project3_main_service.daos.CategoryRepository;
import com.nourish1709.project3_main_service.exceptions.*;
import com.nourish1709.project3_main_service.models.Account;
import com.nourish1709.project3_main_service.models.Category;
import com.nourish1709.project3_main_service.models.dto.CategoryDto;
import lombok.AllArgsConstructor;
import org.hibernate.PropertyValueException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService implements CrudInterface<CategoryDto> {
    private final CategoryRepository categoryRepository;
    private final AccountRepository accountRepository;

    private final ModelMapper modelMapper;

    @Override
    public CategoryDto create(final CategoryDto categoryDto) {
        checkCategory(categoryDto);

        Category category = convertToEntity(categoryDto);

        categoryRepository.save(category);
        Category savedCategory = categoryRepository
                .findById(category.getId())
                .orElseThrow(CategoryNotFoundException::new);

        return convertToDto(savedCategory);
    }

    @Override
    public List<CategoryDto> getAll() {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categoryRepository.findAll()) {
            categoryDtos.add(convertToDto(category));
        }
        return categoryDtos;
    }

    @Override
    public CategoryDto getById(final Long id) {
        return categoryRepository.findById(id).
                map(this::convertToDto)
                        .orElseThrow(CategoryNotFoundException::new);
    }

    @Override
    public CategoryDto update(final Long id, final CategoryDto categoryDto) {
        Category category = convertToEntity(categoryDto);
        category.setId(id);

        getById(category.getId());
        checkCategory(categoryDto);

        categoryRepository.save(category);
        Category savedCategory = categoryRepository
                .findById(category.getId())
                .orElseThrow(CategoryNotFoundException::new);

        return convertToDto(savedCategory);
    }

    @Override
    public void delete(final Long id) {
        CategoryDto categorydto = getById(id);
        Category category = convertToEntity(categorydto);
        category.setId(id);
        categoryRepository.delete(category);
    }

    private void checkCategory(final CategoryDto categoryDto) {
        try {
            String name = checkName(categoryDto.getName());
            categoryDto.setName(name);
        } catch (PropertyValueException | NullPointerException exception) {
            throw new BadCredentialsException("The name is null!");
        }

        try {
            String description = categoryDto.getDescription();
            checkDescription(description);
            categoryDto.setDescription(description);
        } catch (PropertyValueException | NullPointerException exception) {
            throw new BadCredentialsException("The description is null!");
        }

        try {
            String image = categoryDto.getImage();
            checkImage(image);
            categoryDto.setImage(image);
        } catch (PropertyValueException | NullPointerException exception) {
            throw new BadCredentialsException("The image is null!");
        }
    }

    private String checkName(final String name) {
        String trimmedName = name.trim();
        if (trimmedName.length() < 2)
            throw new InvalidNameException("Name is too short!");
        else if (trimmedName.length() > 30)
            throw new InvalidNameException("Name is too long!");
        return trimmedName;
    }

    private void checkDescription(final String description) {
        if (description.length() < 20) {
            throw new InvalidNameException("The description of the category is too short.");
        } else if (description.length() > 101) {
            throw new InvalidNameException("The description of the category is too short.");
        }

        String[] words = description.split(" ");

        int wordsCounter = words.length;
        int letterCounter;
        char letter;

        for (String word : words) {
            letterCounter = 0;

            for (int i = 0; i < word.length(); i++) {
                letter = word.charAt(i);

                if (Character.isLetter(letter)) {
                    letterCounter++;
                }

                if (letterCounter == 2) {
                    break;
                }
            }

            if (letterCounter < 2) {
                wordsCounter--;
            }
        }

        if (wordsCounter < 2) {
            throw new InvalidNameException("The description of the category was entered incorrectly.");
        }
    }

    private void checkImage(final String photo) {
        String trimmedPhoto = photo.trim();
        if (!trimmedPhoto.matches("^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?" +
                "[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$")) {
            throw new InvalidImageUrlException("The photo url is not correct!");
        }
    }

    public Category convertToEntity(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);

        Account account = accountRepository.findById(
                categoryDto.getAccountId())
                .orElseThrow(AccountNotFoundException::new);

        category.setAccount(account);

        return category;
    }

    public CategoryDto convertToDto(Category category) {
        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);

        categoryDto.setAccountId(category.getAccount().getId());

        return categoryDto;
    }
}
