package com.example.project.View;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.project.Model.User;
import com.example.project.Presentor.AccountPresenter;
import com.example.project.R;
import com.google.android.material.textfield.TextInputLayout;

public class SignInActivity extends AppCompatActivity implements SignInDialog.SignInDialogListener{
    TextInputLayout userNameTI, passTI;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presentDialog();
    }
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

        userNameTI = dialog.getDialog().getWindow().findViewById(R.id.username);
        passTI = dialog.getDialog().getWindow().findViewById(R.id.password);
        String userName = userNameTI.getEditText().getText().toString();
        String password = passTI.getEditText().getText().toString();
        user = new User(userName, password);
        AccountPresenter accountPresenter = new AccountPresenter(user, this);
        boolean isCorrect = accountPresenter.checkData();
        if(isCorrect)
            dialog.dismiss();
    }

    public void presentDialog(){
        SignInDialog dialog = new SignInDialog();
        dialog.show(getSupportFragmentManager(),"Dialog");
        dialog.setCancelable(false);
    };

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public User getUserData(){
        return user;
    }

    public void setError(String uNameError, String passError){
        userNameTI.setError(uNameError);
        passTI.setError(passError);
    }

    public void sendMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
