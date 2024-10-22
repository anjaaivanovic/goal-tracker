package com.example.goal_tracker.goal.repository;

import com.example.goal_tracker.goal.model.Goal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    Page<Goal> findByAssigneeIdAndTitleContaining(Long assigneeId, String title, Pageable pageable);
    Page<Goal> findByAssigneeId(Long assigneeId, Pageable pageable);
}