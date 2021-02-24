package com.nourish1709.project3_main_service.services;

import com.nourish1709.project3_main_service.daos.AccountRepository;
import com.nourish1709.project3_main_service.exceptions.*;
import com.nourish1709.project3_main_service.models.Account;
import lombok.AllArgsConstructor;
import org.hibernate.PropertyValueException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountService implements AccountInterface {
    private final AccountRepository accountRepository;

    @Override
    public Account update(Account account) throws
            AccountNotFoundException,
            BadCredentialsException,
            InvalidNameException,
            InvalidPhoneException,
            InvalidAgeException,
            PhoneNumberIsAlreadyTakenException {
        checkAccount(account);

        accountRepository.save(account);

        return account;
    }

    @Override
    public void setNotifications(Long id, boolean enabledNotifications)
            throws AccountNotFoundException {
        Account account = getById(id);

        account.setEnabledNotifications(enabledNotifications);

        accountRepository.save(account);
    }

    @Override
    public Account getById(Long id) throws AccountNotFoundException {
        return accountRepository.findById(id)
                .orElseThrow(AccountNotFoundException::new);
    }

    private void checkAccount(Account account)
            throws InvalidNameException,
            InvalidAgeException,
            InvalidPhoneException,
            BadCredentialsException,
            PhoneNumberIsAlreadyTakenException,
            AccountNotFoundException {

        getById(account.getId());

        try {
            String fName = checkName(account.getFirstName(), "The first name");
            account.setFirstName(fName);
        } catch (PropertyValueException exception) {
            throw new BadCredentialsException("The first name is null!");
        }

        try {
            String lName = checkName(account.getLastName(), "The last name");
            account.setLastName(lName);
        } catch (PropertyValueException exception) {
            throw new BadCredentialsException("The last name is null!");
        }

        try {
            checkAge(account.getAge());
        } catch (PropertyValueException exception) {
            throw new BadCredentialsException("Age is null!");
        }

        try {
            checkPhone(account.getPhone());
        } catch (PropertyValueException exception) {
            throw new BadCredentialsException("The phone number is null!");
        }
    }

    private String checkName(final String name, final String message)
            throws InvalidNameException {
        String trimmedName = name.trim();
        if(trimmedName.length() < 2)
            throw new InvalidNameException(message + " is too short!");
        else if(trimmedName.length() > 40)
            throw new InvalidNameException(message + " is too long!");
        return trimmedName;
    }

    private void checkAge(final int age) throws InvalidAgeException {
        if (age < 14 || age > 130)
            throw new InvalidAgeException("Age is not correct!");
    }

    private void checkPhone(final String phone) throws InvalidPhoneException,
            PhoneNumberIsAlreadyTakenException {
        if (accountRepository.findAccountByPhoneIsContaining(phone) == null) {
            throw new PhoneNumberIsAlreadyTakenException("Phone number is already registered in the system!");
        }
        String trimmedPhone = phone.trim();
        if (!trimmedPhone.matches("\\+?[0-9]{10,12}+") ||
                (trimmedPhone.length() < 10 || trimmedPhone.length() > 13)) {
            throw new InvalidPhoneException("Mobile phone number is not correct!");
        }
    }
}
