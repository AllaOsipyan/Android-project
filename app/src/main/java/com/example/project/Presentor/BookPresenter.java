package com.example.project.Presentor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.Model.BookData;
import com.example.project.Model.JsonConvert;
import com.example.project.View.MainAccountActivity;
import com.example.project.View.MainActivity;

import java.util.List;

public class BookPresenter {
    @SuppressLint("StaticFieldLeak")
    private static MainActivity mainActivity;
    private static MainAccountActivity mainAccountActivity;
    private static int currentBookId = 0;
    BookAdapter adapter;
    public BookPresenter(MainActivity activity){
        mainActivity = activity;
    }
    public BookPresenter(MainAccountActivity activity){
        mainAccountActivity = activity;

    }
    public BookPresenter(){

    }
    public void getBook(String bookName){

        new JsonConvert.ParseTask(bookName).execute();

    }

    public void presentBook(List<BookData> allBooks){
        if(mainActivity!=null){
            BookData currentBook = allBooks.get(currentBookId);
            mainActivity.showBook(currentBook.getTitle(), currentBook.getImageLink("smallThumbnail"));
            currentBookId = currentBookId==allBooks.size()-1? 0: currentBookId + 1;

        }
        if(mainAccountActivity!=null){
            adapter = new BookAdapter(mainAccountActivity,allBooks);
            mainAccountActivity.showBooks(adapter);
        }
    }
}
