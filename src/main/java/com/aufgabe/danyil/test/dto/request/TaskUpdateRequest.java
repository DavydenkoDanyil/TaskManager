package com.aufgabe.danyil.test.dto.request;

import jakarta.validation.constraints.Size;

public class TaskUpdateRequest {

    @Size(max = 255, message = "The title must not exceed 255 characters.")
    private String title;

    @Size(max = 1000, message = "The description must not exceed 1000 characters.")
    private String description;

    private Boolean completed;

    public TaskUpdateRequest() {}

    public TaskUpdateRequest(String title, String description, Boolean completed) {
        this.title = title;
        this.description = description;
        this.completed = completed;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Boolean getCompleted() { return completed; }
    public void setCompleted(Boolean completed) { this.completed = completed; }
}
