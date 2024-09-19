package com.brainyfools.upskillenglish.generate_questions.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrueFalseForm {
    private List<Question> tfList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Question {
        private String question;
        private String answer;
    }
}
