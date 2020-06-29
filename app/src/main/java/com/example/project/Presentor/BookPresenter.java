package com.example.project.Presentor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.project.Model.BookData;
import com.example.project.Model.BookService;
import com.example.project.Model.JsonConvert;
import com.example.project.View.MainAccountActivity;
import com.example.project.View.MainActivity;

import java.util.List;

public class BookPresenter {
    @SuppressLint("StaticFieldLeak")
    private static MainActivity mainActivity;
    private static MainAccountActivity mainAccountActivity;
    private static int currentBookId = 0;
    private static String query;
    BookAdapter adapter;
    private static BookService bookService;
    private static List<BookData> allBooks;
    public BookPresenter(MainActivity activity){
        mainActivity = activity;
        bookService = new BookService(mainActivity);

    }
    public BookPresenter(MainAccountActivity activity){
        mainAccountActivity = activity;
        bookService = new BookService(mainAccountActivity);
    }
    public BookPresenter(){

    }
    public void getBook(Context context, String bookName){
        query = bookName;
        List<String> bookQueries =  bookService.findUserQueries();
        if(!bookQueries.contains(query)) {
            new JsonConvert.ParseTask(context, bookName).execute();

        }
        else
            presentBook(context);

    }

    public void presentBook(Context currContext){
        allBooks =  bookService.findBooksByQuery(query);
        if(currContext == mainAccountActivity){
            adapter = new BookAdapter(mainAccountActivity,allBooks);
            mainAccountActivity.showBooks(adapter);
        }
        if(currContext == mainActivity){
            if (allBooks.size()!=0){
                BookData currentBook = allBooks.get(currentBookId);
                mainActivity.showBook(currentBook.getTitle(), currentBook.getImageLink("smallThumbnail"));
                currentBookId = currentBookId==allBooks.size()-1? 0: currentBookId + 1;
            }
            Log.d("mlog", "current = " +currentBookId);

        }
    }


}