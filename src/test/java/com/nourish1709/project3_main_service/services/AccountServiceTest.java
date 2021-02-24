package com.nourish1709.project3_main_service.services;

import com.nourish1709.project3_main_service.daos.AccountRepository;
import com.nourish1709.project3_main_service.exceptions.*;
import com.nourish1709.project3_main_service.models.Account;
import com.nourish1709.project3_main_service.models.User;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    private AccountService accountService;
    private Account account;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        accountService = new AccountService(accountRepository);
        account = new Account();
        account.setId(1L);
        account.setFirstName("John");
        account.setLastName("Swirkey");
        account.setAge(24);
        account.setEnabledNotifications(true);
        account.setPhone("+380939309393");
        account.setUser(new User());
    }

    @Test
    void updateSuccessfulTest() {
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        Account expected = accountService.update(account);
        assertEquals(expected, account);
    }

    @Test
    void setNotificationsSuccessfulTest(){
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        accountService.setNotifications(1L, false);
        assertFalse(account.isEnabledNotifications());
    }

    @Test
    void updateInvalidNameExceptionTooShortTest() {
        account.setFirstName("");
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        assertThrows(InvalidNameException.class,
                () -> accountService.update(account));
    }

    @Test
    void updateInvalidNameExceptionTooLongTest() {
        account.setFirstName("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        assertThrows(InvalidNameException.class,
                () -> accountService.update(account));
    }

    @Test
    void updateInvalidAgeExceptionTest() {
        account.setAge(5);
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        assertThrows(InvalidAgeException.class,
                () -> accountService.update(account));
    }

    @Test
    void updateInvalidPhoneExceptionTest() {
        account.setPhone("");
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        assertThrows(InvalidPhoneException.class,
                () -> accountService.update(account));
    }

    @Test
    void updateBadCredentialsExceptionFirstNameIsNullTest() {
        account.setFirstName(null);
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        assertThrows(BadCredentialsException.class,
                () -> accountService.update(account));
    }

    @Test
    void updateBadCredentialsExceptionLastNameIsNullTest() {
        account.setLastName(null);
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        assertThrows(BadCredentialsException.class,
                () -> accountService.update(account));
    }

    @Test
    void updateBadCredentialsExceptionPhoneIsNullTest() {
        account.setPhone(null);
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        assertThrows(BadCredentialsException.class,
                () -> accountService.update(account));
    }
}