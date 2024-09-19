package com.brainyfools.upskillenglish.generate_questions.controller;

import com.brainyfools.upskillenglish.generate_questions.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenerateQuestionsController {
    private final OpinionativeService opinionativeService;
    private  final GenerateMCQService generateMCQService;
    private  final GenerateTrueFalseService generateTrueFalseService;
    private final MatchingInfoService matchingInfoService;
    private final SentenceCompletionService sentenceCompletionService;

    public GenerateQuestionsController(OpinionativeService opinionativeService, GenerateMCQService generateMCQService, GenerateTrueFalseService generateTrueFalseService, MatchingInfoService matchingInfoService, SentenceCompletionService sentenceCompletionService) {
        this.opinionativeService = opinionativeService;
        this.generateMCQService = generateMCQService;
        this.generateTrueFalseService = generateTrueFalseService;
        this.matchingInfoService = matchingInfoService;
        this.sentenceCompletionService = sentenceCompletionService;
    }


    @PostMapping("/api/public/mcq")
    public ResponseEntity<String> mcqPractice(@RequestBody String passage) {
        return (ResponseEntity<String>) generateMCQService.create(passage);
    }

    @PostMapping("/api/public/true-false")
    public ResponseEntity<String> truefalsePractice(@RequestBody String passage) {
        return (ResponseEntity<String>) generateTrueFalseService.create(passage);
    }

    @PostMapping("/api/public/opinionative")
    public ResponseEntity<String> opinionativePractice(@RequestBody String passage) {
        return (ResponseEntity<String>) opinionativeService.create(passage);
    }

    @PostMapping("/api/public/matching")
    public ResponseEntity<String> matchingPractice(@RequestBody String passage) {
        return (ResponseEntity<String>) matchingInfoService.create(passage);
    }
    @PostMapping("/api/public/completion")
    public ResponseEntity<String> completionPractice(@RequestBody String passage) {
        return (ResponseEntity<String>) sentenceCompletionService.create(passage);
    }
}
