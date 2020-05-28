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


    public boolean checkData(){
        if (user.getUserName().isEmpty()|| user.getPassword().isEmpty()){
            signInActivity.setError("Поле должно быть заполнено","Поле должно быть заполнено");
            return  false;
        }
        else {
            for (User u:usersAccounts.listAllUsers()
                 ) {
                if(u.getUserName().equals(user.getUserName()) && u.getPassword().equals(user.getPassword())) {
                    signInActivity.sendMessage("Вход успешно выполнен");
                    return true;
                }
                else {
                    if(!u.getUserName().equals(user.getUserName()))
                    signInActivity.setError("Имя пользователя введено неправильно",null);
                    else
                        signInActivity.setError(null,"Пароль введен неправильно");
                }
            }

        }
         //   User.users.add(new User(user.getUserName(), user.getUserName()));
        return false;
    }

}
