package com.brainyfools.upskillenglish.gemini;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeminiService {

    @Value("${api.gemini.url}")
    private String apiUrl;

    @Value("${api.gemini.key}")
    private String apiKey;

    public <T> T call(String prompt, Class<T> responseType) {
        String fullApiUrl = String.format(apiUrl, apiKey);

        RestTemplate restTemplate = new RestTemplate();

        // This is essential for the server to correctly parse the request and handle the data appropriately.
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        RequestBody requestBody = new RequestBody();
        requestBody.setPrompt(prompt);

        // Complete Request Setup and Interaction with the API
        HttpEntity<RequestBody> httpEntity = new HttpEntity<>(requestBody, headers);

        ObjectMapper objectMapper = new ObjectMapper();
        T responseObj = null;

        ResponseBody responseBody = restTemplate.postForObject(fullApiUrl, httpEntity, ResponseBody.class);

        try {
            JsonNode rootNode = objectMapper.readTree(
                    responseBody.getCandidates().get(0).getContent().getParts().get(0).getText());
            responseObj = objectMapper.treeToValue(rootNode, responseType);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return responseObj;
    }
}