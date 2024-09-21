package com.brainyfools.upskillenglish.generate_questions.controller;

import com.brainyfools.upskillenglish.generate_questions.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
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

    @PostMapping("/mcq")
    public ResponseEntity<?> mcqPractice(@RequestBody String passage) {
        return generateMCQService.create(passage);
    }

    @PostMapping("/factCheck")
    public ResponseEntity<?> truefalsePractice(@RequestBody String passage) {
        return generateTrueFalseService.create(passage);
    }

    @PostMapping("/opinionative")
    public ResponseEntity<?> opinionativePractice(@RequestBody String passage) {
        return opinionativeService.create(passage);
    }

    @PostMapping("/matching")
    public ResponseEntity<?> matchingPractice(@RequestBody String passage) {
        return matchingInfoService.create(passage);
    }

    @PostMapping("/completion")
    public ResponseEntity<?> completionPractice(@RequestBody String passage) {
        return sentenceCompletionService.create(passage);
    }
}
