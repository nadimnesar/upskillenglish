package com.brainyfools.upskillenglish.controller;

import com.brainyfools.upskillenglish.gemini.GeminiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gemini")
public class GeminiController {
    GeminiService geminiService;

    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @GetMapping("/v1")
    public ResponseEntity<?> getV1(@RequestParam String prompt) {
        return geminiService.callApi(prompt);
    }

    @GetMapping("/v2")
    public ResponseEntity<?> getV2() {
        String prompt = """
                        are you Gemini AI?
                """;
        return geminiService.callApi(prompt);
    }
}