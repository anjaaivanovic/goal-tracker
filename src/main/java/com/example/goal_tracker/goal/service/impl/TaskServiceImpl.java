package com.example.goal_tracker.goal.service.impl;

import com.example.goal_tracker.goal.dto.TasksRequest;
import com.example.goal_tracker.goal.exception.GoalNotFoundException;
import com.example.goal_tracker.goal.exception.TaskNotFoundException;
import com.example.goal_tracker.goal.model.Goal;
import com.example.goal_tracker.goal.model.Status;
import com.example.goal_tracker.goal.model.Task;
import com.example.goal_tracker.goal.repository.GoalRepository;
import com.example.goal_tracker.goal.repository.TaskRepository;
import com.example.goal_tracker.goal.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final GoalRepository goalRepository;

    @Override
    public List<Task> addTasks(List<Task> tasks, TasksRequest tasksRequest) {

        Goal goal = goalRepository.findById(tasksRequest.getParentGoalId()).orElseThrow(GoalNotFoundException::new);

        tasks.forEach(task -> {
            task.setDateCreated(LocalDate.now());
            task.setStatus(Status.TO_DO);
            task.setParentGoal(goal);
        });

        return taskRepository.saveAll(tasks);
    }

    @Override
    public void deleteTask(Long taskId) {
        if(!taskRepository.existsById(taskId)) {
            throw new TaskNotFoundException();
        }

        goalRepository.deleteById(taskId);
    }

    @Override
    public Task updateTaskStatus(Long taskId, boolean forwards) {

        Task task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);

        switch (task.getStatus()) {
            case TO_DO:
                if (forwards) {
                    task.setStatus(Status.IN_PROGRESS);
                }
                break;
            case IN_PROGRESS:
                task.setStatus(forwards ? Status.DONE : Status.TO_DO);
                break;
            case DONE:
                if (!forwards) {
                    task.setStatus(Status.IN_PROGRESS);
                }
                break;
            default:
                break;
        }

        return taskRepository.save(task);
    }
}