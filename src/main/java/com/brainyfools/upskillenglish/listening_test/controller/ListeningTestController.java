package com.brainyfools.upskillenglish.listening_test.controller;
import com.brainyfools.upskillenglish.listening_test.service.ListeningTestService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ListeningTestController {
    private final ListeningTestService listeningTestService;

    public ListeningTestController(ListeningTestService listeningTestService) {
        this.listeningTestService = listeningTestService;
    }


    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/v1/generate-listening-test-qs")
    public ResponseEntity<?> createPassage(@RequestBody Map<String, String> requestBody) {
        String text = requestBody.get("text");
        return listeningTestService.createQuestionResponse(text);
    }


}
