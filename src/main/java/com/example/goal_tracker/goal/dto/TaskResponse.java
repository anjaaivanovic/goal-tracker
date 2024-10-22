package com.example.goal_tracker.goal.dto;

import com.example.goal_tracker.goal.model.Status;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskResponse {
    private Long id;

    private String title;

    private String description;

    private Status status;

    private LocalDate dateCreated;

    private LocalDate deadline;
}