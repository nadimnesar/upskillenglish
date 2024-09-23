package com.brainyfools.upskillenglish.improve_writing.controller;

import com.brainyfools.upskillenglish.improve_writing.service.GenerateWritingTopicService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GenerateWritingTopicController {

    GenerateWritingTopicService generateWritingTopicService;

    public GenerateWritingTopicController(GenerateWritingTopicService generateWritingTopicService) {
        this.generateWritingTopicService = generateWritingTopicService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/v1/generate-topic")
    public ResponseEntity<?> create() {
        return generateWritingTopicService.generateWritingTopic();
    }
}