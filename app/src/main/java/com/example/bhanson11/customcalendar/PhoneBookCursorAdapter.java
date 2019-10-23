package com.example.bhanson11.customcalendar;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;

/**
 * Created by bhanson11 on 4/10/2019.
 */


public class PhoneBookCursorAdapter extends SimpleCursorAdapter
{
    static final String[] columns = {DBManager.C_CLIENT_NAME, DBManager.C_CLIENT_PHONE};
    static final int[] ids = {R.id.tv_name, R.id.tv_number};

    public PhoneBookCursorAdapter(Context context, Cursor cursor)
    {
        super(context, R.layout.phone_book_row, cursor, columns,ids);
    }

    @Override
    public void bindView(View row, Context context, Cursor cursor)
    {
        super.bindView(row, context, cursor);
    }
}
