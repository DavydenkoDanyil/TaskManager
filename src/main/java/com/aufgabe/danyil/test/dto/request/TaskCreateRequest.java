package com.aufgabe.danyil.test.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TaskCreateRequest {

    @NotBlank(message = "The title must not be empty")
    @Size(max = 255, message = "The title must not exceed 255 characters.")
    private String title;

    @Size(max = 1000, message = "The description must not exceed 1000 characters.")
    private String description;

    public TaskCreateRequest() {}

    public TaskCreateRequest(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}