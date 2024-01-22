package com.example.to_do_list_dailytaskplanner.Models;

public class Users {
    public String usernaame;
    public String taskName;
    public String taskContent;
    public String email;
    public String password;
    public String userid;
    public String date;
    public String time;
    public String priority;

    public Users(String taskid) {
        this.taskid = taskid;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String taskid;
    public Users(String usernaame, String email, String password) {
        this.usernaame = usernaame;
        this.email = email;
        this.password = password;
    }

    public String getUsernaame() {
        return usernaame;
    }

    public void setUsernaame(String usernaame) {
        this.usernaame = usernaame;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Users() {
    }

    public Users(String taskName, String taskContent, String date, String time) {
        this.taskName = taskName;
        this.taskContent = taskContent;
        this.date = date;
        this.time = time;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Users(String taskName, String taskContent, String date, String time, String priority) {
        this.taskName = taskName;
        this.taskContent = taskContent;
        this.date = date;
        this.time = time;
        this.priority = priority;
    }
}
