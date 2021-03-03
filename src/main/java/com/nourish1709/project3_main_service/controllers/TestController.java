package com.nourish1709.project3_main_service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashSet;
import java.util.Set;

@RestController
public class TestController {

    @GetMapping("/names")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<String>> getNames() {
        Set<String> names = new LinkedHashSet<>(Set.of(
                "Oleg",
                "Roman",
                "Andrianna",
                "Zhenya"
        ));

        return new ResponseEntity<>(names, HttpStatus.OK);
    }

    @GetMapping("/name")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> getName() {
        return new ResponseEntity<>("project 3", HttpStatus.OK);
    }
}
