package com.example.project.Presentor;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.project.Model.DB;
import com.example.project.Model.User;
import com.example.project.Model.UsersAccounts;
import com.example.project.View.RegistrationActivity;

public class RegistrationPresenter {
    private RegistrationActivity registrationActivity;
    private UsersAccounts usersAccounts;

    public  RegistrationPresenter(RegistrationActivity regActivity){
        registrationActivity = regActivity;
        usersAccounts = new UsersAccounts(registrationActivity);
    }
    //Проверка данных и регистрация пользователя
    public boolean registrateUser(String userName, String password, String passConf, String email){
        boolean flag = true;
        if (userName.isEmpty()||password.isEmpty()||passConf.isEmpty()||email.isEmpty())
            registrationActivity.setError("all","Все поля должны быть заполнены");
        else {
            if (userName.length()<5) {
                registrationActivity.setError("username", "Кол-во символов не меньше 5");
                flag = false;
            }
            else if(usersAccounts.findUserByName(userName)!=null){
                registrationActivity.setError("username","Пользователь уже существует");
                flag = false;
            }
            if (password.length()<8){
                registrationActivity.setError("pass", "Кол-во символов не меньше 8");
                flag = false;
            }
            if (!password.equals(passConf)){
                registrationActivity.setError("passConf","Пароли не совпадают");
                flag = false;
            }
            if(!email.contains("@")) {
                registrationActivity.setError("email", "Некорректный адрес");
                flag = false;
            }
            if(flag){
                usersAccounts.addNewUser(userName, password, email);
                return true;}
        }

        return false;
    }
}
