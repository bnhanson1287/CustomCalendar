package com.example.bhanson11.customcalendar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by bhanson11 on 4/10/2019.
 */

public class AppointmentBooking extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    Button saveButton, timeButton;
    Spinner spinner;
    ClientSpinnerAdapter adapter;
    TextView tvTime, tvDate, client;
    DateFormat timeFormatter = new SimpleDateFormat("hh:mm a");
    DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.CANADA);
    Calendar calendar = Calendar.getInstance();
    Cursor cursor;
    DBManager dbManager;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_booking);

        // text views
        tvTime = (TextView)findViewById(R.id.tv_time);
        tvDate = (TextView)findViewById(R.id.tv_date);
        client = (TextView)findViewById(R.id.textview_client);

        //buttons
        saveButton = (Button)findViewById(R.id.save_appointment_button);
        saveButton.setOnClickListener(this);
        //spinner
        spinner = (Spinner)findViewById(R.id.spin_name);
        spinner.setOnItemSelectedListener(this);

        //db stuff
        dbManager = new DBManager(this);
        refreshSpinner();

        //retrieve date from schedule
        Bundle extras = getIntent().getExtras();
        if(extras == null)
        {
            date = "";
        }
        else
        {
            date = extras.getString("DATE");
        }
        tvDate.setText(date);
    }
    // update date if required
    private void updateDateLabel()
    {
        tvDate.setText(dateFormat.format(calendar.getTime()));
    }
    public void chooseDate(View view)
    {
        new DatePickerDialog(this,dateSetListener,calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),calendar.get(Calendar.YEAR)).show();
    }
    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day)
        {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            updateDateLabel();
        }
    };
    // setting time for the appointment
    private void updateTimeLabel()
    {

        tvTime.setText(timeFormatter.format(calendar.getTime()));
    }
    public void chooseTime(View view)
    {
        new TimePickerDialog(this, timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),false).show();
    }
    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minutes)
        {
            calendar.set(Calendar.MINUTE, minutes);
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            updateTimeLabel();
        }
    };

    // populating spinner
    private void populateSpinner()
    {
        database = dbManager.getReadableDatabase();
        aClient = new ArrayList<>();
        cursor = database.query(DBManager.TABLE_CLIENTS,null,null,null,null,null,null);
        int id;
        String clientName, clientPhone;

        while(cursor.moveToNext())
        {
            id = cursor.getInt(cursor.getColumnIndex(DBManager.C_ID));
            clientName = cursor.getString(cursor.getColumnIndex(DBManager.C_CLIENT_NAME));
            clientPhone = cursor.getString(cursor.getColumnIndex(DBManager.C_CLIENT_PHONE));
            Clients temp = new Clients(id,clientName,clientPhone);
            aClient.add(temp);
        }

    }
    private void refreshSpinner()
    {
        populateSpinner();
        adapter = new ClientSpinnerAdapter(this,R.layout.spinner_client_row, aClient);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int index, long l)
    {
        Clients client = (Clients) parent.getAdapter().getItem(index);
        currentClientIndex = index;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }

    //save appointment code
    public void AddAppointment()
    {
        String date = tvDate.getText().toString();
        String time = tvTime.getText().toString();

        if(!time.isEmpty())
        {
            tvDate.setText("");
            tvTime.setText("");

            ContentValues values = new ContentValues();
            values.put(DBManager.C_CLIENT_ID, aClient.get(currentClientIndex).getId());
            values.put(DBManager.C_DATE,date);
            values.put(DBManager.C_TIME,time);
            values.put(DBManager.C_CLIENT_NAME, aClient.get(currentClientIndex).getClientName());
            try
            {
                database = dbManager.getWritableDatabase();
                database.insertOrThrow(DBManager.TABLE_APPOINTMENT, null, values);
                Toast.makeText(this, "Appointment saved!", Toast.LENGTH_LONG).show();
                database.close();
            }
            catch (Exception e)
            {

                Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(this, "Enter appointment details", Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onClick(View view)
    {
        AddAppointment();
    }
}
