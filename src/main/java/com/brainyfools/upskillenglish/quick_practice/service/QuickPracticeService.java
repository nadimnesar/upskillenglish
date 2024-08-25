package com.brainyfools.upskillenglish.quick_practice.service;

import com.brainyfools.upskillenglish.auth.model.User;
import com.brainyfools.upskillenglish.auth.repository.UserRepository;
import com.brainyfools.upskillenglish.gemini.GeminiService;
import com.brainyfools.upskillenglish.quick_practice.model.QuestionForm;
import com.brainyfools.upskillenglish.quick_practice.model.ResponseQuestionForm;
import com.brainyfools.upskillenglish.quick_practice.model.SubmittedQuestionForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class QuickPracticeService {

    GeminiService geminiService;
    UserRepository userRepository;

    public QuickPracticeService(GeminiService geminiService, UserRepository userRepository) {
        this.geminiService = geminiService;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> create() {
        String prompt = """
                Task: Generate 10 multiple-choice questions (MCQs) using the specified JSON format. The questions should be designed for someone aiming to achieve an IELTS score of 7 or higher.
                
                Topics to Choose From:
                - Grammatical Error Identification
                - Sentence Completion
                - Sentence Correction
                - Synonyms and Antonyms
                - Appropriate Word Usage
                - Contextual Vocabulary
                - Coherence and Cohesion
                - Argument Analysis
                - Assumption Identification
                - Idioms and Phrasal Verbs
                
                Requirements:
                - Each question should clearly state the task in the `question` field.
                - Provide four answer options (`optionA`, `optionB`, `optionC`, `optionD`) for each question.
                - Indicate the correct answer by setting the `answer` field to one of the options (`a`, `b`, `c`, or `d`).
                - Ensure that no question is repeated within the same response.
                - Use the JSON structure below for formatting the questions:
                
                {
                    "questionList": [
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
                """;

        QuestionForm questionForm = geminiService.call(prompt, QuestionForm.class);
        return new ResponseEntity<>(questionForm, HttpStatus.CREATED);
    }

    public ResponseEntity<?> submit(SubmittedQuestionForm submittedQuestionForm,
                                    Authentication authentication) {
        ResponseQuestionForm responseQuestionForm = new ResponseQuestionForm();
        long xp = 0;
        for (SubmittedQuestionForm.SubmittedQuestion submittedQuestion :
                submittedQuestionForm.getSubmittedQuestionFormList()) {

            xp += submittedQuestion.getAnswer().equals(submittedQuestion.getUserAnswer()) ? 1 : 0;

            ResponseQuestionForm.ResponseQuestion responseQuestion =
                    new ResponseQuestionForm.ResponseQuestion();

            responseQuestion.setQuestion(submittedQuestion.getQuestion());
            switch (submittedQuestion.getAnswer()) {
                case "a" -> responseQuestion.setAnswer(submittedQuestion.getOptionA());
                case "b" -> responseQuestion.setAnswer(submittedQuestion.getOptionB());
                case "c" -> responseQuestion.setAnswer(submittedQuestion.getOptionC());
                case "d" -> responseQuestion.setAnswer(submittedQuestion.getOptionD());
                default -> responseQuestion.setAnswer(null);
            }
            switch (submittedQuestion.getUserAnswer()) {
                case "a" -> responseQuestion.setUserAnswer(submittedQuestion.getOptionA());
                case "b" -> responseQuestion.setUserAnswer(submittedQuestion.getOptionB());
                case "c" -> responseQuestion.setUserAnswer(submittedQuestion.getOptionC());
                case "d" -> responseQuestion.setUserAnswer(submittedQuestion.getOptionD());
                default -> responseQuestion.setUserAnswer(null);
            }
            responseQuestionForm.getResponseQuestionList().add(responseQuestion);
        }

        User user = (User) authentication.getPrincipal();
        user.increaseXp(xp);
        userRepository.save(user);

        return new ResponseEntity<>(responseQuestionForm, HttpStatus.CREATED);
    }
}
