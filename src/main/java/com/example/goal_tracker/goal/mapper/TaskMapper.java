package com.example.goal_tracker.goal.mapper;

import com.example.goal_tracker.goal.dto.TaskRequest;
import com.example.goal_tracker.goal.dto.TaskResponse;
import com.example.goal_tracker.goal.model.Task;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskResponse toTaskResponse(Task task);

    List<TaskResponse> toTaskResponses(List<Task> tasks);

    Task toTask(TaskRequest taskRequest);

    List<Task> toTasks(List<TaskRequest> taskRequests);
}