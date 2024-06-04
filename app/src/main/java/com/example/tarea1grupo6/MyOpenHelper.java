package com.example.tarea1grupo6;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {

    public static final String usuario  = "CREATE TABLE usuario (id INTEGER PRIMARY KEY AUTOINCREMENT, " + "cedula TEXT," +
            "nombresApellidos TEXT, " + "edad INTEGER, " + "nacionalidad TEXT, " + "genero TEXT, " + "fechaNacimiento TEXT)";
    public static final String dbName = "dbTarea1Grupo6";
    public static final int dbVersion = 1;

    public MyOpenHelper(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(usuario);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
