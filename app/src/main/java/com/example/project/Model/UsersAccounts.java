package com.example.project.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UsersAccounts {
    private List<User> users = new ArrayList(Arrays.asList(new User("admin", "admin")));

    public void create(String userName, String pass) {
        users.add(new User(userName, pass));
    }

    public List<User> listAllUsers(){
        return users;
    }
}
