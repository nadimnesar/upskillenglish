package com.brainyfools.upskillenglish.writing_test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolutionDto {
    private String question;
    private String correctAnswer;
    private String userAnswer;
}
