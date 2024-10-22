package com.example.goal_tracker.goal.service;

import com.example.goal_tracker.goal.dto.TasksRequest;
import com.example.goal_tracker.goal.model.Task;

import java.util.List;

public interface TaskService {
    List<Task> addTasks(List<Task> tasks, TasksRequest tasksRequest);
    void deleteTask(Long taskId);
    Task updateTaskStatus(Long taskId, boolean forwards);
}