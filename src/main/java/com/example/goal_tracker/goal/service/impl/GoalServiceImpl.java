package com.example.goal_tracker.goal.service.impl;

import com.example.goal_tracker.goal.dto.PaginationRequest;
import com.example.goal_tracker.goal.model.Goal;
import com.example.goal_tracker.goal.repository.GoalRepository;
import com.example.goal_tracker.goal.service.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;

    @Override
    public Page<Goal> getGoals(PaginationRequest paginationRequest) {

        Sort sort = paginationRequest.getDirection().equalsIgnoreCase("asc")
                ? Sort.by(paginationRequest.getSortBy()).ascending()
                : Sort.by(paginationRequest.getSortBy()).descending();

        Pageable pageable = PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize(), sort);

        if (paginationRequest.getSearch() != null && !paginationRequest.getSearch().isEmpty()) {
            return goalRepository.findByAssigneeIdAndTitleContaining(paginationRequest.getUserId(), paginationRequest.getSearch(), pageable);
        } else {
            return goalRepository.findByAssigneeId(paginationRequest.getUserId(), pageable);
        }
    }
}