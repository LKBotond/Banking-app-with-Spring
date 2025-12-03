package com.banking.backend.controllers;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Cosmetics {

    @GetMapping("/favicon.ico")
    public ResponseEntity<Void> favicon() {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/.well-known/appspecific/{file:.+}")
    public ResponseEntity<Void> wellKnown(@PathVariable String file) {
        return ResponseEntity.noContent().build();
    }

}
