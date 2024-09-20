package com.brainyfools.upskillenglish.gemini;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseBody {
    private List<Candidate> candidates;
    private UsageMetadata usageMetadata;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Candidate {
        private Content content;
        private String finishReason;
        private int index;
        private List<SafetyRating> safetyRatings;

        @AllArgsConstructor
        @NoArgsConstructor
        @Data
        public static class Content {
            private List<Part> parts;
            private String role;

            @AllArgsConstructor
            @NoArgsConstructor
            @Data
            public static class Part {
                private String text;
            }
        }

        @AllArgsConstructor
        @NoArgsConstructor
        @Data
        public static class SafetyRating {
            private String category;
            private String probability;
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class UsageMetadata {
        private int promptTokenCount;
        private int candidatesTokenCount;
        private int totalTokenCount;
    }
}