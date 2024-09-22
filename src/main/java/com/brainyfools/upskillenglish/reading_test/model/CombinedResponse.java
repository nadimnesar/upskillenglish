package com.brainyfools.upskillenglish.reading_test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CombinedResponse {
    private Object passage;
    private Object mcq;
    private Object opinionative;
    private Object factCheck;
}
