package com.example.concerto.bean;

import java.util.List;

public class Task {
    public Long taskId;                 //任务id
    public String taskTitle;            //任务名称
    public Integer taskVersion;         //任务版本
    public String taskPriority;         //任务优先级
    public String taskType;             //任务种类
    public String taskStartTime;        //任务开始时间
    public String taskEndTime;          //任务结束时间
    public String taskStatus;           //任务状态
    public List<Tag> tags;              //标签
    public List<User> participants;     //参与者
    public List<Subtask> subTasks;      //子任务
    public List<TaskComment> comments;  //评论
}
