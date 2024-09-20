package com.brainyfools.upskillenglish.generate_questions.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MCQForm {

    private List<Question> mcqList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Question {
        private String question;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private String answer;
    }
}
