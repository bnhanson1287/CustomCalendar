package com.example.bhanson11.customcalendar;

import java.util.Date;

/**
 * Created by bhanson11 on 4/10/2019.
 */

public class Appointment
{
    private int id;
    private String clientName;
    private Date date;
    private String time;
    private String clientId;

    public Appointment (String clientName, Date date, String time)
    {
        this.clientName = clientName;
        this.date = date;
        this.time = time;
    }
    public Appointment (int id, String clientName, Date date, String time, String clientId)
    {
        this.id = id;
        this.clientName = clientName;
        this.date = date;
        this.time = time;
        this.clientId = clientId;
    }
    public Appointment (int id, String clientName, String time)
    {
        this.id = id;
        this.clientName = clientName;
        this.time = time;
    }
    public Appointment (Date date)
    {
        this.date = date;
    }
    public int getId()
    {
        return id;
    }
    public String getClientName()
    {
        return clientName;
    }
    public Date getDate()
    {
        return date;
    }
    public String getTime()
    {
        return time;
    }
    public String getClientId()
    {
        return clientId;
    }
}