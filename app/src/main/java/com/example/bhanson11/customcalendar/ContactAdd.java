package com.example.bhanson11.customcalendar;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by bhanson11 on 4/10/2019.
 */

public class ContactAdd extends BaseActivity implements View.OnClickListener {

    Button saveContact;
    EditText clientName, clientNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_add);

        saveContact = (Button) findViewById(R.id.save_contact_btn);
        saveContact.setOnClickListener(this);
        clientName = (EditText) findViewById(R.id.et_client_name);
        clientNumber = (EditText) findViewById(R.id.et_client_phone);
        dbManager = new DBManager(this);
    }

    @Override
    public void onClick(View view)
    {

        String name = clientName.getText().toString();
        String number = clientNumber.getText().toString();
        if (!name.isEmpty())
        {
            clientName.setText("");
            clientNumber.setText("");

            ContentValues values = new ContentValues();

            values.put(DBManager.C_CLIENT_NAME, name);
            values.put(DBManager.C_CLIENT_PHONE,number);
            try
            {
                database = dbManager.getWritableDatabase();
                database.insertOrThrow(DBManager.TABLE_CLIENTS, null, values);
                Toast.makeText(this, "You saved: " + name, Toast.LENGTH_LONG).show();



            }
            catch (Exception e)
            {

                Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(this, "Enter a client name", Toast.LENGTH_LONG).show();
        }
        Intent intent = new Intent (this, PhoneBook.class);
        this.startActivity(intent);

    }

}
