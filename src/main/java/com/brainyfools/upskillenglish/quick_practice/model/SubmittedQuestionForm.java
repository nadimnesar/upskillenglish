package com.brainyfools.upskillenglish.quick_practice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubmittedQuestionForm {
    private List<SubmittedQuestion> submittedQuestionFormList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SubmittedQuestion {
        private String question;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private String answer;
        private String userAnswer;
    }
}