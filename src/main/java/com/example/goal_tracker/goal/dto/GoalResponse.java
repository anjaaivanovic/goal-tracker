package com.example.goal_tracker.goal.dto;

import com.example.goal_tracker.goal.model.Status;

import java.time.LocalDate;

public class GoalResponse {

    private Long id;

    private String title;

    private String description;

    private Status status;

    private LocalDate dateCreated;

    private LocalDate deadline;
}