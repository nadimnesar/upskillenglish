package com.brainyfools.upskillenglish.quick_practice.controller;

import com.brainyfools.upskillenglish.quick_practice.service.QuickPracticeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class QuickPracticeController {

    QuickPracticeService quickPracticeService;

    public QuickPracticeController(QuickPracticeService quickPracticeService) {
        this.quickPracticeService = quickPracticeService;
    }

    @GetMapping("/v1/create-quick-practice")
    public ResponseEntity<?> create() {
        return quickPracticeService.create();
    }
}