package com.brainyfools.upskillenglish.writing_test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraphData {
    private List<String> labels;
    private List<Integer> year1Values;
    private List<Integer> year2Values;
    private String year1Label;
    private String year2Label;
    private String writingPrompt;
    private String answer;
}
