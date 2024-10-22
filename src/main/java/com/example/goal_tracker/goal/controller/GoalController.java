package com.example.goal_tracker.goal.controller;

import com.example.goal_tracker.goal.dto.GoalResponse;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/goals")

@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    private final GoalMapper goalMapper;

    @PreAuthorize("hasAnyRole('TEAM_LEAD', 'PERSONAL_USER', 'TEAM_MEMBER')")
    @GetMapping
    public ResponseEntity<Page<GoalResponse>> getGoals(
            @AuthenticationPrincipal(expression = "id") Long userId,
            @Valid @ModelAttribute PaginationRequest paginationRequest) {

        paginationRequest.setUserId(userId);
        Page<Goal> goalPage = goalService.getGoals(paginationRequest);
        List<GoalResponse> goalResponses = goalMapper.toGoalResponses(goalPage.getContent());

        return ResponseEntity.ok(new PageImpl<>(goalResponses, goalPage.getPageable(), goalPage.getTotalElements()));
    }
}