package com.brainyfools.upskillenglish.gemini;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestBody {
    private List<Content> contents;
    private final GenerationConfig generationConfig = new GenerationConfig("application/json",
            0.85, 0.95, 64, 8192);

    public void setPrompt(String prompt) {
        this.contents = List.of(new Content(List.of(new Part(prompt))));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Content {
        private List<Part> parts;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Part {
        private String text;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GenerationConfig {
        private String responseMimeType;
        private Double temperature;
        private Double topP;
        private Integer topK;
        private Integer maxOutputTokens;
    }
}