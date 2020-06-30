package com.example.project.View;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.project.Model.DB;
import com.example.project.Presentor.RegistrationPresenter;
import com.example.project.R;
import com.google.android.material.textfield.TextInputLayout;

public class RegistrationActivity extends Activity {
    TextInputLayout usernameTI, passTI,passConfTI, emailTI;
    TextView errTV;
    String userName, password, passConf, email;
    SharedPreferences.Editor editor;
    RegistrationPresenter registrationPresenter = new RegistrationPresenter(this);
    SharedPreferences sharedPreferences;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        usernameTI = findViewById(R.id.username_reg);
        passTI = findViewById(R.id.password_reg);
        passConfTI =findViewById(R.id.confirm_pass_reg);
        emailTI = findViewById(R.id.email_reg);
        errTV = findViewById(R.id.error_tv);
        sharedPreferences = getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }



    public void registration(View view){

        setError("all","");
        userName = usernameTI.getEditText().getText().toString();
        password = passTI.getEditText().getText().toString();
        passConf = passConfTI.getEditText().getText().toString();
        email = emailTI.getEditText().getText().toString();
        if (registrationPresenter.registrateUser(userName, password, passConf, email)){
            editor.clear();
            editor.putBoolean("isInAccount", true).commit(); //запомнить, что пользователь в аккаунте
            Intent intent = new Intent(this, MainAccountActivity.class);
            startActivity(intent);
            finishAffinity();

        }
    }

    public void setError(String field, String error){
        switch (field){
            case ("username"):
                usernameTI.setError(error);
                break;
            case ("pass"):
                passTI.setError(error);
                break;
            case ("passConf"):
                passConfTI.setError(error);
                break;
            case ("email"):
                emailTI.setError(error);
                break;
            case ("all"):
                usernameTI.setError("");
                passTI.setError("");
                passConfTI.setError("");
                emailTI.setError("");
                errTV.setText("");
                errTV.setTextColor(Color.RED);
                errTV.setText(error);
                break;

        }
    }


}
