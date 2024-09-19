package com.brainyfools.upskillenglish.generate_questions.service;

import com.brainyfools.upskillenglish.auth.repository.UserRepository;
import com.brainyfools.upskillenglish.gemini.GeminiService;
import com.brainyfools.upskillenglish.generate_questions.model.SentenceCompletionForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SentenceCompletionService {
    GeminiService geminiService;
    UserRepository userRepository;

    public SentenceCompletionService(GeminiService geminiService,
                                     UserRepository userRepository) {
        this.geminiService = geminiService;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> create(String passage) {
        System.out.println(passage);
        String prompt = String.format( """
                Analyze the given %s. I need to complete sentences using words from the passage,
                for testing comprehension of specific information. Now generate 5 incomplete sentences.
                Store them in the 'sentence' field, then provide the word/phrase which will complete the
                sentence and store it into the field 'answer'.
                You must follow the JSON Structure provided below.
                
                {
                    "scList": [
                        {
                            "sentence": "String",
                            "answer": "char",
                        }
                    ]
                }
                
        
                """, passage);
        SentenceCompletionForm sentenceCompletionForm = geminiService.call(prompt, SentenceCompletionForm.class);
        return new ResponseEntity<>(sentenceCompletionForm, HttpStatus.CREATED);

    }
}
