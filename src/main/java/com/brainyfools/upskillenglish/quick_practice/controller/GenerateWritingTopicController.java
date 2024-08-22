package com.brainyfools.upskillenglish.quick_practice.controller;

import com.brainyfools.upskillenglish.quick_practice.service.GenerateWritingTopicService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class GenerateWritingTopicController {


    GenerateWritingTopicService generateWritingTopicService;

    public GenerateWritingTopicController(GenerateWritingTopicService generateWritingTopicService) {
        this.generateWritingTopicService = generateWritingTopicService;
    }

    @GetMapping("/genTopic")
    public ResponseEntity<?> create() {
        return generateWritingTopicService.generateWritingTopic();
    }





}