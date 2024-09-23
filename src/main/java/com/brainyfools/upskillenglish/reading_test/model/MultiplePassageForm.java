package com.brainyfools.upskillenglish.reading_test.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultiplePassageForm {

    private List<Passage> psList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Passage {
        @JsonProperty("A")
        private String a;

        @JsonProperty("B")
        private String b;

        @JsonProperty("C")
        private String c;

        @JsonProperty("D")
        private String d;

        @JsonProperty("E")
        private String e;
    }
}
