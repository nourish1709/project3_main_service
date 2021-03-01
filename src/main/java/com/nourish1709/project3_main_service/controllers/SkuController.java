package com.nourish1709.project3_main_service.controllers;

import com.nourish1709.project3_main_service.models.dto.SkuDto;
import com.nourish1709.project3_main_service.services.SkuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop/sku/")
public class SkuController {

    private final SkuService service;

    public SkuController(SkuService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public SkuDto createSku(@RequestBody SkuDto sku) {
        return service.create(sku);
    }

    @GetMapping
    public List<SkuDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public SkuDto findById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/{id}")
    public SkuDto updateSku(@PathVariable Long id, @RequestBody SkuDto sku) {
        return service.update(id, sku);
    }

    @DeleteMapping("/{id}")
    public void deleteSku(@PathVariable Long id) {
        service.delete(id);
    }

}
