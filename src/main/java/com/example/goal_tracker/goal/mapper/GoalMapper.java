package com.example.goal_tracker.goal.mapper;

import com.example.goal_tracker.goal.dto.GoalRequest;
import com.example.goal_tracker.goal.dto.GoalResponse;
import com.example.goal_tracker.goal.dto.GoalWithTasksResponse;
import com.example.goal_tracker.goal.model.Goal;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = TaskMapper.class)
public interface GoalMapper {

    GoalResponse toGoalResponse(Goal goal);

    List<GoalResponse> toGoalResponses(List<Goal> goals);

    GoalWithTasksResponse toGoalWithTasksResponse(Goal goal);

    Goal toGoal(GoalRequest goalRequest);
}