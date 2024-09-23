package com.brainyfools.upskillenglish.reading_test.controller;

import com.brainyfools.upskillenglish.generate_questions.service.GenerateMCQService;
import com.brainyfools.upskillenglish.generate_questions.service.GenerateTrueFalseService;
import com.brainyfools.upskillenglish.generate_questions.service.OpinionativeService;
import com.brainyfools.upskillenglish.reading_test.model.CombinedPassageAndMatchingInfoResponse;
import com.brainyfools.upskillenglish.reading_test.model.CombinedResponse;
import com.brainyfools.upskillenglish.reading_test.model.MatchingInfoForm;
import com.brainyfools.upskillenglish.reading_test.model.MultiplePassageForm;
import com.brainyfools.upskillenglish.reading_test.service.MatchingInfoService;
import com.brainyfools.upskillenglish.reading_test.service.MultiplePassageService;
import com.brainyfools.upskillenglish.reading_test.service.ReadingTestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
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
    private final MultiplePassageService multiplePassageService;
    private final MatchingInfoService matchingInfoService;

    public ReadingTestController(ReadingTestService readingTestService, GenerateMCQService generateMCQService, GenerateTrueFalseService generateTrueFalseService, OpinionativeService opinionativeService, MultiplePassageService multiplePassageService, MatchingInfoService matchingInfoService) {
        this.readingTestService = readingTestService;
        this.generateMCQService = generateMCQService;
        this.generateTrueFalseService = generateTrueFalseService;
        this.opinionativeService = opinionativeService;
        this.multiplePassageService = multiplePassageService;
        this.matchingInfoService = matchingInfoService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/v2/generate-passage")
    public ResponseEntity<?> generatePassage() {
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
    @PostMapping("/v1/generate-matching")
    public ResponseEntity<?> generatePassagesAndMatchingInfo() {
        ResponseEntity<?> passagesResponse = multiplePassageService.generateMultiplePassage();
        MultiplePassageForm multiplePassageForm = (MultiplePassageForm) passagesResponse.getBody();

        if (multiplePassageForm == null || multiplePassageForm.getPsList().isEmpty()) {
            return new ResponseEntity<>("Failed to generate passages", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        StringBuilder passagesBuilder = new StringBuilder();
        for (MultiplePassageForm.Passage passage : multiplePassageForm.getPsList()) {
            passagesBuilder.append(passage.getA()).append(" ");
            passagesBuilder.append(passage.getB()).append(" ");
            passagesBuilder.append(passage.getC()).append(" ");
            passagesBuilder.append(passage.getD()).append(" ");
            passagesBuilder.append(passage.getE()).append(" ");
        }
        String concatenatedPassages = passagesBuilder.toString().trim();

        ResponseEntity<?> matchingInfoResponse = matchingInfoService.create(concatenatedPassages);
        MatchingInfoForm matchingInfoForm = (MatchingInfoForm) matchingInfoResponse.getBody();

        if (matchingInfoForm == null || matchingInfoForm.getInfoList().isEmpty()) {
            return new ResponseEntity<>("Failed to generate matching info", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        CombinedPassageAndMatchingInfoResponse combinedResponse2 = new CombinedPassageAndMatchingInfoResponse(
                multiplePassageForm,
                matchingInfoForm
        );
        LOGGER.info("Generated passages and matching info: {}", combinedResponse2);
        return new ResponseEntity<>(combinedResponse2, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/v1/updateScore")
    public ResponseEntity<?> updateScore(@RequestParam int score, Authentication authentication) {
        LOGGER.info("Score: {}", score);
        return readingTestService.updateScore(score, authentication);
    }
}
