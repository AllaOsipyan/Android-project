package com.example.project.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//Создание БД
public class DB extends SQLiteOpenHelper {

    public static final int Db_version = 1;
    public static final String DbName = "ProjectDb";
    public static final String Users = "Users";
    public static final String All_books = "all_books";

    public DB(@Nullable Context context) {
        super(context, DbName, null, Db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS users (ID INT, NAME VARCHAR(200), EMAIL VARCHAR(200), PASSWORD VARCHAR(200))"
        );
        db.execSQL("INSERT INTO users values('0', 'admin', 'admin@mail','12345678')");
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS all_books (BOOK_ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE VARCHAR(400), AUTHORS VARCHAR(200), IMAGE VARCHAR(300),USER_QUERY VARCHAR(400), USER_ID INTEGER," +
                        "FOREIGN KEY(USER_ID) REFERENCES "+Users+"(ID) ON DELETE CASCADE)"

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS all_books");
        onCreate(db);
    }
}
