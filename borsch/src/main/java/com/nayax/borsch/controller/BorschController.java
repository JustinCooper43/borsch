package com.nayax.borsch.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BorschController {
    @GetMapping("/hello")
    public ResponseEntity<?> greetings() {
        return ResponseEntity.ok().body("Hello borsch!");
    }
}
