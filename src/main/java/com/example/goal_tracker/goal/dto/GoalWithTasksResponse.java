package com.example.goal_tracker.goal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GoalWithTasksResponse extends GoalResponse {

    private List<TaskResponse> tasks;
}