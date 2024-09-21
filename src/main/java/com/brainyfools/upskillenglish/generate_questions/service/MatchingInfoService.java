package com.brainyfools.upskillenglish.generate_questions.service;

import com.brainyfools.upskillenglish.auth.repository.UserRepository;
import com.brainyfools.upskillenglish.gemini.GeminiService;
import com.brainyfools.upskillenglish.generate_questions.model.MatchingInfoForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MatchingInfoService {

    GeminiService geminiService;
    UserRepository userRepository;

    public MatchingInfoService(GeminiService geminiService, UserRepository userRepository) {
        this.geminiService = geminiService;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> create(String passage) {
        String prompt = String.format("""
                Analyze the given %s. There are few paragraphs serialized from A.
                Select each paragraph in a random order. Generate the main theme of the paragraph
                or any other question from the paragraph but don't make the sentence too obvious that it can easily
                guess without putting good attention to the paragraph, you can use some metaphor here to indicate the
                paragraph if necessary. Then store it into the 'info' field of
                the infoList. Also store the serial of the paragraph into the field 'serial'.
                You must follow the JSON Structure provided below.
                
                {
                    "infoList": [
                        {
                            "info": "String",
                            "serial": "char",
                        }
                    ]
                }
                """, passage);
        MatchingInfoForm matchingInfoForm = geminiService.call(prompt, MatchingInfoForm.class);
        return new ResponseEntity<>(matchingInfoForm, HttpStatus.CREATED);
    }
}
