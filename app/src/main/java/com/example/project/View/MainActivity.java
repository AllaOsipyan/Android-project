package com.example.project.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.Model.User;
import com.example.project.R;
import com.example.project.View.SignInDialog;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Editor editor;
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        sharedPreferences = getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if(sharedPreferences.getBoolean("isInAccount", false)){//если до перезапуска пользователь не выходил из аккаунта
            Intent intent = new Intent(this, MainAccountActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        editor.clear();

        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.auth:
                Intent intent = new Intent(this, SignInActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return true;
            case R.id.registr:
                Intent intentReg = new Intent(this, RegistrationActivity.class);
                startActivity(intentReg);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
