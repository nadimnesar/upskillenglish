package com.brainyfools.upskillenglish.reading_test.service;

import com.brainyfools.upskillenglish.auth.model.User;
import com.brainyfools.upskillenglish.auth.repository.UserRepository;
import com.brainyfools.upskillenglish.gemini.GeminiService;
import com.brainyfools.upskillenglish.reading_test.model.PassageTestModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ReadingTestService {
    private final GeminiService geminiService;
    private final UserRepository userRepository;

    public ReadingTestService(GeminiService geminiService, UserRepository userRepository) {
        this.geminiService = geminiService;
        this.userRepository = userRepository;
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

    public ResponseEntity<?> updateScore(int score, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        user.increaseXp(score);
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
