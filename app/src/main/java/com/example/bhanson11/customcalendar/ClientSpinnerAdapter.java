package com.example.bhanson11.customcalendar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by bhanson11 on 4/10/2019.
 */

public class ClientSpinnerAdapter extends ArrayAdapter
{
    private Context context;
    private ArrayList aList;

    public ClientSpinnerAdapter(Context context, int rowLayoutId, ArrayList objects)
    {
        super(context, rowLayoutId,objects);
        this.context = context;
        this.aList = objects;
    }
    public int getCount() {return aList.size();}
    public Clients getItem(int index)
    {
        return (Clients) aList.get(index);
    }

    @Override
    public View getDropDownView(int position, View row, ViewGroup parent)
    {
        AppointmentBooking activity = (AppointmentBooking) context;
        LayoutInflater inflater = activity.getLayoutInflater();
        View spinnerRow = inflater.inflate(R.layout.spinner_client_row, null);
        TextView textView = (TextView)spinnerRow.findViewById(R.id.textview_client);

        Clients client = getItem(position);
        textView.setText(client.getClientName());

        return spinnerRow;
    }
    @Override
    public View getView(int index,  View row,  ViewGroup parent)
    {
        TextView label = new TextView(context);

        label.setTextColor(Color.parseColor("#002244"));
        label.setTextSize(20);

        Clients list = getItem(index);
        label.setText(list.getClientName());

        return label;
    }
}