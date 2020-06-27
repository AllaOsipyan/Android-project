package com.example.project.Model;

import java.util.ArrayList;
import java.util.List;

public class User {


    private int id;
    private String userName;
    private String password;
    private String email;




    public User(String userName, String password){
        this.userName = userName;
        this.password = password;

    }

    public User(int id, String userName, String password, String email){
        this.id = id;
        this.userName = userName;
        this.password = password;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

