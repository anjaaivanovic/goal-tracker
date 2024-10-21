package com.example.goal_tracker.goal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/team")
@PreAuthorize("hasAnyRole('TEAM_MEMBER', 'PERSONAL_USER')")
public class GoalController {

    @GetMapping
    public ResponseEntity<String> getGoal() {
        return ResponseEntity.ok("Goal Tracker");
    }
}