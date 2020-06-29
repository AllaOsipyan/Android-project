package com.example.project.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.Presentor.BookPresenter;
import com.example.project.R;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Editor editor;
    ImageView bookImage;
    TextView bookTitle;
    BookPresenter bookPresenter;
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        bookImage = findViewById(R.id.book_image);
        bookPresenter = new BookPresenter(this);
        bookTitle = findViewById(R.id.book_title);

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

    public void findNewBook(View view){
        bookPresenter.getBook(this,"flower");
    }

    public void showBook(String title, String path ){
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this));
        builder.build()
                .load(path.replace("\"","").trim())
                .fit()
                .into(bookImage);
        bookTitle.setText(title);
    }

}