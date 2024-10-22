package com.example.goal_tracker.goal.controller;

import com.example.goal_tracker.goal.dto.GoalRequest;
import com.example.goal_tracker.goal.dto.GoalResponse;
import com.example.goal_tracker.goal.dto.GoalWithTasksResponse;
import com.example.goal_tracker.goal.dto.PaginationRequest;
import com.example.goal_tracker.goal.mapper.GoalMapper;
import com.example.goal_tracker.goal.model.Goal;
import com.example.goal_tracker.goal.service.GoalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goals")

@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    private final GoalMapper goalMapper;

    @PreAuthorize("hasAnyRole('TEAM_LEAD', 'PERSONAL_USER', 'TEAM_MEMBER')")
    @GetMapping
    public ResponseEntity<Page<GoalResponse>> findGoals(
            @AuthenticationPrincipal(expression = "id") Long userId,
            @Valid @ModelAttribute PaginationRequest paginationRequest
    ) {

        paginationRequest.setUserId(userId);
        Page<Goal> goalPage = goalService.findGoals(paginationRequest);
        List<GoalResponse> goalResponses = goalMapper.toGoalResponses(goalPage.getContent());

        return ResponseEntity.ok(new PageImpl<>(goalResponses, goalPage.getPageable(), goalPage.getTotalElements()));
    }

    @PreAuthorize("hasAnyRole('TEAM_LEAD', 'PERSONAL_USER', 'TEAM_MEMBER')")
    @GetMapping("/{goalId}")
    public ResponseEntity<GoalWithTasksResponse> findGoal(
            @AuthenticationPrincipal(expression = "id") Long userId,
            @PathVariable Long goalId
    ) {

        var mapped = goalMapper.toGoalWithTasksResponse(goalService.findGoal(goalId, userId));
        return ResponseEntity.ok(mapped);
    }

    @PreAuthorize("hasAnyRole('TEAM_LEAD', 'PERSONAL_USER')")
    @PostMapping
    public ResponseEntity<GoalResponse> addGoal(
            @AuthenticationPrincipal(expression = "id") Long userId,
            @Valid @RequestBody GoalRequest goalRequest
    ) {

        goalRequest.setCreatedById(userId);
        Goal newGoal = goalMapper.toGoal(goalRequest);
        GoalResponse response = goalMapper.toGoalResponse(goalService.addGoal(newGoal, goalRequest));

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('TEAM_LEAD', 'PERSONAL_USER')")
    @DeleteMapping("/{goalId}")
    public ResponseEntity<String> deleteGoal(
            @AuthenticationPrincipal(expression = "id") Long userId,
            @PathVariable Long goalId
    ) {

        goalService.deleteGoal(goalId, userId);

        return ResponseEntity.ok("Goal successfully deleted");
    }
}