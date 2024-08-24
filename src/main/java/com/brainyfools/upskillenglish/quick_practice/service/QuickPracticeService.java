package com.brainyfools.upskillenglish.quick_practice.service;

import com.brainyfools.upskillenglish.gemini.GeminiService;
import com.brainyfools.upskillenglish.quick_practice.model.QuestionForm;
import com.brainyfools.upskillenglish.quick_practice.model.ResponseQuestionForm;
import com.brainyfools.upskillenglish.quick_practice.model.SubmittedQuestionForm;
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

    public ResponseEntity<?> submit(SubmittedQuestionForm submittedQuestionForm) {
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

        System.out.println("got it:" + xp);
        return new ResponseEntity<>(responseQuestionForm, HttpStatus.CREATED);
    }
}
