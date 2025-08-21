package com.aufgabe.danyil.test.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "The title must not be empty")
    @Size(max = 255, message = "The maximum text length is 255 characters.")
    private String title;

    @Column(columnDefinition = "TEXT")
    @Size(max = 1000, message = "The maximum text length is 1000 characters.")
    private String descriptions;

    @Column(nullable = false)
    private Boolean completed = false;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public Task() {
    }

    public Task(String title, String descriptions, Long userId) {
        this.title = title;
        this.descriptions = descriptions;
        this.userId = userId;
        this.completed = false;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getDescription() {return descriptions;}
    public void setDescription(String descriptions) {this.descriptions = descriptions;}

    public Boolean getCompleted() {return completed;}
    public void setCompleted(Boolean completed) {this.completed = completed;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public LocalDateTime getUpdatedAt() {return updatedAt;}
    public void setUpdatedAt(LocalDateTime updatedAt) {this.updatedAt = updatedAt;}

    public Long getUserId() {return userId;}
    public void setUserId(Long userId) {this.userId = userId;}
}

