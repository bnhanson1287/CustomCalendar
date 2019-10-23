package com.example.bhanson11.customcalendar;

/**
 * Created by bhanson11 on 4/10/2019.
 */
public class Clients
{
    private int id;
    private String clientName, clientNumber;

    public Clients (int id, String ClientName, String ClientNumber)
    {
        this.id=id;
        this.clientName = ClientName;
        this.clientNumber = ClientNumber;
    }
    public int getId(){return id;}
    public void setId(int id){this.id=id;}
    public String getClientName() {return clientName;}
    public void setClientName(String ClientName){this.clientName = ClientName;}
    public String getClientNumber() {return clientNumber;}
    public void setClientNumber(String ClientNumber){this.clientNumber = ClientNumber;}
}
