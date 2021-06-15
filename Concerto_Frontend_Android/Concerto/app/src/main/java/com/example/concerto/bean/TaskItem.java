package com.example.concerto.bean;

import java.util.List;

public class TaskItem {
    private String taskId;
    private  int complete=0;
    private String taskTitle;
    private String taskProgres;
    String subTaskNum;
    String subTaskCompletedNum;
    public List<TagsItem> tags;
    private int days;
    private List<String> names;


    public String getSubTaskCompletedNum() {
        return subTaskCompletedNum;
    }

    public String getSubTaskNum() {
        return subTaskNum;
    }

    public void setSubTaskCompletedNum(String subTaskCompletedNum) {
        this.subTaskCompletedNum = subTaskCompletedNum;
    }

    public void setSubTaskNum(String subTaskNum) {
        this.subTaskNum = subTaskNum;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

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

    public List<String> getNames() {
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

    public void setNames(List<String> names) {
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
