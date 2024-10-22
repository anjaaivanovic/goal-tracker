package com.example.goal_tracker.goal.mapper;

import com.example.goal_tracker.goal.dto.GoalResponse;
import com.example.goal_tracker.goal.model.Goal;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GoalMapper {

    GoalResponse toGoalResponse(Goal goal);
    List<GoalResponse> toGoalResponses(List<Goal> goals);
}