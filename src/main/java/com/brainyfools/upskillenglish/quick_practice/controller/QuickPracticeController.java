package com.brainyfools.upskillenglish.quick_practice.controller;

import com.brainyfools.upskillenglish.quick_practice.model.SubmittedQuestionForm;
import com.brainyfools.upskillenglish.quick_practice.service.QuickPracticeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class QuickPracticeController {

    QuickPracticeService quickPracticeService;

    public QuickPracticeController(QuickPracticeService quickPracticeService) {
        this.quickPracticeService = quickPracticeService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/v1/create-quick-practice")
    public ResponseEntity<?> create() {
        return quickPracticeService.create();
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/v1/submit-quick-practice")
    public ResponseEntity<?> submit(@RequestBody SubmittedQuestionForm submittedQuestionForm) {
        return quickPracticeService.submit(submittedQuestionForm);
    }
}