/**
  * Copyright 2021 bejson.com 
  */
package com.example.concerto.bean;

public class Participants {

    private int userId;
    private String userPhone;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String userSalt;
    private String userIntroducton;
    public void setUserId(int userId) {
         this.userId = userId;
     }
    public int getUserId() {
         return userId;
     }

    public void setUserPhone(String userPhone) {
         this.userPhone = userPhone;
     }
    public String getUserPhone() {
         return userPhone;
     }

    public void setUserName(String userName) {
         this.userName = userName;
     }
    public String getUserName() {
         return userName;
     }

    public void setUserEmail(String userEmail) {
         this.userEmail = userEmail;
     }
    public String getUserEmail() {
         return userEmail;
     }

    public void setUserPassword(String userPassword) {
         this.userPassword = userPassword;
     }
    public String getUserPassword() {
         return userPassword;
     }

    public void setUserSalt(String userSalt) {
         this.userSalt = userSalt;
     }
    public String getUserSalt() {
         return userSalt;
    }

    public void setUserIntroducton(String userIntroducton) {
         this.userIntroducton = userIntroducton;
    }
    public String getUserIntroducton() {
         return userIntroducton;
     }

}