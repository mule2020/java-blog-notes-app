package com.mulecode.model;

import java.time.LocalDateTime;

public class Note {

    private int id;
    private String title;
    private String content;
    private int userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Note() {}

    public Note(String title, String content, int userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Note{id=" + id + ", title='" + title + "', userId=" + userId + "}";
    }
}