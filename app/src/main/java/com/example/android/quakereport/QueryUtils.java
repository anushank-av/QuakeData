package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 *
 */
public final class QueryUtils {
    private static String LOG_TAG = QueryUtils.class.getSimpleName();
     /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link QuakeData} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<QuakeData> extractEarthquakes(String queryUrl) {
        //Generate a url of Url type
        String reply = "";

        URL mQueryUrl = generateURL(queryUrl);
        //Properly generated url obtained.
        try {
            //Create a string to obtain response
            reply = getHTTPData(mQueryUrl);
        }catch (IOException e){
            Log.e(LOG_TAG,"Error in networking process");
        }
        return returnResultsFromJSON(reply);

    }

    private static String getHTTPData(URL mQueryUrl) throws IOException {
        String response = "";
        InputStream in = null;
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) mQueryUrl.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(1000);
            con.setReadTimeout(1000);
            con.connect();
            if (con.getResponseCode() == 200) {
                in = con.getInputStream();
                response = getResponseData(in);
            } else {
                Log.e(LOG_TAG, con.getResponseCode() + " : Error");
            }
        }catch (IOException e){
            Log.e(LOG_TAG,"Error parsing JSON results");
        }
        finally {
            if (con!=null){con.disconnect();}
            if (in !=null)in.close();
        }
        return response;

    }

    private static String getResponseData(InputStream in) throws IOException {
       StringBuilder responseCode = new StringBuilder();
       if (in!=null){InputStreamReader mInputReader = new InputStreamReader(in, Charset.forName("UTF-8"));
        BufferedReader bReader = new BufferedReader(mInputReader);
       String line = bReader.readLine();
           while(line!=null){
               responseCode.append(line);
               line = bReader.readLine();
           }
       }
        return responseCode.toString();
    }

    private static URL generateURL(String queryUrl) {
        URL url = null;
        try{
            url = new URL(queryUrl);
        }
        catch (MalformedURLException e) {
            Log.e(EarthquakeActivity.LOG_TAG,"Error while parsing URL",e);
        }
        return url;
    }
    private static ArrayList<QuakeData> returnResultsFromJSON(String jsonTag){
        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<QuakeData> quakeDatas = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            JSONObject quakeRoot = new JSONObject(jsonTag);
            JSONArray quakeArray = quakeRoot.getJSONArray("features");
            for(int i = 0;i<quakeArray.length();i++)
            {
                JSONObject quakeData = quakeArray.getJSONObject(i);
                JSONObject quakeProp = quakeData.getJSONObject("properties");
                double mag = quakeProp.getDouble("mag");
                String loc = quakeProp.getString("place");
                long time = quakeProp.getLong("time");
                String url = quakeProp.getString("url");
                quakeDatas.add(new QuakeData(mag,loc,time,url));

            }
            // build up a list of QuakeData objects with the corresponding data.

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(LOG_TAG, "Problem parsing the QuakeData JSON results", e);
        }

        // Return the list of QuakeDatas
        return quakeDatas;
    }

}