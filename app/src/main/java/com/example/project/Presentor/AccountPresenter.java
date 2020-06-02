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
    UsersAccounts usersAccounts;

    public AccountPresenter(SignInActivity signInActivity) {
        this.signInActivity = signInActivity;
        usersAccounts = new UsersAccounts(this.signInActivity);
    }


    public boolean checkData(String userName, String password){
        if (userName.isEmpty()|| password.isEmpty()){
            signInActivity.setError("Поле должно быть заполнено","Поле должно быть заполнено");
            return  false;
        }
        else {
            User user = usersAccounts.findUserByName(userName);
            if (user==null){
                signInActivity.setError("Имя пользователя введено неправильно",null);
                return false;
            }
            else if (!user.getPassword().equals(password)){
                signInActivity.setError(null,"Пароль введен неправильно");
                return false;
            }
            else{
                signInActivity.sendMessage("Вход успешно выполнен");
                return true;
            }


        }

    }

}
