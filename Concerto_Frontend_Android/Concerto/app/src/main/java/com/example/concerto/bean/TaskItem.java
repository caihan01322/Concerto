package com.example.concerto.bean;

import java.util.List;

public class TaskItem {
    private  int complete=0;
    private String taskTitle;
    private String taskProgres;
    public List<TagsItem> tags;
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

    public void setTags(List<TagsItem> tags) {
        this.tags = tags;
    }

    public List<TagsItem> getTags() {
        return tags;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public void setNames(String[] names) {
        this.names = names;
    }


    public void setTaskProgres(String taskProgres) {
        this.taskProgres = taskProgres;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }
}
