package com.brainyfools.upskillenglish.reading_test.service;

import com.brainyfools.upskillenglish.gemini.GeminiService;
import com.brainyfools.upskillenglish.reading_test.model.PassageTestModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GeneratePassageTestService {
    private final GeminiService geminiService;
    public GeneratePassageTestService(GeminiService geminiService) {
        this.geminiService = geminiService;
    }
    public ResponseEntity<?> generatePassage(int wordLimit) {
        String prompt = String.format("""
                Task: Generate a passage of approximately %s words using the specified JSON format.
                The passage should be tailored for someone preparing for the IELTS reading test, aiming for a score of 7 or higher.
                Choose topics that are frequently featured in the IELTS examination.
                
                {
                    "passage": "String"
                }
                
                Note: Replace "String" with the appropriate passage text. Don't repeat same passage again.
                """, wordLimit);
        PassageTestModel passageTestModel = geminiService.call(prompt, PassageTestModel.class);
        return new ResponseEntity<>(passageTestModel, HttpStatus.CREATED);
    }
}
