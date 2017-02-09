package com.example.android.quakereport;

import java.util.Date;

/**
 * Created by anush on 27-01-2017.
 */

public class QuakeData {
    /*This is a data model class that contains data items like
    City name, Magnitude, Date for the earthquake. This will be used to populate
    the ListView with data.
     */
    //Variables to get the date, mag, and city data for an earthquake.

    private double mag;
    private String city;
    private long qDate;
    private String url;

    public QuakeData(double mMag,String mCity,long mDate, String contentUrl)
    {
        mag = mMag;
        city = mCity;
        qDate = mDate;
        url = contentUrl;

    }

    public double getMag(){
        return mag;
    }
    public String getCity(){
        return city;
    }
    public long getDate(){
        return qDate;
    }
    public String getUrl(){ return url;}
}
