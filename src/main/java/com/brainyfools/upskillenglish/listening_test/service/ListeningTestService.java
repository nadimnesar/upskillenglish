package com.brainyfools.upskillenglish.listening_test.service;

import com.brainyfools.upskillenglish.generate_questions.service.GenerateMCQService;
import com.brainyfools.upskillenglish.generate_questions.service.GenerateTrueFalseService;
import com.brainyfools.upskillenglish.generate_questions.service.OpinionativeService;
import com.brainyfools.upskillenglish.listening_test.model.CombinedResponse3;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ListeningTestService {
    private final GenerateMCQService generateMCQService;
    private final OpinionativeService opinionativeService;
    private final GenerateTrueFalseService generateTrueFalseService;

    public ListeningTestService(GenerateMCQService generateMCQService, OpinionativeService opinionativeService, GenerateTrueFalseService generateTrueFalseService) {
        this.generateMCQService = generateMCQService;
        this.opinionativeService = opinionativeService;
        this.generateTrueFalseService = generateTrueFalseService;
    }

    public ResponseEntity<?> createQuestionResponse(String text) {
        ResponseEntity<?> mcqResponse = generateMCQService.create(text);
        ResponseEntity<?> opinionativeResponse = opinionativeService.create(text);
        ResponseEntity<?> factCheckResponse = generateTrueFalseService.create(text);

        CombinedResponse3 combinedResponse = new CombinedResponse3(
                mcqResponse.getBody(),
                opinionativeResponse.getBody(),
                factCheckResponse.getBody()
        );

        return ResponseEntity.ok(combinedResponse);
    }
}
