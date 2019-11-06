package ru.geekbrains.service;

import java.util.Date;

public class ToDoRepr {

    private Long id;

    private String description;

    private Date targetDate;

    public ToDoRepr() {
    }

    public ToDoRepr(Long id, String description, Date targetDate) {
        this.id = id;
        this.description = description;
        this.targetDate = targetDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }
}
