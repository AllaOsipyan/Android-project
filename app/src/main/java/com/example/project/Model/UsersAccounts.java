package com.example.project.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UsersAccounts {

    private DB dbhelper;
    public UsersAccounts(Context context){
        dbhelper = new DB(context);
    }

    //Добавление пользователя в БД
    public void addNewUser(String userName,String pass, String email){
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", userName);
        contentValues.put("EMAIL", email);
        contentValues.put("PASSWORD", pass);
        sqLiteDatabase.insert(DB.Users, null, contentValues);
        dbhelper.close();
    }
    //Поиск пользователя в БД
    public User findUserByName(String name){
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        String selection = "NAME =?";
        String[] whereArgs = new String[]{name};
        Cursor cursor = sqLiteDatabase.query("users", null,selection,whereArgs,null,null, null);
        if(cursor.moveToFirst()){
            int nameIndex = cursor.getColumnIndex("NAME");
            int passwordIndex = cursor.getColumnIndex("PASSWORD");
            int emailIndex = cursor.getColumnIndex("EMAIL");
            return  new User(cursor.getString(nameIndex), cursor.getString(passwordIndex), cursor.getString(emailIndex));
        }
        cursor.close();
        return null;
    }

    public List<User> getAllUsers(){
        List<User> users = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("users", null,null,null,null,null, null);
        if(cursor.moveToFirst()){
            int nameIndex = cursor.getColumnIndex("NAME");
            int passwordIndex = cursor.getColumnIndex("PASSWORD");
            int emailIndex = cursor.getColumnIndex("EMAIL");
            do {
                users.add(new User(cursor.getString(nameIndex), cursor.getString(passwordIndex), cursor.getString(emailIndex)));
                Log.d("mlog", "name = " + cursor.getString(nameIndex) + ";  email= " + cursor.getString(emailIndex));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return users;
    }
}
