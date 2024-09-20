package com.brainyfools.upskillenglish.improve_listening.controller;

import com.brainyfools.upskillenglish.improve_listening.service.ImproveListeningService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ImproveListeningController {

    private static final Logger LOGGER = LogManager.getLogger(ImproveListeningController.class);

    private final ImproveListeningService improveListeningService;

    public ImproveListeningController(ImproveListeningService improveListeningService) {
        this.improveListeningService = improveListeningService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/v1/generate-listening-text")
    public ResponseEntity<?> createPassage() {
        return improveListeningService.generateListeningText();
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/v1/generate-listening-audio")
    public ResponseEntity<?> createAudio(@RequestBody String text) {
        LOGGER.info("Received audio text: {}", text);
        return improveListeningService.generateListeningAudio(text);
    }
}
