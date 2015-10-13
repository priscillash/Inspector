package com.example.priscilla.inspectores;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by priscilla on 12/10/15.
 */

//clase que se va a encargar del schema de la base de datos y operaciones CRUDE
public class DBManager {

    public static final String TABLE_NAME = "pruebaTiempo";

    public static final String CN_ID = "_id";
    public static final String CN_MATRICULA = "matricula";
    public static final String CN_TIME = "time";


    public static final String CREATE_TABLE = "create table " +TABLE_NAME+ " ("
            + CN_ID + " integer primary key autoincrement,"
            + CN_MATRICULA + " text not null,"
            + CN_TIME + " text);";


    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {

        helper = new DBHelper(context);
        db = helper.getWritableDatabase();

    }

    public ContentValues generarContentValues(String matricula, String time){
        ContentValues valores =new ContentValues();
        valores.put(CN_MATRICULA,matricula);
        valores.put(CN_TIME,time);

        return valores;
    }

    public void insertar (String matricula, String time){

        db.insert(TABLE_NAME,null,generarContentValues(matricula,time));


    }



}
