package com.nourish1709.project3_main_service.services;

import com.nourish1709.project3_main_service.daos.AccountRepository;
import com.nourish1709.project3_main_service.exceptions.*;
import com.nourish1709.project3_main_service.models.Account;
import com.nourish1709.project3_main_service.models.User;
import com.nourish1709.project3_main_service.models.dto.AccountDto;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    ApplicationContext applicationContext =
            new AnnotationConfigApplicationContext(ModelMapperConfiguration.class);

    ModelMapper modelMapper;

    private AccountService accountService;
    private Account account;
    private AccountDto accountDto;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        modelMapper = applicationContext.getBean(ModelMapper.class);

        accountService = new AccountService(accountRepository, modelMapper);
        account = new Account();
        account.setId(1L);
        account.setFirstName("John");
        account.setLastName("Swirkey");
        account.setAge(24);
        account.setEnabledNotifications(true);
        account.setPhone("+380939309393");
        account.setUser(new User());

        accountDto = new AccountDto();
        accountDto.setFirstName("John");
        accountDto.setLastName("Swirkey");
        accountDto.setAge(24);
        accountDto.setEnabledNotifications(true);
        accountDto.setPhone("+380939309393");
        accountDto.setUser(new User());
    }

    @Test
    void updateSuccessfulTest() {
        Mockito.when(accountRepository
                .findById(1L))
                .thenReturn(Optional.of(account));
        Mockito.when(accountRepository
                .save(Mockito.any(Account.class)))
                .thenReturn(account);
        AccountDto actual = accountService.update(1L, accountDto);
        assertEquals(accountDto, actual);
    }

    @Test
    void setNotificationsSuccessfulTest(){
        Mockito.when(accountRepository
                .findById(1L))
                .thenReturn(Optional.of(account));
        Mockito.when(accountRepository
                .save(account))
                .thenReturn(account);
        accountService.setNotifications(1L, false);
        assertFalse(account.isEnabledNotifications());
    }

    @Test
    void updateInvalidNameExceptionTooShortTest() {
        accountDto.setFirstName("");
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        assertThrows(InvalidNameException.class,
                () -> accountService.update(1L, accountDto));
    }

    @Test
    void updateInvalidNameExceptionTooLongTest() {
        accountDto.setFirstName("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        assertThrows(InvalidNameException.class,
                () -> accountService.update(1L, accountDto));
    }

    @Test
    void updateInvalidAgeExceptionTest() {
        accountDto.setAge(5);
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        assertThrows(InvalidAgeException.class,
                () -> accountService.update(1L, accountDto));
    }

    @Test
    void updateInvalidPhoneExceptionTest() {
        accountDto.setPhone("");
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        assertThrows(InvalidPhoneException.class,
                () -> accountService.update(1L, accountDto));
    }

    @Test
    void updateBadCredentialsExceptionFirstNameIsNullTest() {
        accountDto.setFirstName(null);
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        assertThrows(BadCredentialsException.class,
                () -> accountService.update(1L, accountDto));
    }

    @Test
    void updateBadCredentialsExceptionLastNameIsNullTest() {
        accountDto.setLastName(null);
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        assertThrows(BadCredentialsException.class,
                () -> accountService.update(1L, accountDto));
    }

    @Test
    void updateBadCredentialsExceptionPhoneIsNullTest() {
        accountDto.setPhone(null);
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        assertThrows(BadCredentialsException.class,
                () -> accountService.update(1L, accountDto));
    }
}