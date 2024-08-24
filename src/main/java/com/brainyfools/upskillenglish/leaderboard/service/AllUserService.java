package com.brainyfools.upskillenglish.leaderboard.service;

import com.brainyfools.upskillenglish.auth.model.User;
import com.brainyfools.upskillenglish.auth.repository.UserRepository;
import com.brainyfools.upskillenglish.leaderboard.model.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllUserService {

    private final UserRepository userRepository;

    public AllUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAllByOrderByXpDesc(pageable);

        List<UserResponse> userResponseList = userPage.stream()
                .map(user -> new UserResponse(user.getUsername(), user.getXp()))
                .toList();
        return new ResponseEntity<>(userResponseList, HttpStatus.OK);
    }
}
