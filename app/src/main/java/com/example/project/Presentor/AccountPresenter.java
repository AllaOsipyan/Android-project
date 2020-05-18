package com.example.project.Presentor;

import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.project.Model.User;
import com.example.project.Model.UsersAccounts;
import com.example.project.R;
import com.example.project.View.SignInActivity;
import com.example.project.View.SignInDialog;

public class AccountPresenter {
    private SignInActivity signInActivity;
    private User user;
    UsersAccounts usersAccounts = new UsersAccounts();

    public AccountPresenter(User user, SignInActivity signInActivity) {
        this.user = user;
        this.signInActivity = signInActivity;
    }


    public void checkData(){
        if (user.getUserName().isEmpty()|| user.getPassword().isEmpty()){
            signInActivity.setError("Поля не должны быть пустыми");
        }
        else {
            for (User u:usersAccounts.listAllUsers()
                 ) {
                if(u.getUserName().equals(user.getUserName()) && u.getPassword().equals(user.getPassword())) {
                    signInActivity.sendMessage("Вход успешно выполнен");
                    break;
                }
                else {
                    signInActivity.setError("Неправильное имя потльзователя или пароль");
                }
            }

        }
         //   User.users.add(new User(user.getUserName(), user.getUserName()));

    }

}
