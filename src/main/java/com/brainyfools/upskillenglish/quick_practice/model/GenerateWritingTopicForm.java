package com.brainyfools.upskillenglish.quick_practice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateWritingTopicForm {
    private List<Topics> topicList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Topics {
        private String topic;
    }
}