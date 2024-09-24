package com.brainyfools.upskillenglish.leaderboard.controller;

import com.brainyfools.upskillenglish.leaderboard.service.AllUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class AllUserController {

    private final AllUserService allUserService;

    public AllUserController(AllUserService allUserService) {
        this.allUserService = allUserService;
    }

    @GetMapping("/v1/get-all-user")
    public ResponseEntity<?> getAllUser(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        return allUserService.getAllUsers(page, size);
    }
}
