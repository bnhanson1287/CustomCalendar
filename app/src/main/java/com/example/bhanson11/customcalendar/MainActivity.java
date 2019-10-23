package com.example.bhanson11.customcalendar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    // global variables
    ImageView next, previous;
    Button addEventBtn;
    GridView calendarGridView;
    Calendar calendar = Calendar.getInstance(Locale.CANADA);
    DateFormat formatter = new SimpleDateFormat("MMMM yyyy",Locale.CANADA);

    TextView currentDate, selectedDate;
    GridAdapter gridAdapter;
    Cursor cursor;
    ListView appointments;
    static final int MAX_CALENDAR_SIZE = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DBManager(this);
        //Buttons
        addEventBtn = (Button)findViewById(R.id.calendar_add_btn);
        addEventBtn.setOnClickListener(this);

        //ImageViews for next & previous buttons
        next = (ImageView)findViewById(R.id.next_month);
        next.setOnClickListener(this);
        previous = (ImageView)findViewById(R.id.previous_month);
        previous.setOnClickListener(this);

        //TextViews
        currentDate = (TextView)findViewById(R.id.display_current_date);
        selectedDate = (TextView)findViewById(R.id.selected_date);

        //GridView for calendar
        calendarGridView = (GridView)findViewById(R.id.calendar_gv);
        calendarGridView.setOnItemClickListener(this);

        // ListView for Appointments
        appointments = (ListView)findViewById(R.id.lv_appointments);
        CalendarSetUp();

    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        CalendarSetUp();
    }

    // calendar buttons
    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.calendar_add_btn:
            {
                String date = selectedDate.getText().toString();
                Intent intent = new Intent(this, AppointmentBooking.class);
                intent.putExtra("DATE", date);
                startActivity(intent);
                break;
            }
            case R.id.previous_month:
            {
                calendar.add(Calendar.MONTH, -1);
                CalendarSetUp();
                break;
            }
            case R.id.next_month:
            {
                calendar.add(Calendar.MONTH, 1);
                CalendarSetUp();
                break;
            }
        }
    }

    //loading dates into calendar view
    public void CalendarSetUp()
    {

        List<Date>  daysList = new ArrayList<>();
        Calendar cloneCalendar = (Calendar)calendar.clone();
        List<Appointment> appointmentsList = getAppointments();
        cloneCalendar.set(Calendar.DAY_OF_MONTH,1);
        int monthFirstDay = cloneCalendar.get(Calendar.DAY_OF_WEEK);
        cloneCalendar.add(Calendar.DAY_OF_MONTH, - monthFirstDay);
        while(daysList.size() < MAX_CALENDAR_SIZE)
        {
            daysList.add(cloneCalendar.getTime());
            cloneCalendar.add(Calendar.DAY_OF_MONTH,1);
        }
        String today = formatter.format(calendar.getTime());
        currentDate.setText(today);
        cloneCalendar.set(Calendar.DAY_OF_MONTH,1);
        gridAdapter = new GridAdapter(this, daysList, calendar, appointmentsList);
        calendarGridView.setAdapter(gridAdapter);
    }

    // click events in calendar view
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int index, long rowId)
    {
        currentDateIndex = index;
        List<Date>  daysList = new ArrayList<>();
        Calendar cloneCalendar = (Calendar)calendar.clone();
        cloneCalendar.set(Calendar.DAY_OF_MONTH,1);
        int monthFirstDay = cloneCalendar.get(Calendar.DAY_OF_WEEK)-1;
        cloneCalendar.add(Calendar.DAY_OF_MONTH, - monthFirstDay);
        while(daysList.size() < MAX_CALENDAR_SIZE)
        {
            daysList.add(cloneCalendar.getTime());
            cloneCalendar.add(Calendar.DAY_OF_MONTH,1);
        }
        cloneCalendar.set(Calendar.DAY_OF_MONTH,1);
        String onClickDate = daysList.get(index).toString();
        String shrtOnClickDate = onClickDate.substring(8,10);
        String date = ""+shrtOnClickDate+" "+currentDate.getText();
        selectedDate.setText(date);
        refreshAppointmentList();
    }

    // populate appointment listview
    public Cursor populateAppointmentList()
    {
        anAppointment = new ArrayList<Appointment>();
        database = dbManager.getReadableDatabase();
        String whereClause = DBManager.C_DATE+ " = " + "'"+(selectedDate.getText().toString())+"'";

        cursor = database.query(DBManager.TABLE_APPOINTMENT, null,whereClause,null,null,null,null);
        String name, time;
        int id;

        while(cursor.moveToNext())
        {
            id = cursor.getInt(cursor.getColumnIndex(DBManager.C_ID));
            time= cursor.getString(cursor.getColumnIndex(DBManager.C_DATE));
            name = cursor.getString(cursor.getColumnIndex(DBManager.C_CLIENT_NAME));

            Appointment item = new Appointment(id,time,name);
            anAppointment.add(item);
        }
        return cursor;
    }
    public void refreshAppointmentList()
    {
        cursor = populateAppointmentList();
        AppointmentListViewCursor adapter = new AppointmentListViewCursor(this,cursor);
        appointments.setAdapter(adapter);
    }


    // populate gridview with appointments
    public List<Appointment> getAppointments()
    {
        database = dbManager.getReadableDatabase();
        List<Appointment> appointments = new ArrayList<>();
        String appointmentDate;
        String time;
        Date appointmentDateDate;
        cursor = database.query(DBManager.TABLE_APPOINTMENT,null,null,null,null,null,null);

        while(cursor.moveToNext())
        {
            appointmentDate = cursor.getString(cursor.getColumnIndex(DBManager.C_DATE));


            appointmentDateDate = ConvertAppointmentStringToDate(appointmentDate);

            Appointment item = new Appointment(appointmentDateDate);
            appointments.add(item);
        }
        return appointments;
    }

    private Date ConvertAppointmentStringToDate(String dateString)
    {
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.CANADA);
        Date date = new Date();
        try
        {
            date = dateFormat.parse(dateString);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return date;


    }


}


