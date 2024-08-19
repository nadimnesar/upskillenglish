package com.brainyfools.upskillenglish.quick_practice.service;

import com.brainyfools.upskillenglish.gemini.GeminiService;
import com.brainyfools.upskillenglish.quick_practice.model.QuestionForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class QuickPracticeService {

    GeminiService geminiService;

    public QuickPracticeService(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    public ResponseEntity<?> create() {
        String prompt = """
                    Generate 10 multiple-choice questions (MCQs) using the JSON format below.
                    You can choose any topics from the following list: Grammatical Error Identification, Sentence Completion,
                    Sentence Correction, Synonyms and Antonyms, Appropriate Word Usage, Contextual Vocabulary, Coherence and Cohesion,
                    Argument Analysis, Assumption Identification, Idioms and Phrasal Verbs. Clearly mention the question in the statement.
                    {
                    "questionList":
                        [
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
                    And don't repeat same question.
                """;

        QuestionForm questionForm = geminiService.call(prompt, QuestionForm.class);
        return new ResponseEntity<>(questionForm, HttpStatus.CREATED);
    }
}
