/**
  * Copyright 2021 bejson.com 
  */
package com.example.concerto.bean;
import java.util.List;

public class SubTasks {

    private int taskId;
    private String taskTitle;
    private int taskStatus;
    private List<Participants> participants;
    public void setTaskId(int taskId) {
         this.taskId = taskId;
     }
    public int getTaskId() {
         return taskId;
     }

    public void setTaskTitle(String taskTitle) {
         this.taskTitle = taskTitle;
     }
    public String getTaskTitle() {
         return taskTitle;
     }

    public void setTaskStatus(int taskStatus) {
         this.taskStatus = taskStatus;
     }
    public int getTaskStatus() {
         return taskStatus;
     }

    public void setParticipants(List<Participants> participants) {
         this.participants = participants;
    }
    public List<Participants> getParticipants() {
         return participants;
     }

}