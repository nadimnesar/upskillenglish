package com.brainyfools.upskillenglish.improve_writing.controller;

import com.brainyfools.upskillenglish.improve_writing.service.ImproveWritingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ImproveWritingController {

    ImproveWritingService improveWritingService;

    public ImproveWritingController(ImproveWritingService improveWritingService) {
        this.improveWritingService = improveWritingService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/v1/judge-writing")
    public ResponseEntity<?> judgeWriting(@RequestBody Map<String, String> userInput) {
        String input1 = userInput.get("question");
        String input2 = userInput.get("answer");
        return improveWritingService.judgeWriting(input1, input2);
    }
}