package com.example.goal_tracker.goal.service;

import com.example.goal_tracker.goal.dto.GoalRequest;
import com.example.goal_tracker.goal.dto.PaginationRequest;
import com.example.goal_tracker.goal.model.Goal;
import org.springframework.data.domain.Page;

public interface GoalService {

    Page<Goal> findGoals(PaginationRequest paginationRequest);

    Goal findGoal(Long goalId);

    Goal addGoal(Goal goal, GoalRequest goalRequest);
}