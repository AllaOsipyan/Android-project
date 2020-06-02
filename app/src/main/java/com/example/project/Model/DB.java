package com.example.project.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//Создание БД
public class DB extends SQLiteOpenHelper {

    public static final int Db_version = 1;
    public static final String DbName = "MyDb";
    public static final String Users = "Users";

    public DB(@Nullable Context context) {
        super(context, DbName, null, Db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS users (ID INT, NAME VARCHAR(200), EMAIL VARCHAR(200), PASSWORD VARCHAR(200))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }
}
