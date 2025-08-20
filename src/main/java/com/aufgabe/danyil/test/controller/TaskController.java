package com.aufgabe.danyil.test.controller;

import com.aufgabe.danyil.test.dto.request.TaskCreateRequest;
import com.aufgabe.danyil.test.dto.request.TaskUpdateRequest;
import com.aufgabe.danyil.test.dto.response.PagedTaskResponse;
import com.aufgabe.danyil.test.dto.response.TaskResponse;
import com.aufgabe.danyil.test.entity.User;
import com.aufgabe.danyil.test.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Get all tasks with pagination
     * GET /api/tasks?page=0&size=5&sortBy=createdAt&sortDir=desc
     */
    @GetMapping
    public ResponseEntity<PagedTaskResponse> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            Authentication authentication) {

        Long userId = getUserIdFromAuthentication(authentication);
        PagedTaskResponse response = taskService.getAllTasks(userId, page, size, sortBy, sortDir);
        return ResponseEntity.ok(response);
    }

    /**
     * Get tasks by status
     * GET /api/tasks/status?completed=true&page=0&size=5
     */
    @GetMapping("/status")
    public ResponseEntity<PagedTaskResponse> getTasksByStatus(
            @RequestParam Boolean completed,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Authentication authentication) {

        Long userId = getUserIdFromAuthentication(authentication);
        PagedTaskResponse response = taskService.getTasksByStatus(userId, completed, page, size);
        return ResponseEntity.ok(response);
    }

    /**
     * Search for tasks
     * GET /api/tasks/search?q=задача&page=0&size=5
     */
    @GetMapping("/search")
    public ResponseEntity<PagedTaskResponse> searchTasks(
            @RequestParam("q") String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Authentication authentication) {

        Long userId = getUserIdFromAuthentication(authentication);
        PagedTaskResponse response = taskService.searchTasks(userId, query, page, size);
        return ResponseEntity.ok(response);
    }

    /**
     * Get task by ID
     * GET /api/tasks/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(
            @PathVariable Long id,
            Authentication authentication) {

        Long userId = getUserIdFromAuthentication(authentication);
        TaskResponse response = taskService.getTaskById(id, userId);
        return ResponseEntity.ok(response);
    }

    /**
     * Create a new task
     * POST /api/tasks
     */
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskCreateRequest request,
            Authentication authentication) {

        Long userId = getUserIdFromAuthentication(authentication);
        TaskResponse response = taskService.createTask(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Update task
     * PUT /api/tasks/1
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskUpdateRequest request,
            Authentication authentication) {

        Long userId = getUserIdFromAuthentication(authentication);
        TaskResponse response = taskService.updateTask(id, request, userId);
        return ResponseEntity.ok(response);
    }

    /**
     * Mark task as completed
     * PATCH /api/tasks/1/complete
     */
    @PatchMapping("/{id}/complete")
    public ResponseEntity<TaskResponse> markTaskAsCompleted(
            @PathVariable Long id,
            Authentication authentication) {

        Long userId = getUserIdFromAuthentication(authentication);
        TaskResponse response = taskService.markTaskAsCompleted(id, userId);
        return ResponseEntity.ok(response);
    }

    /**
     * Mark task as uncompleted
     * PATCH /api/tasks/1/incomplete
     */
    @PatchMapping("/{id}/incomplete")
    public ResponseEntity<TaskResponse> markTaskAsIncomplete(
            @PathVariable Long id,
            Authentication authentication) {

        Long userId = getUserIdFromAuthentication(authentication);
        TaskResponse response = taskService.markTaskAsIncomplete(id, userId);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete task
     * DELETE /api/tasks/1
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteTask(
            @PathVariable Long id,
            Authentication authentication) {

        Long userId = getUserIdFromAuthentication(authentication);
        taskService.deleteTask(id, userId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Task successfully deleted");
        return ResponseEntity.ok(response);
    }

    /**
     * Get task statistics
     * GET /api/tasks/statistics
     */
    @GetMapping("/statistics")
    public ResponseEntity<TaskService.TaskStatistics> getTaskStatistics(Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        TaskService.TaskStatistics statistics = taskService.getTaskStatistics(userId);
        return ResponseEntity.ok(statistics);
    }

    /**
     * Extract user ID from authentication object
     */
    private Long getUserIdFromAuthentication(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }
}