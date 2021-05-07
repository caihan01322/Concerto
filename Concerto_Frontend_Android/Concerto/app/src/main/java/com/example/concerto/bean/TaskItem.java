package com.example.concerto.bean;

import java.util.List;

public class TaskItem {
    private String taskTitle;
    private String taskProgres;
    private String[] tags;
    private int days;
    private String[] names;


    public TaskItem(){}

    public int getDays() {
        return days;
    }

    public String getTaskProgres() {
        return taskProgres;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public String[] getNames() {
        return names;
    }

    public String[] getTags() {
        return tags;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public void setTaskProgres(String taskProgres) {
        this.taskProgres = taskProgres;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }



}
