package com.example.enigma.task_2.controller;

import com.example.enigma.task_2.model.SortDirection;
import com.example.enigma.task_2.model.TaskStatus;
import com.example.enigma.task_2.model.entity.user_dto.UserTaskActionRequest;
import com.example.enigma.task_2.model.entity.task_dto.TaskDto;
import com.example.enigma.task_2.model.entity.task_dto.TaskWithoutIdDto;
import com.example.enigma.task_2.model.entity.task_dto.TaskWithoutUserDto;
import com.example.enigma.task_2.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/detailed")
    public List<TaskDto> getDetailedTasks(
            @RequestParam(name = "user_id", required = false) Long userId,
            @RequestParam(name = "status", required = false) TaskStatus status,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "sort", required = false, defaultValue = "false" ) boolean sort,
            @RequestParam(name = "sort_direction", required = false, defaultValue = "ASC") SortDirection sortDirection) {
        return taskService.findAllDetailed(userId, status, page, sort, sortDirection);
    }

    @GetMapping("/basic")
    public List<TaskWithoutUserDto> getBasicTasks(
            @RequestParam(name = "user_id", required = false) Long userId,
            @RequestParam(name = "status", required = false) TaskStatus status,
            @RequestParam(name = "sort", required = false, defaultValue = "false" ) boolean sort,
            @RequestParam(name = "sort_direction", required = false, defaultValue = "ASC") SortDirection sortDirection){
        return taskService.findAllBasic(userId, status, sort, sortDirection);
    }

    @GetMapping("/unsigned")
    public List<TaskWithoutUserDto> getUnsignedTasks(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "sort", required = false, defaultValue = "false" ) boolean sort,
            @RequestParam(name = "sort_direction", required = false, defaultValue = "ASC") SortDirection sortDirection) {
        return taskService.findUnsigned(page, sort, sortDirection);
    }

    @GetMapping("/{id}")
    public TaskDto getTask(@PathVariable("id") Long id) {
        return taskService.findTaskById(id);
    }

    @GetMapping("/titles/{title}")
    public TaskDto getTaskByName(@PathVariable("title") String title) {
        return taskService.findTaskByTitle(title);
    }

    @PostMapping
    public TaskDto crateTask(@RequestBody TaskWithoutIdDto newTask){
        return taskService.create(newTask);
    }

    @PutMapping("/{id}")
    public TaskDto editTask(@PathVariable("id") Long id, @RequestBody TaskWithoutIdDto updatedTask){
        return taskService.update(id, updatedTask);
    }

    @PatchMapping("/{id}/status")
    public TaskDto changeTaskStatus(@PathVariable("id") Long id, @RequestBody TaskStatus taskStatus) {
        return taskService.changeStatus(id, taskStatus);
    }

    @PatchMapping("/{id}/users")
    public TaskDto modifyUserAssignmentToTask(@PathVariable("id") Long id, @RequestBody UserTaskActionRequest userTaskActionRequest) {
        return taskService.modifyUserAssignmentToTask(id, userTaskActionRequest);
    }

    @DeleteMapping("/{id}")
    public void removeTask(@PathVariable("id") Long id) {
        taskService.delete(id);
    }
}