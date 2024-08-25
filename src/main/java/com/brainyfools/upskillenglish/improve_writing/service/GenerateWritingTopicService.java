package com.brainyfools.upskillenglish.improve_writing.service;

import com.brainyfools.upskillenglish.gemini.GeminiService;
import com.brainyfools.upskillenglish.improve_writing.model.GenerateWritingTopicForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GenerateWritingTopicService {

    GeminiService geminiService;

    public GenerateWritingTopicService(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    public ResponseEntity<?> generateWritingTopic() {

        String prompt = String.format("""
                     Provide an IELTS writing topic using the JSON format below. And Don't repeat the same response. Generate only one single topic.
                    {
                    "topicList":
                    [
                        {
                          "topic": "String"
                        }
                    ]
                }
                """);
        GenerateWritingTopicForm generateWritingTopicForm = geminiService.call(prompt, GenerateWritingTopicForm.class);
        return new ResponseEntity<>(generateWritingTopicForm, HttpStatus.CREATED);
    }
}
