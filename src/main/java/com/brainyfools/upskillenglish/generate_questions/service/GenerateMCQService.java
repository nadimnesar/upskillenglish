package com.brainyfools.upskillenglish.generate_questions.service;

import com.brainyfools.upskillenglish.auth.repository.UserRepository;
import com.brainyfools.upskillenglish.gemini.GeminiService;
import com.brainyfools.upskillenglish.generate_questions.model.MCQForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GenerateMCQService {

    GeminiService geminiService;
    UserRepository userRepository;

    public GenerateMCQService(GeminiService geminiService, UserRepository userRepository) {
        this.geminiService = geminiService;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> create(String passage) {
        String prompt = String.format("""
                Analyze the given %s.
                Task: Generate 10 multiple-choice questions (MCQs) from the passage using the specified JSON format. The questions should be designed for someone aiming to achieve an IELTS score of 7 or higher.
                
                Requirements:
                - Each question should clearly state the task in the `question` field.
                - Provide four answer options (`optionA`, `optionB`, `optionC`, `optionD`) for each question.
                - Indicate the correct answer by setting the `answer` field to one of the options (`a`, `b`, `c`, or `d`).
                - Ensure that no question is repeated within the same response.
                - Use the JSON structure below for formatting the questions:
                
                {
                    "mcqList": [
                        {
                            "question": "String",
                            "optionA": "String",
                            "optionB": "String",
                            "optionC": "String",
                            "optionD": "String",
                            "answer": "a | b | c | d"
                        }
                    ]
                }
                
                Note: Replace "String" with appropriate text for each field. Only use "a", "b", "c", or "d" in the `answer` field.
                """, passage);

        MCQForm mcqForm = geminiService.call(prompt, MCQForm.class);
        return new ResponseEntity<>(mcqForm, HttpStatus.CREATED);
    }
}
