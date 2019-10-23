package com.example.bhanson11.customcalendar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by bhanson11 on 4/10/2019.
 */

public class DBManager extends SQLiteOpenHelper
{
    static final String DB_NAME = "client.db";
    static final int DB_VERSION = 1;
    static final String TABLE_APPOINTMENT = "appointments";
    static final String TABLE_CLIENTS = "clients";
    static final String C_ID = BaseColumns._ID;//id
    static final String C_CLIENT_NAME = "clientName";
    static final String C_CLIENT_PHONE = "clientPhone";
    static final String C_CLIENT_ID = "clientId";
    static final String C_DATE = "appointmentDate";
    static final String C_TIME = "appointmentTime";

    public DBManager(Context context)
    {
        super(context, DB_NAME,null,DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql = "CREATE TABLE "+ TABLE_CLIENTS+ " (" + C_ID + " Integer primary key autoincrement, "+C_CLIENT_NAME+" text, "+C_CLIENT_PHONE+ " text)";
        db.execSQL(sql);

        sql = "CREATE TABLE "+ TABLE_APPOINTMENT+ " (" + C_ID + " Integer primary key autoincrement, "+C_DATE+" TEXT, " + C_TIME +" TEXT, " + C_CLIENT_ID + " integer, "
                +C_CLIENT_NAME+" TEXT)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        db.execSQL("drop table if exists " + TABLE_CLIENTS);
        db.execSQL("drop table if exists " + TABLE_APPOINTMENT);

    }



}
