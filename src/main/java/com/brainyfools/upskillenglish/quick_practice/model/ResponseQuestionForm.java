package com.brainyfools.upskillenglish.quick_practice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseQuestionForm {
    private List<ResponseQuestion> responseQuestionList;

    public List<ResponseQuestion> getResponseQuestionList() {
        if (this.responseQuestionList == null) {
            this.responseQuestionList = new ArrayList<>();
        }
        return responseQuestionList;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseQuestion {
        private String question;
        private String answer;
        private String userAnswer;
    }
}
