package com.aufgabe.danyil.test.repository;

import com.aufgabe.danyil.test.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


    Page<Task> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    Optional<Task> findByIdAndUserId(Long id, Long userId);

    Page<Task> findByUserIdAndTitleContainingIgnoreCaseOrderByCreatedAtDesc(
            Long userId, String title, Pageable pageable);

    Page<Task> findByUserIdAndCompletedOrderByUpdatedAtDesc(Long userId, Boolean completed, Pageable pageable);

    long countByUserId(Long userId);

    long countByUserIdAndCompleted(Long userId, Boolean completed);

    List<Task> findByUserIdAndCreatedAtAfter(Long userId, LocalDateTime date);

    void deleteAllByUserId(Long userId);

    @Query("SELECT t FROM Task t WHERE t.userId = :userId AND t.completed = false ORDER BY t.createdAt DESC")
    Page<Task> findPendingTasksByUserId(@Param("userId") Long userId, Pageable pageable);
}