package com.nourish1709.project3_main_service.services;

import com.nourish1709.project3_main_service.daos.CategoryRepository;
import com.nourish1709.project3_main_service.exceptions.*;
import com.nourish1709.project3_main_service.models.Account;
import com.nourish1709.project3_main_service.models.Category;
import lombok.AllArgsConstructor;
import org.hibernate.PropertyValueException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService implements CrudInterface<Category> {
    private final CategoryRepository categoryRepository;
    private final AccountService accountService;

    @Override
    public Category create(final Category category) {
        checkCategory(category);
        categoryRepository.save(category);
        return getById(category.getId());
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getById(final Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);
    }

    @Override
    public Category update(final Long id, final Category category) {
        category.setId(id);

        getById(category.getId());

        return create(category);
    }

    @Override
    public void delete(final Long id) {
        Category category = getById(id);
        categoryRepository.delete(category);
    }

    private void checkCategory(final Category category) {
        try {
            String name = checkName(category.getName());
            category.setName(name);
        } catch (PropertyValueException | NullPointerException exception) {
            throw new BadCredentialsException("The name is null!");
        }

        try {
            String description = category.getDescription();
            checkDescription(description);
            category.setDescription(description);
        } catch (PropertyValueException | NullPointerException exception) {
            throw new BadCredentialsException("The description is null!");
        }

        try {
            String image = category.getImage();
            checkImage(image);
            category.setImage(image);
        } catch (PropertyValueException | NullPointerException exception) {
            throw new BadCredentialsException("The image is null!");
        }

        try {
            Account account = category.getAccount();
            accountService.getById(account.getId());
        } catch (PropertyValueException | NullPointerException exception) {
            throw new BadCredentialsException("The account is null!");
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
}
