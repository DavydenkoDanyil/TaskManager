package com.aufgabe.danyil.test.service;

import com.aufgabe.danyil.test.dto.request.TaskCreateRequest;
import com.aufgabe.danyil.test.dto.request.TaskUpdateRequest;
import com.aufgabe.danyil.test.dto.response.PagedTaskResponse;
import com.aufgabe.danyil.test.dto.response.TaskResponse;
import com.aufgabe.danyil.test.entity.Task;
import com.aufgabe.danyil.test.exception.TaskNotFoundException;
import com.aufgabe.danyil.test.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public PagedTaskResponse getAllTasks(Long userId, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Task> taskPage = taskRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);

        return mapToPagedResponse(taskPage);
    }


    public PagedTaskResponse getTasksByStatus(Long userId, Boolean completed, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> taskPage = taskRepository.findByUserIdAndCompletedOrderByUpdatedAtDesc(
                userId, completed, pageable);

        return mapToPagedResponse(taskPage);
    }


    public PagedTaskResponse searchTasks(Long userId, String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> taskPage = taskRepository.findByUserIdAndTitleContainingIgnoreCaseOrderByCreatedAtDesc(
                userId, query, pageable);

        return mapToPagedResponse(taskPage);
    }


    public TaskResponse getTaskById(Long taskId, Long userId) {
        Task task = taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found" + taskId));

        return new TaskResponse(task);
    }


    public TaskResponse createTask(TaskCreateRequest request, Long userId) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setUserId(userId);
        task.setCompleted(false);

        Task savedTask = taskRepository.save(task);
        return new TaskResponse(savedTask);
    }


    public TaskResponse updateTask(Long taskId, TaskUpdateRequest request, Long userId) {
        Task task = taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new TaskNotFoundException("Задача не найдена с ID: " + taskId));

        // Обновляем только переданные поля
        if (request.getTitle() != null && !request.getTitle().trim().isEmpty()) {
            task.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }

        if (request.getCompleted() != null) {
            task.setCompleted(request.getCompleted());
        }

        Task updatedTask = taskRepository.save(task);
        return new TaskResponse(updatedTask);
    }


    public TaskResponse markTaskAsCompleted(Long taskId, Long userId) {
        Task task = taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new TaskNotFoundException("Задача не найдена с ID: " + taskId));

        task.setCompleted(true);
        Task updatedTask = taskRepository.save(task);
        return new TaskResponse(updatedTask);
    }


    public TaskResponse markTaskAsIncomplete(Long taskId, Long userId) {
        Task task = taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new TaskNotFoundException("Задача не найдена с ID: " + taskId));

        task.setCompleted(false);
        Task updatedTask = taskRepository.save(task);
        return new TaskResponse(updatedTask);
    }


    public void deleteTask(Long taskId, Long userId) {
        Task task = taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new TaskNotFoundException("Задача не найдена с ID: " + taskId));

        taskRepository.delete(task);
    }


    public TaskStatistics getTaskStatistics(Long userId) {
        long totalTasks = taskRepository.countByUserId(userId);
        long completedTasks = taskRepository.countByUserIdAndCompleted(userId, true);
        long pendingTasks = totalTasks - completedTasks;

        return new TaskStatistics(totalTasks, completedTasks, pendingTasks);
    }

    private PagedTaskResponse mapToPagedResponse(Page<Task> taskPage) {
        List<TaskResponse> taskResponses = taskPage.getContent()
                .stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());

        return new PagedTaskResponse(
                taskResponses,
                taskPage.getNumber(),
                taskPage.getTotalPages(),
                taskPage.getTotalElements(),
                taskPage.getSize(),
                taskPage.isFirst(),
                taskPage.isLast(),
                taskPage.isEmpty()
        );
    }

    public static class TaskStatistics {
        private final long totalTasks;
        private final long completedTasks;
        private final long pendingTasks;

        public TaskStatistics(long totalTasks, long completedTasks, long pendingTasks) {
            this.totalTasks = totalTasks;
            this.completedTasks = completedTasks;
            this.pendingTasks = pendingTasks;
        }

        public long getTotalTasks() { return totalTasks; }
        public long getCompletedTasks() { return completedTasks; }
        public long getPendingTasks() { return pendingTasks; }
    }
}