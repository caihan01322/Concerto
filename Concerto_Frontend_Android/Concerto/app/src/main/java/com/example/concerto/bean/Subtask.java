package com.example.concerto.bean;

import java.util.List;

public class Subtask {
    public Long taskId;
    public String taskTitle;
    public String taskStatus;
    public List<User> participants;
    public Subtask(Long taskId,String taskTitle,String taskStatus,List<User> participants){
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskStatus = taskStatus;
        this.participants = participants;
    }
}
