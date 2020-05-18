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

public class SignInActivity extends AppCompatActivity implements SignInDialog.SignInDialogListener{
    EditText et, pass;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presentDialog();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

        et = dialog.getDialog().findViewById(R.id.username);
        pass = dialog.getDialog().findViewById(R.id.password);
        String userName = et.getText().toString();
        String password = pass.getText().toString();
        user = new User(userName, password);
        AccountPresenter accountPresenter = new AccountPresenter(user, this);
        accountPresenter.checkData();
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

    public void setError(String error){
        presentDialog();
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    public void sendMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
