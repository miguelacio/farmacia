package com.example.example.examen.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by miguelacio on 19/01/18.
 */

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table cliente(id text, name text, pass text)");
        db.execSQL("create table medicina(id text, name text, price int)");
        db.execSQL("create table compra(id text, namecliente text, namemedicina text, qty text )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnte, int versionNue) {
        Log.w("RatedCalls Database",
                "Upgrading database, this will drop tables and recreate.");
        db.execSQL("DROP TABLE IF EXISTS cliente");
        onCreate(db);

    }
}
