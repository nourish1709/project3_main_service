package com.nourish1709.project3_main_service.controllers;

import com.nourish1709.project3_main_service.models.dto.SkuDto;
import com.nourish1709.project3_main_service.services.SkuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SkuController {

    private final SkuService service;

    public SkuController(SkuService service) {
        this.service = service;
    }

    @PostMapping("/position")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SkuDto> createSku(@RequestBody SkuDto sku) {
        SkuDto newSku = new SkuDto();
        newSku.setName(sku.getName());
        newSku.setPrice(sku.getPrice());
        newSku.setDescription(sku.getDescription());
        newSku.setAvailability(sku.isAvailability());
        newSku.setCategoryId(sku.getCategoryId());
        newSku.setAccountId(sku.getAccountId());
        service.create(newSku);
        return new ResponseEntity<>(newSku, HttpStatus.OK);
    }

    @GetMapping("/position/")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<SkuDto>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/position/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<SkuDto> findById(@PathVariable Long id) {
        SkuDto sku = service.getById(id);
        if (sku == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(sku, HttpStatus.OK);
    }

    @PutMapping("/position/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SkuDto> updateSku(@PathVariable Long id, @RequestBody SkuDto sku) {
        SkuDto newSku = service.getById(id);
        newSku.setName(sku.getName());
        newSku.setPrice(sku.getPrice());
        newSku.setDescription(sku.getDescription());
        newSku.setAvailability(sku.isAvailability());
        newSku.setCategoryId(sku.getCategoryId());
        newSku.setAccountId(sku.getAccountId());
        SkuDto result = service.update(id, newSku);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/position/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SkuDto> deleteSku(@PathVariable Long id) {
        SkuDto sku = service.getById(id);
        if (sku == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.delete(id);
        return new ResponseEntity<>(sku, HttpStatus.OK);
    }
}


