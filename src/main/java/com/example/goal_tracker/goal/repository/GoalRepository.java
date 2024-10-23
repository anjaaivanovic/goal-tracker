package com.example.goal_tracker.goal.repository;

import com.example.goal_tracker.goal.model.Goal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    Page<Goal> findByAssigneeIdAndTitleContaining(Long assigneeId, String title, Pageable pageable);

    Page<Goal> findByAssigneeId(Long assigneeId, Pageable pageable);

    @Query("SELECT g FROM Goal g LEFT JOIN FETCH g.tasks WHERE g.id = :id")
    Optional<Goal> findByIdWithTasks(@Param("id") Long id);

    @Query("SELECT g FROM Goal g JOIN FETCH g.assignee u WHERE g.deadline BETWEEN :startDate AND :endDate AND g.status <> 2")
    List<Goal> findGoalsBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT g FROM Goal g JOIN FETCH g.assignee u WHERE g.deadline < :date AND g.status <> 2")
    List<Goal> findGoalsBeforeDate(@Param("date") LocalDate date);
}