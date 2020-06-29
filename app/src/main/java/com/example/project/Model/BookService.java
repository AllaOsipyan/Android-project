package com.example.project.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookService {
    private DB dbhelper;
    private UsersAccounts  usersAccounts;
    public BookService(Context context){
        dbhelper = new DB(context);
        usersAccounts = new UsersAccounts(context);
    }


    public List<String> findUserQueries()  {
        List<String> userQueries = new ArrayList<>();
        String[] columns = new String[]{"USER_QUERY"};
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(true, DB.All_books, columns, null,null,null,null,null, null);
        while (cursor.moveToNext()){
            int queryIndex = cursor.getColumnIndex("USER_QUERY");
            userQueries.add(cursor.getString(queryIndex));
            Log.d("mlog", "name = " + cursor.getString(queryIndex) );
        }
        cursor.close();
        return userQueries;
    }


    public void addBook(BookData book, String query){

        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("TITLE", book.getTitle());
        contentValues.put("AUTHORS", book.transformAuthors());
        contentValues.put("USER_QUERY",query);
        contentValues.put("IMAGE", book.getImageLink("smallThumbnail"));
        sqLiteDatabase.insert(DB.All_books, null, contentValues);
        dbhelper.close();
        Log.d("mlog", "Добавлена книга");
    }

    public List<BookData> findUserBooks(User user) {
        List<BookData> userBooks = new ArrayList<>();
        JsonArray jsonArray = new JsonArray();

        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        // String selection = "USER_ID =?";
        //String[] whereArgs = new String[]{String.valueOf(user.getId())};
        Cursor cursor = sqLiteDatabase.query(DB.All_books, null,null,null,null,null, null);
        while (cursor.moveToNext()){
            BookData bookData = new BookData();
            int idIndex = cursor.getColumnIndex("USER_ID");
            int titleIndex = cursor.getColumnIndex("TITLE");
            int authorsIndex = cursor.getColumnIndex("AUTHORS");
            int imageIndex = cursor.getColumnIndex("IMAGE");
            bookData.setTitle(cursor.getString(titleIndex));
            bookData.setImage(cursor.getString(imageIndex));
            for (String author: cursor.getString(authorsIndex).split("\n")) {
                jsonArray.add(new JsonParser().parse(author));
            }
            bookData.setAuthors(jsonArray);
            userBooks.add(bookData);
            Log.d("mlog", "name = " + cursor.getString(titleIndex) + ";  email= " + cursor.getString(authorsIndex)+"; id= "+cursor.getString(idIndex));
        }
        cursor.close();
        return userBooks;
    }

    public List<BookData> findBooksByQuery(String query) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        String selection = "USER_QUERY=?";
        String[] whereArgs = new String[]{query};
        Cursor cursor = sqLiteDatabase.query(DB.All_books, null,selection,whereArgs,null,null, null);
        return getResult(cursor);
    }

    public List<BookData> findAllBooks() {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(DB.All_books, null,null,null,null,null, null);
        return getResult(cursor);
    }

    private List<BookData> getResult(Cursor cursor){
        List<BookData> userBooks = new ArrayList<>();
        JsonArray jsonArray = new JsonArray();
        while (cursor.moveToNext()){
            BookData bookData = new BookData();
            int idIndex = cursor.getColumnIndex("USER_ID");
            int titleIndex = cursor.getColumnIndex("TITLE");
            int authorsIndex = cursor.getColumnIndex("AUTHORS");
            int imageIndex = cursor.getColumnIndex("IMAGE");
            int qu =  cursor.getColumnIndex("USER_QUERY");
            bookData.setTitle(cursor.getString(titleIndex));
            bookData.setImage(cursor.getString(imageIndex));
            for (String author: cursor.getString(authorsIndex).split("\n")) {
                jsonArray.add(new JsonParser().parse(author));
            }
            bookData.setAuthors(jsonArray);
            userBooks.add(bookData);
            Log.d("mlog", "name = " + cursor.getString(titleIndex)+ "query" + cursor.getString(qu) + ";  email= " + cursor.getString(authorsIndex)+"; id= "+cursor.getString(idIndex));
        }
        cursor.close();
        return userBooks;
    }
}