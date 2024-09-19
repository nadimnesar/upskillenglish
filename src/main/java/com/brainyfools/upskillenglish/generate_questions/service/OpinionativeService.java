package com.brainyfools.upskillenglish.generate_questions.service;

import com.brainyfools.upskillenglish.auth.repository.UserRepository;
import com.brainyfools.upskillenglish.gemini.GeminiService;
import com.brainyfools.upskillenglish.generate_questions.model.OpinionativeForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OpinionativeService {

    GeminiService geminiService;
    UserRepository userRepository;

    public OpinionativeService(GeminiService geminiService, UserRepository userRepository) {
        this.geminiService = geminiService;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> create(String passage) {
        String prompt =  String.format("""
                Task: Generate 10 Opinion based questions from the passage using the specified JSON format. The questions should be designed for someone aiming to achieve an IELTS score of 7 or higher.
                
                Requirements:
                - Each question should clearly state the task in the `question` field.
                - The 'answer' field should contain the answer of the question, which is in the format of either Yes or No.
                - Ensure that no question is repeated within the same response.
                - Use the JSON structure below for formatting the questions:
                
                {
                    "questionList": [
                        {
                            "question": "String",
                            "answer": "Yes | NO"
                        }
                    ]
                }
                
        
                """,passage);

        OpinionativeForm opinionativeForm = geminiService.call(prompt, OpinionativeForm.class);
        return new ResponseEntity<>(opinionativeForm, HttpStatus.CREATED);
    }


}
