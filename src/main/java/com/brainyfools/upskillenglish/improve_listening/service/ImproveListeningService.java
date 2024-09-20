package com.brainyfools.upskillenglish.improve_listening.service;

import com.brainyfools.upskillenglish.gemini.GeminiService;
import com.brainyfools.upskillenglish.improve_listening.model.TextModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ImproveListeningService {

    private static final Logger LOGGER = LogManager.getLogger(ImproveListeningService.class);
    private final GeminiService geminiService;

    @Value("${api.huggingface.key}")
    private String huggingFaceKey;

    public ImproveListeningService(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    public ResponseEntity<?> generateListeningText() {
        String prompt = """
                Task: Generate a practice text of approximately 300 words in JSON format for IELTS listening exam preparation.
                Format:
                {
                    "text": "String"
                }
                Requirements:
                - The text should simulate an IELTS Listening exam passage.
                - Structure the text as either a **conversation** (between two or more people) or a **monologue** (such as a lecture, tour guide, or announcement).
                - Provide context for the conversation or monologue, such as where it is taking place (e.g., in a cafe, at a university, or during a tour).
                - Incorporate specific **details**, such as dates, times, locations, prices, or schedules. Include enough detail to test the listener's ability to extract relevant information.
                - Use a variety of formal and informal language styles to reflect different IELTS sections (e.g., social conversations vs. academic lectures).
                - Choose topics commonly found in IELTS exams, such as **travel**, **education**, **work**, **health**, and **everyday life**.
                - Include different accents or mention speakers from different countries to simulate the variety of accents in the IELTS Listening test.
                - Avoid repetitive text and ensure the passage is well-structured, with clear transitions between ideas or speakers.
                - Replace "String" with the appropriate passage content.
                """;
        TextModel text = geminiService.call(prompt, TextModel.class);
        return new ResponseEntity<>(text, HttpStatus.CREATED);
    }

    public ResponseEntity<?> generateListeningAudio(String text) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + huggingFaceKey);

        String apiUrl = "https://api-inference.huggingface.co/models/facebook/mms-tts-eng";

        String requestBody = "{\"inputs\": \"" + text + "\"}";

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        for (int i = 1; i <= 10; i++) {
            try {
                LOGGER.info("{}'th attempt to generate audio!", i);
                ResponseEntity<byte[]> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, byte[].class);
                if (response.getStatusCode() == HttpStatus.OK) {
                    return response;
                }
            } catch (HttpServerErrorException e) {
                LOGGER.info("Audio generate failed. Error: {}", e.getMessage());
                if (e.getStatusCode().isSameCodeAs(HttpStatus.SERVICE_UNAVAILABLE)) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        LOGGER.error("Retry interrupted: {}", ie.getMessage());
                    }
                } else {
                    LOGGER.info("Request failed with status code: {}", e.getStatusCode());
                    return ResponseEntity.internalServerError().build();
                }
            }
        }
        return ResponseEntity.internalServerError().build();
    }
}
