/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String REQUEST_URL = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

       //Get the earthquakes
        new LoadQuakeData().execute(REQUEST_URL);


    }
    private class LoadQuakeData extends AsyncTask<String,Void,List<QuakeData>>{

        @Override
        protected List<QuakeData> doInBackground(String... strings) {
            if(strings.length<1 || strings[0] == null){
                return null;
            }
            return QueryUtils.extractEarthquakes(strings[0]);
        }

        @Override
        protected void onPostExecute(List<QuakeData> quakeDatas) {
            // Find a reference to the {@link ListView} in the layout
            ListView earthquakeListView = (ListView) findViewById(R.id.list);

            // Create a new {@link ArrayAdapter} of earthquakes
            final QuakeDataAapter mQuakeAdapter = new QuakeDataAapter(getApplicationContext(),quakeDatas);

            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            earthquakeListView.setAdapter(mQuakeAdapter);
            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    QuakeData clickedData = mQuakeAdapter.getItem(i);
                    Uri clickedUri = Uri.parse(clickedData.getUrl());
                    Intent intent = new Intent(Intent.ACTION_VIEW,clickedUri);
                    startActivity(intent);
                }
            });
        }
    }
}
