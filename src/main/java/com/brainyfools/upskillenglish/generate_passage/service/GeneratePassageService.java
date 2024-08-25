package com.brainyfools.upskillenglish.generate_passage.service;

import com.brainyfools.upskillenglish.gemini.GeminiService;
import com.brainyfools.upskillenglish.generate_passage.model.PassageModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GeneratePassageService {

    private final GeminiService geminiService;

    public GeneratePassageService(GeminiService geminiService) {
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
        PassageModel passage = geminiService.call(prompt, PassageModel.class);
        return new ResponseEntity<>(passage, HttpStatus.CREATED);
    }

    public ResponseEntity<?> translatePassage(String passageText) {
        String prompt = String.format("""
                Task: Translate the passage below into Bengali and provide the translated text using the specified JSON format.
                
                %s
                
                {
                    "passage": "String"
                }
                
                Note: Replace "String" with the appropriate translated passage text. Ensure that the translation is done line by line, making it easy for anyone to understand and learn from.
                """, passageText);

        PassageModel passage = geminiService.call(prompt, PassageModel.class);
        return new ResponseEntity<>(passage, HttpStatus.CREATED);
    }
}
