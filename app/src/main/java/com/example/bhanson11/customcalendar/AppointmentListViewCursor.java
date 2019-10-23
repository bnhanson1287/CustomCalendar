package com.example.bhanson11.customcalendar;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;

public class AppointmentListViewCursor extends SimpleCursorAdapter
{
    static final String[] FROM = {DBManager.C_ID, DBManager.C_TIME, DBManager.C_CLIENT_NAME };
    static final int[] TO = {R.id.tv_list_id,R.id.tv_time_appointment,R.id.tv_client_appointment};

    public AppointmentListViewCursor(Context context, Cursor cursor)
    {
        super(context, R.layout.listview_row,cursor,FROM,TO);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
    }
}
