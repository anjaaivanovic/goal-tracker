package com.example.goal_tracker.goal.controller;

import com.example.goal_tracker.goal.dto.TaskResponse;
import com.example.goal_tracker.goal.dto.TasksRequest;
import com.example.goal_tracker.goal.mapper.TaskMapper;
import com.example.goal_tracker.goal.model.Task;
import com.example.goal_tracker.goal.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    private final TaskMapper taskMapper;

    @PreAuthorize("hasAnyRole('TEAM_LEAD', 'PERSONAL_USER')")
    @PostMapping
    public ResponseEntity<List<TaskResponse>> addTask(
            @Valid @RequestBody TasksRequest tasksRequest) {

        List<Task> tasks = taskMapper.toTasks(tasksRequest.getTaskRequests());
        List<TaskResponse> taskResponses = taskMapper.toTaskResponses(taskService.addTasks(tasks, tasksRequest));

        return ResponseEntity.ok(taskResponses);
    }

    @PreAuthorize("hasAnyRole('TEAM_LEAD', 'PERSONAL_USER')")
    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId) {

        taskService.deleteTask(taskId);

        return ResponseEntity.ok("Task successfully deleted");
    }

    @PreAuthorize("hasAnyRole('TEAM_LEAD', 'TEAM_MEMBER', 'PERSONAL_USER')")
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTaskStatus(@PathVariable Long taskId, @RequestParam boolean forwards) {

        return ResponseEntity.ok(taskMapper.toTaskResponse(taskService.updateTaskStatus(taskId, forwards)));
    }
}