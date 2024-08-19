package com.brainyfools.upskillenglish.quick_practice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImproveWritingForm {
    private List<Question> SolutionList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Question {
        private String solution;
    }
}