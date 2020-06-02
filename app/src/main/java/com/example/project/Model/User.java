package com.example.project.Model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String userName;
    private String password;
    private String email;




    public User(String userName, String password){
        this.userName = userName;
        this.password = password;

    }

    public User(String userName, String password, String email){
        this.userName = userName;
        this.password = password;

    }
    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;}
}
