package com.brainyfools.upskillenglish.generate_passage.controller;

import com.brainyfools.upskillenglish.generate_passage.service.GeneratePassageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GeneratePassageController {

    private static final Logger LOGGER = LogManager.getLogger(GeneratePassageController.class);
    private final GeneratePassageService generatePassageService;

    public GeneratePassageController(GeneratePassageService generatePassageService) {
        this.generatePassageService = generatePassageService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/v1/generate-passage")
    public ResponseEntity<?> generatePassage(@RequestParam(defaultValue = "250") int wordLimit) {
        return generatePassageService.generatePassage(wordLimit);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/v1/translate-passage")
    public ResponseEntity<?> translatePassage(@RequestBody String passage) {
        LOGGER.info("Received passage for translate: {}", passage);
        return generatePassageService.translatePassage(passage);
    }
}
