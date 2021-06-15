/**
  * Copyright 2021 bejson.com 
  */
package com.example.concerto.bean;
import java.util.Date;
import java.util.List;

public class Data {

    private int taskId;
    private int taskVersion;
    private String taskTitle;
    private int taskPriority;
    private int taskStatus;
    private int taskType;
    private Date taskStartTime;
    private Date taskEndTime;
    private List<Tags> tags;
    private List<Participants> participants;
    private List<SubTasks> subTasks;
    public void setTaskId(int taskId) {
         this.taskId = taskId;
     }
    public int getTaskId() {
         return taskId;
     }

    public void setTaskVersion(int taskVersion) {
         this.taskVersion = taskVersion;
     }
    public int getTaskVersion() {
         return taskVersion;
     }

    public void setTaskTitle(String taskTitle) {
         this.taskTitle = taskTitle;
     }
    public String getTaskTitle() {
         return taskTitle;
     }

    public void setTaskPriority(int taskPriority) {
         this.taskPriority = taskPriority;
     }
    public int getTaskPriority() {
         return taskPriority;
     }

    public void setTaskStatus(int taskStatus) {
         this.taskStatus = taskStatus;
     }
    public int getTaskStatus() {
         return taskStatus;
     }

    public void setTaskType(int taskType) {
         this.taskType = taskType;
     }
    public int getTaskType() {
         return taskType;
     }

    public void setTaskStartTime(Date taskStartTime) {
         this.taskStartTime = taskStartTime;
     }
    public Date getTaskStartTime() {
         return taskStartTime;
     }

    public void setTaskEndTime(Date taskEndTime) {
         this.taskEndTime = taskEndTime;
     }
    public Date getTaskEndTime() {
         return taskEndTime;
     }

    public void setTags(List<Tags> tags) {
         this.tags = tags;
     }
    public List<Tags> getTags() {
         return tags;
     }

    public void setParticipants(List<Participants> participants) {
         this.participants = participants;
    }
    public List<Participants> getParticipants() {
         return participants;
     }

    public void setSubTasks(List<SubTasks> subTasks) {
         this.subTasks = subTasks;
     }
    public List<SubTasks> getSubTasks() {
         return subTasks;
     }

}