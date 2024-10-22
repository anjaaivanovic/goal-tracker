package com.example.goal_tracker.goal.service.impl;

import com.example.goal_tracker.auth.exception.UserNotFoundException;
import com.example.goal_tracker.auth.model.User;
import com.example.goal_tracker.auth.repository.UserRepository;
import com.example.goal_tracker.goal.dto.GoalRequest;
import com.example.goal_tracker.goal.dto.PaginationRequest;
import com.example.goal_tracker.goal.exception.GoalNotFoundException;
import com.example.goal_tracker.goal.model.Goal;
import com.example.goal_tracker.goal.model.Status;
import com.example.goal_tracker.goal.repository.GoalRepository;
import com.example.goal_tracker.goal.service.GoalService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;

    private final UserRepository userRepository;

    @Override
    public Page<Goal> findGoals(PaginationRequest paginationRequest) {

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

    @Override
    public Goal findGoal(Long goalId) {

        return goalRepository.findByIdWithTasks(goalId).orElseThrow(GoalNotFoundException::new);
    }

    @Override
    public Goal addGoal(Goal goal, GoalRequest goalRequest) {

        User assignee = userRepository.findById(goalRequest.getAssigneeId()).orElseThrow(UserNotFoundException::new);
        User createdBy = userRepository.findById(goalRequest.getCreatedById()).orElseThrow(UserNotFoundException::new);

        goal.setAssignee(assignee);
        goal.setCreatedBy(createdBy);
        goal.setDateCreated(LocalDate.now());
        goal.setStatus(Status.TO_DO);

        return goalRepository.save(goal);
    }
}