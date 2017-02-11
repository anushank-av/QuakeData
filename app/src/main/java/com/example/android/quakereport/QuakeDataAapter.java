package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by anush on 27-01-2017.
 */

public class QuakeDataAapter extends ArrayAdapter<QuakeData> {

    public QuakeDataAapter(Context context, List<QuakeData> quakeDatas) {
        super(context,0,quakeDatas);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        // Check if the existing view is being reused, otherwise inflate the view
        if(listView == null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_element,parent,false);
        }
        QuakeData currentData = getItem(position);


        //Setting the magnitude
        /*
            //Setting the image circle for magnitude first
         */
        TextView magInfo = (TextView)listView.findViewById(R.id.magData);
        GradientDrawable magnitudeCircle = (GradientDrawable)magInfo.getBackground();


        //Get the appripriate background colour based on mag

        int magColor = getMagnitudeColor(currentData.getMag());
        magnitudeCircle.setColor(magColor);

        magInfo.setText(String.valueOf(currentData.getMag()));

        //Setting the location

        TextView cityInfo = (TextView)listView.findViewById(R.id.cityData);
        String location = currentData.getCity();
        String primaryLoc;
        String offset;
        if(location.contains("of")){
            String[] parts = location.split("of");
            parts[0] = parts[0].concat("of");
            primaryLoc = parts[1];
           offset = parts[0];
        }
        else
        {
            offset = "Near the ";
            primaryLoc = location;
        }

        TextView cityOffset = (TextView)listView.findViewById(R.id.cityOffsetData);
        cityOffset.setText(offset);

        cityInfo.setText(primaryLoc);

        //Setting the date


        TextView dateInfo = (TextView)listView.findViewById(R.id.dateData);
        Date qDate = new Date(currentData.getDate());
        SimpleDateFormat forDate = new SimpleDateFormat("LLL DD, yyyy");
        String date = forDate.format(qDate);
        forDate = new SimpleDateFormat("hh : mm a");
        String time =forDate.format(qDate);
        dateInfo.setText(date);
        TextView timeInfo = (TextView)listView.findViewById(R.id.timeData);
        timeInfo.setText(time);

        //return the created object

        return listView;
    }
    private int getMagnitudeColor(double magnitude)
    {
        int magnitudeResource;
        int magnitudeFloor = (int)Math.floor(magnitude);
        switch (magnitudeFloor){
            case 0:
            case 1:
                magnitudeResource = R.color.magnitude1;
                break;
            case 2:
                magnitudeResource = R.color.magnitude2;
                break;
            case 3:
                magnitudeResource = R.color.magnitude3;
                break;
            case 4:
                magnitudeResource = R.color.magnitude4;
                break;
            case 5:
                magnitudeResource = R.color.magnitude5;
                break;
            case 6:
                magnitudeResource = R.color.magnitude6;
                break;
            case 7:
                magnitudeResource = R.color.magnitude7;
                break;
            case 8:
                magnitudeResource = R.color.magnitude8;
                break;
            case 9:
                magnitudeResource = R.color.magnitude9;
                break;
            default:
                magnitudeResource = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(),magnitudeResource);
    }
}
