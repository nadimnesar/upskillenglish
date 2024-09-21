package com.brainyfools.upskillenglish.generate_questions.controller;

import com.brainyfools.upskillenglish.generate_questions.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GenerateQuestionsController {

    private final OpinionativeService opinionativeService;
    private final GenerateMCQService generateMCQService;
    private final GenerateTrueFalseService generateTrueFalseService;
    private final MatchingInfoService matchingInfoService;
    private final SentenceCompletionService sentenceCompletionService;

    public GenerateQuestionsController(OpinionativeService opinionativeService, GenerateMCQService generateMCQService, GenerateTrueFalseService generateTrueFalseService, MatchingInfoService matchingInfoService, SentenceCompletionService sentenceCompletionService) {
        this.opinionativeService = opinionativeService;
        this.generateMCQService = generateMCQService;
        this.generateTrueFalseService = generateTrueFalseService;
        this.matchingInfoService = matchingInfoService;
        this.sentenceCompletionService = sentenceCompletionService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/v1/generate-mcq")
    public ResponseEntity<?> mcqPractice(@RequestBody String passage) {
        return generateMCQService.create(passage);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/v1/generate-factCheck")
    public ResponseEntity<?> factCheckPractice(@RequestBody String passage) {
        return generateTrueFalseService.create(passage);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/v1/generate-opinionative")
    public ResponseEntity<?> opinionativePractice(@RequestBody String passage) {
        return opinionativeService.create(passage);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/v1/generate-matching")
    public ResponseEntity<?> matchingPractice(@RequestBody String passage) {
        return matchingInfoService.create(passage);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/v1/generate-completion")
    public ResponseEntity<?> completionPractice(@RequestBody String passage) {
        return sentenceCompletionService.create(passage);
    }
}
