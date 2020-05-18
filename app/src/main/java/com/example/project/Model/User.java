package com.example.project.Model;

import java.util.ArrayList;
import java.util.List;

public class User {

    String userName;
    String password;

    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

}
