package com.example.clasesql.entidades;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class BaseInicial extends SQLiteOpenHelper {

    public BaseInicial(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }



    ////    EXRAER DATOS DE LAS TABLAS
    public Cursor getDataTable(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }


    public void insertDataRutas(String codigo, String ciudad, String longitud, String latitud) {

        //descripcion ,fecha ,imagen ,valor
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO rutas VALUES(?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, codigo);
        statement.bindString(2, ciudad);
        statement.bindString(3, longitud);
        statement.bindString(4, latitud);
        statement.executeInsert();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS rutas (codigo TEXT PRIMARY KEY,ciudad TEXT,latitud TEXT,longitud TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "SELECT * FROM rutas";


    }
}
