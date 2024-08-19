package com.brainyfools.upskillenglish.quick_practice.controller;

import com.brainyfools.upskillenglish.quick_practice.service.ImproveWritingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ImproveWritingController {

    ImproveWritingService improveWritingService;

    public ImproveWritingController(ImproveWritingService improveWritingService) {
        this.improveWritingService = improveWritingService;
    }

    @PostMapping("/judge")
    public ResponseEntity<?> judgeWriting (@RequestBody Map<String, String> userInput) {
        String input1 = userInput.get("question");
        String input2 = userInput.get("answer");
        return improveWritingService.judgeWriting(input1, input2);
    }


}