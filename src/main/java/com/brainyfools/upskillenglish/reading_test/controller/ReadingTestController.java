package com.brainyfools.upskillenglish.reading_test.controller;

import com.brainyfools.upskillenglish.generate_questions.service.GenerateMCQService;
import com.brainyfools.upskillenglish.generate_questions.service.GenerateTrueFalseService;
import com.brainyfools.upskillenglish.generate_questions.service.OpinionativeService;
import com.brainyfools.upskillenglish.reading_test.model.CombinedResponse;
import com.brainyfools.upskillenglish.reading_test.service.ReadingTestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ReadingTestController {

    private static final Logger LOGGER = LogManager.getLogger(ReadingTestController.class);

    private final ReadingTestService readingTestService;
    private final GenerateMCQService generateMCQService;
    private final GenerateTrueFalseService generateTrueFalseService;
    private final OpinionativeService opinionativeService;

    public ReadingTestController(ReadingTestService readingTestService, GenerateMCQService generateMCQService, GenerateTrueFalseService generateTrueFalseService, OpinionativeService opinionativeService) {
        this.readingTestService = readingTestService;
        this.generateMCQService = generateMCQService;
        this.generateTrueFalseService = generateTrueFalseService;
        this.opinionativeService = opinionativeService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/v2/generate-passage")
    public ResponseEntity<?> generatePassage() {
        // Generate the passage
        ResponseEntity<?> tmp = readingTestService.generatePassage(200);
        String passage = tmp.getBody().toString();

        ResponseEntity<?> mcq = generateMCQService.create(passage);
        ResponseEntity<?> opinionative = opinionativeService.create(passage);
        ResponseEntity<?> factCheck = generateTrueFalseService.create(passage);
        CombinedResponse combinedResponse = new CombinedResponse(
                tmp.getBody(),
                mcq.getBody(),
                opinionative.getBody(),
                factCheck.getBody()
        );
        LOGGER.info("Generate Passage: {}", combinedResponse);
        return ResponseEntity.ok(combinedResponse);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/v1/updateScore")
    public ResponseEntity<?> updateScore(@RequestParam int score, Authentication authentication) {
        LOGGER.info("Score: {}", score);
        return readingTestService.updateScore(score, authentication);
    }

}
