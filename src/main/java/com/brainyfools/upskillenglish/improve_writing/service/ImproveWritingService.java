package com.brainyfools.upskillenglish.improve_writing.service;

import com.brainyfools.upskillenglish.gemini.GeminiService;
import com.brainyfools.upskillenglish.improve_writing.model.ImproveWritingForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ImproveWritingService {

    GeminiService geminiService;

    public ImproveWritingService(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    public ResponseEntity<?> judgeWriting(String question, String answer) {
        String prompt = String.format("""
                    Judge the answer according to the question: "%s". The answer is: "%s". Provide the solution using the JSON format below.
                    Please list all errors with their solutions in bullet-point format, specifying the sentence for each error. Provide overall feedback on whether the writing answers the question.
                    For solutions, correct grammar issues (verb tense, subject-verb agreement, articles, prepositions), check spelling and punctuation, and assess coherence and organization.
                    Ensure the essay addresses both views, supports the writer's opinion, uses varied vocabulary, simplifies complex sentences, and meets the minimum word count (250 words).
                    If you were an IELTS instructor, what score would you give this answer out of 10?
                    {
                        "solutionList":
                        [
                            {
                              "solution": "String"
                            }
                        ]
                    }
                """, question, answer);

        ImproveWritingForm improveWritingForm = geminiService.call(prompt, ImproveWritingForm.class);
        return new ResponseEntity<>(improveWritingForm, HttpStatus.CREATED);
    }
}
