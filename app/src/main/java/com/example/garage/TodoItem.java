package com.example.garage;

public class TodoItem {
    private int id;
    private String title;
    private String date;
    private int isCompleted; // 0: Chưa làm, 1: Đã làm

    public TodoItem(int id, String title, String date, int isCompleted) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.isCompleted = isCompleted;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public int getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(int isCompleted) {
        this.isCompleted = isCompleted;
    }
}