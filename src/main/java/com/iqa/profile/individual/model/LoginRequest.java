package com.iqa.profile.individual.model;

/**
 * Created by kwk on 2018/05/10.
 */
public class LoginRequest {
    String username;
    String password;
    int user_type;

    public LoginRequest() {
    }

    public LoginRequest(String username, String password,int user_type) {
        this.username=username;
        this.password=password;
        this.user_type=user_type;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }
}
