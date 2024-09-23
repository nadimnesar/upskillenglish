package com.brainyfools.upskillenglish.reading_test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchingInfoForm {
    private List<Question> infoList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Question {
        private String info;
        private char serial;
    }
}
