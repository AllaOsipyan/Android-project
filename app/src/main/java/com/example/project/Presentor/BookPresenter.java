package com.example.project.Presentor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.Model.BookData;
import com.example.project.Model.JsonConvert;
import com.example.project.View.MainActivity;

import java.util.List;

public class BookPresenter {
    @SuppressLint("StaticFieldLeak")
    private static MainActivity mainActivity = new MainActivity();
    private static int currentBook = 0;

    public BookPresenter(MainActivity activity){
        mainActivity = activity;
    }
    public BookPresenter(){

    }
    public void getBook(){

        new JsonConvert.ParseTask().execute();

    }

    public void showBook(List<BookData> allBooks){

        mainActivity.showBook(allBooks.get(currentBook));
        currentBook=currentBook==9?0:currentBook+1;
    }
}
