package com.example.concerto.bean;

public class UserItem {
    String name;
    String mailbox;
    long userId;

    public UserItem(){}
    public UserItem(String name,String mailbox){
        this.name=name;
        this.mailbox=mailbox;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMailbox() {
        return mailbox;
    }

    public String getName() {
        return name;
    }
}
