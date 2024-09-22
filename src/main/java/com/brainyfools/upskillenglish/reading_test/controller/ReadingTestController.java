package com.brainyfools.upskillenglish.reading_test.controller;

import com.brainyfools.upskillenglish.generate_questions.service.GenerateMCQService;
import com.brainyfools.upskillenglish.generate_questions.service.GenerateTrueFalseService;
import com.brainyfools.upskillenglish.generate_questions.service.OpinionativeService;
import com.brainyfools.upskillenglish.reading_test.model.CombinedResponse;
import com.brainyfools.upskillenglish.reading_test.service.GeneratePassageTestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class ReadingTestController {
    private final GeneratePassageTestService generatePassageTestService;
    private final GenerateMCQService generateMCQService;
    private final GenerateTrueFalseService generateTrueFalseService;
    private final OpinionativeService opinionativeService;
    public ReadingTestController(GeneratePassageTestService generatePassageTestService, GenerateMCQService generateMCQService, GenerateTrueFalseService generateTrueFalseService, OpinionativeService opinionativeService) {
        this.generatePassageTestService = generatePassageTestService;
        this.generateMCQService = generateMCQService;
        this.generateTrueFalseService = generateTrueFalseService;
        this.opinionativeService = opinionativeService;
    }

    @GetMapping("/v2/generate-passage")
    public ResponseEntity<?> generatePassage() {
        // Generate the passage
        ResponseEntity<?> tmp = generatePassageTestService.generatePassage(200);
        String passage = tmp.getBody().toString();

        ResponseEntity<?> mcq = generateMCQService.create(passage);
        ResponseEntity<?> opinionative = opinionativeService.create(passage);
        ResponseEntity<?> factCheck = generateTrueFalseService.create(passage);
        System.out.println(passage);
        CombinedResponse combinedResponse = new CombinedResponse(
                tmp.getBody(),
                mcq.getBody(),
                opinionative.getBody(),
                factCheck.getBody()
        );
        return ResponseEntity.ok(combinedResponse);
    }

}
