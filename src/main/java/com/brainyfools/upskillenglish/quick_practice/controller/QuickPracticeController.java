package com.brainyfools.upskillenglish.quick_practice.controller;

import com.brainyfools.upskillenglish.quick_practice.service.QuickPracticeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class QuickPracticeController {

    QuickPracticeService quickPracticeService;

    public QuickPracticeController(QuickPracticeService quickPracticeService) {
        this.quickPracticeService = quickPracticeService;
    }

    @GetMapping("/v1/create")
    public ResponseEntity<?> create() {
        return quickPracticeService.create();
    }
}