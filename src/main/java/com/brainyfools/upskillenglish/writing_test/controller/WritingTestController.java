package com.brainyfools.upskillenglish.writing_test.controller;

import com.brainyfools.upskillenglish.writing_test.model.SolutionDto;
import com.brainyfools.upskillenglish.writing_test.service.WritingTestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class WritingTestController {

    private static final Logger LOGGER = LogManager.getLogger(WritingTestController.class);
    private final WritingTestService writingTestService;

    public WritingTestController(WritingTestService writingTestService) {
        this.writingTestService = writingTestService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/v1/generateGraph")
    public ResponseEntity<?> generateGraph() {
        LOGGER.info("Generating graph...");
        return writingTestService.generateGraph();
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/v1/writtenTestSolutionSubmit")
    public ResponseEntity<?> solutionSubmit(@RequestBody SolutionDto solutionDto,
                                            Authentication authentication) {
        LOGGER.info("Call solution submit: {}", solutionDto);
        return writingTestService.getScore(solutionDto, authentication);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/v1/generatePassageTest")
    public ResponseEntity<?> generatePassageTest() {
        return writingTestService.generatePassageTest();
    }
}
