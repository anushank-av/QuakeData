package com.example.android.quakereport;

import android.content.Context;
import android.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

/**
 * Created by anush on 12-02-2017.
 */

public class QuakeDataLoader extends AsyncTaskLoader<List<QuakeData>> {
    // LOG TAG
    private static final String LOG_TAG = QuakeDataLoader.class.getName();

    //String for storing Url
    private static String mURL = "";



    public QuakeDataLoader(Context context, String address) {
        super(context);
        mURL = address;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG,"Force Loading");
        forceLoad();
    }

    @Override
    public List<QuakeData> loadInBackground() {
        Log.i(LOG_TAG,"Running Background Process");
        if (mURL==null)return null;
        return QueryUtils.extractEarthquakes(mURL);
    }
}
