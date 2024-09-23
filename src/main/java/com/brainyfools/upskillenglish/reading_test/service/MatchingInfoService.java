package com.brainyfools.upskillenglish.reading_test.service;

import com.brainyfools.upskillenglish.auth.repository.UserRepository;
import com.brainyfools.upskillenglish.gemini.GeminiService;
import com.brainyfools.upskillenglish.reading_test.model.MatchingInfoForm;
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
    Analyze the given %s. There are several paragraphs serialized from A onwards.
    Select and process each paragraph in a completely random order, without following the original sequence.
    For each randomly selected paragraph, generate one concise sentence that summarizes the main theme or poses 
    a subtle question related to the paragraph. Ensure that the generated sentence is not too obvious, requiring 
    careful attention to the details of the paragraph. Use metaphorical language or indirect clues when necessary 
    to make the question or statement less direct. Generate exactly 5 sentence from five different paragraphs.

    Store the generated sentence in the 'info' field and the corresponding paragraph's serial in the 'serial' field 
    of the infoList. Make sure to shuffle the order of paragraphs before generating the info. 

    Follow the exact JSON structure provided below:
    
    {
        "infoList": [
            {
                "info": "String",
                "serial": "char"
            }
        ]
    }
    """, passage);


        MatchingInfoForm matchingInfoForm = geminiService.call(prompt, MatchingInfoForm.class);
        return new ResponseEntity<>(matchingInfoForm, HttpStatus.CREATED);
    }

}
