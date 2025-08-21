package com.aufgabe.danyil.test.entity;

public enum TaskStatus {
    PENDING("Waiting"),
    IN_PROGRESS("В работе"),
    COMPLETED("Завершено"),
    CANCELLED("Отменено");

    private final String displayName;

    TaskStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
