package com.example.bhanson11.customcalendar;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

/**
 * Created by bhanson11 on 4/10/2019.
 */

public class PhoneBook extends BaseActivity
{
    ListView listView;
    PhoneBookCursorAdapter adapter;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_book);
        dbManager = new DBManager(this);
        database = dbManager.getReadableDatabase();

        listView = (ListView)findViewById(R.id.lv_phone_book);
    }

    @Override
    protected void onResume()
    {
        cursor = database.query(DBManager.TABLE_CLIENTS,null,null,null,null,null,null,null);

        adapter = new PhoneBookCursorAdapter(this,cursor);
        listView.setAdapter(adapter);


        super.onResume();
    }
}
