package com.brainyfools.upskillenglish.reading_test.service;

import com.brainyfools.upskillenglish.auth.repository.UserRepository;
import com.brainyfools.upskillenglish.gemini.GeminiService;
import com.brainyfools.upskillenglish.reading_test.model.MultiplePassageForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MultiplePassageService {
    GeminiService geminiService;
    UserRepository userRepository;
    public MultiplePassageService(GeminiService geminiService, UserRepository userRepository) {
        this.geminiService = geminiService;
        this.userRepository = userRepository;
    }
    public ResponseEntity<?> generateMultiplePassage() {
        String prompt = String.format("""
                Task: Generate 5 short passage of approximately 60 words each and store them in the psList using the specified JSON format.
                The passages should be tailored for someone preparing for the IELTS reading test, aiming for a score of 7 or higher.
                Choose topics that are frequently featured in the IELTS examination.
                
                {
                    "psList": [
                        {
                            "A": "String",
                            "B": "String",
                            "C": "String",
                            "D": "String",
                            "E": "String",
                        }
                    ]
                }
                
                Note: Replace "String" with the appropriate passage text. The first character of the String
                should be the Serial Number of the string, that means if the the index is 'A' the the passage serial is A. Don't repeat same passage.
                """);
        MultiplePassageForm passages = geminiService.call(prompt, MultiplePassageForm.class);
        return new ResponseEntity<>(passages, HttpStatus.CREATED);
    }
}
