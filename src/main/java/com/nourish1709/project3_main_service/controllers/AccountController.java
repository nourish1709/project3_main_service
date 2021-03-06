package com.nourish1709.project3_main_service.controllers;

import com.nourish1709.project3_main_service.models.dto.AccountDto;
import com.nourish1709.project3_main_service.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PutMapping("/accounts/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable Long id, @RequestBody AccountDto accountDto) {
        AccountDto accountDto1 = new AccountDto();
        accountDto1.setFirstName(accountDto.getFirstName());
        accountDto1.setLastName(accountDto.getLastName());
        accountDto1.setAge(accountDto.getAge());
        accountDto1.setUser(accountDto.getUser());
        accountDto1.setEnabledNotifications(accountDto.isEnabledNotifications());
        AccountDto result = service.update(id, accountDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/accounts/notifications/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<AccountDto> setNotification(@PathVariable Long id, @RequestBody boolean notification) {
        AccountDto accountDto = service.getById(id);
        accountDto.setEnabledNotifications(notification);
        service.setNotifications(id, notification);
        return new ResponseEntity<>(accountDto, HttpStatus.OK);
    }

    @GetMapping("/accounts/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AccountDto> findById(@PathVariable Long id) {
        AccountDto accountDto = service.getById(id);
        if (accountDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accountDto, HttpStatus.OK);
    }
}
