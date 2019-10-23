package com.example.bhanson11.customcalendar;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

/**
 * Created by bhanson11 on 4/10/2019.
 */

public class BaseActivity extends AppCompatActivity
{
    DBManager dbManager;

    static SQLiteDatabase database;
    static DBManager manager;

    static int currentDateIndex = 0;
    static int currentClientIndex = 0;

    static ArrayList<Clients> aClient;
    static ArrayList<Appointment> anAppointment;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.phone_book:
            {
                Intent intent = new Intent(this, PhoneBook.class);
                this.startActivity(intent);
                break;
            }
            case R.id.add_client:
            {
                Intent intent = new Intent(this, ContactAdd.class);
                this.startActivity(intent);
                break;
            }
            case R.id.home_schedule:
            {
                Intent intent = new Intent(this, MainActivity.class);
                this.startActivity(intent);
                break;
            }
        }
        return true;
    }
}
