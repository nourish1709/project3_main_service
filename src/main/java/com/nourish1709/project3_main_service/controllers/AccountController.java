package com.nourish1709.project3_main_service.controllers;

import com.nourish1709.project3_main_service.models.dto.AccountDto;
import com.nourish1709.project3_main_service.services.AccountService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop/account")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping("/{id}")
    public AccountDto updateAccount(@PathVariable Long id, @RequestBody AccountDto accountDto) {
        return service.update(id, accountDto);
    }

    @PostMapping("/{id}/{notification}")
    public void setNotification(@PathVariable Long id, @PathVariable boolean notification) {
        service.setNotifications(id, notification);
    }

    @GetMapping("/{id}")
    public AccountDto getById(@PathVariable Long id){
        return service.getById(id);
    }
}
