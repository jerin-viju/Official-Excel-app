package com.greycodes.excel14.pojo;

/**
 * Created by jincy on 9/8/15.
 */
public class LoginResponse {
    int status;
    String user_id;
    String user_name;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
 }
