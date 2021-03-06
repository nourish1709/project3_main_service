package com.nourish1709.project3_main_service.services;

import com.nourish1709.project3_main_service.daos.AccountRepository;
import com.nourish1709.project3_main_service.daos.UserRepository;
import com.nourish1709.project3_main_service.exceptions.*;
import com.nourish1709.project3_main_service.models.Account;
import com.nourish1709.project3_main_service.models.dto.AccountDto;
import com.nourish1709.project3_main_service.models.User;
import lombok.AllArgsConstructor;
import org.hibernate.PropertyValueException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountService implements AccountInterface {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public void init() {
        List<User> users = userRepository.findAll().stream()
                .map(user -> new User(
                        user.getUsername(),
                        user.getEmail(),
                        user.getPassword()
                ))
                .collect(Collectors.toList());

        List<Account> accounts = Arrays.asList(
                new Account(
                        1L,
                        "Oleg",
                        "Kandalov",
                        true,
                        25,
                        "380967528946",
                        users.get(0)
                ),
                new Account(
                        2L,
                        "Roman",
                        "Hryhoriev",
                        true,
                        25,
                        "380967528946",
                        users.get(1)
                ),
                new Account(
                        3L,
                        "Andriiana",
                        "Mazan",
                        true,
                        20,
                        "380967528946",
                        users.get(2)
                ),
                new Account(
                        3L,
                        "Zhenya",
                        "Savonenko",
                        true,
                        20,
                        "380967528946",
                        users.get(3)
                ),
                new Account(
                        4L,
                        "user",
                        "",
                        true,
                        25,
                        "380938492045",
                        users.get(4)
                )
        );

        accountRepository.saveAll(accounts);
    }

    @Override
    public AccountDto update(Long id, AccountDto accountDto) {
        Account account = convertToEntity(accountDto);
        account.setId(id);
        checkAccount(account);

        Account savedAccount = accountRepository.save(account);

        return convertToDto(savedAccount);
    }

    @Override
    public void setNotifications(Long id, boolean enabledNotifications) {
        Account account = accountRepository.findById(id)
                .orElseThrow(AccountNotFoundException::new);

        account.setEnabledNotifications(enabledNotifications);

        accountRepository.save(account);
    }

    @Override
    public AccountDto getById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(AccountNotFoundException::new);
        return convertToDto(account);
    }

    private void checkAccount(Account account) {
        getById(account.getId());

        try {
            String fName = checkName(account.getFirstName(), "The first name");
            account.setFirstName(fName);
        } catch (PropertyValueException | NullPointerException exception) {
            throw new BadCredentialsException("The first name is null!");
        }

        try {
            String lName = checkName(account.getLastName(), "The last name");
            account.setLastName(lName);
        } catch (PropertyValueException | NullPointerException exception) {
            throw new BadCredentialsException("The last name is null!");
        }

        checkAge(account.getAge());

        try {
            checkPhone(account.getPhone());
        } catch (PropertyValueException | NullPointerException exception) {
            throw new BadCredentialsException("The phone number is null!");
        }
    }

    private String checkName(final String name, final String message) {
        String trimmedName = name.trim();
        if (trimmedName.length() < 2)
            throw new InvalidNameException(message + " is too short!");
        else if (trimmedName.length() > 40)
            throw new InvalidNameException(message + " is too long!");
        return trimmedName;
    }

    private void checkAge(final int age) {
        if (age < 14 || age > 130)
            throw new InvalidAgeException("Age is not correct!");
    }

    private void checkPhone(final String phone) {
        if (accountRepository.findAccountByPhoneIsContaining(phone) != null) {
            throw new PhoneNumberIsAlreadyTakenException("Phone number is already registered in the system!");
        }
        String trimmedPhone = phone.trim();
        if (!trimmedPhone.matches("\\+?[0-9]{10,12}+") ||
                (trimmedPhone.length() < 10 || trimmedPhone.length() > 13)) {
            throw new InvalidPhoneException("Mobile phone number is not correct!");
        }
    }

    private Account convertToEntity(AccountDto accountDto) {
        return modelMapper.map(accountDto, Account.class);
    }

    private AccountDto convertToDto(Account account) {
        return modelMapper.map(account, AccountDto.class);
    }
}
