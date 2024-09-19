package com.brainyfools.upskillenglish.generate_questions.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SentenceCompletionForm {
    private List<Question> scList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Question {
        private String sentence;
        private String answer;
    }
}
