package com.example.goal_tracker.goal.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class TasksRequest {

    @NotNull(message = "Parent goal id is required.")
    private Long parentGoalId;

    @NotEmpty(message = "At least one task request is required.")
    private List<TaskRequest> taskRequests;
}