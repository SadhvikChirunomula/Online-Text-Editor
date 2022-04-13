package com.epsilon.OnlineTextEditor.models.request;

import java.util.HashMap;

public class RegisterUserRequest {
    String userName;
    String password;
    String userEmail;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashMap<String, Object> toJson() {
        HashMap<String, Object> tmp = new HashMap<>();
        tmp.put("username", userName);
        tmp.put("password", password);
        return tmp;
    }
}