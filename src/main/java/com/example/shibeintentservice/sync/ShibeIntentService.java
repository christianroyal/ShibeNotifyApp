package com.example.shibeintentservice.sync;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.os.ResultReceiver;
import android.util.Log;

import com.example.shibeintentservice.Constants;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class ShibeIntentService extends IntentService{
    public final String TAG = "ShibeIntentService";

    public ShibeIntentService() {
        super("ShibeIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final ResultReceiver rec = intent.getParcelableExtra("receiver");
        Integer count = intent.getIntExtra("count", 1);
        Bundle b = new Bundle();
        String baseUrl = "http://shibe.online/api/shibes";
        String query = "?count=" + count;
        StringBuilder result = new StringBuilder();
        HttpURLConnection httpURLConnection = null;

        Log.d(TAG, "onHandleIntent: HTTPConnection");


        try {
            URL url = new URL(baseUrl + query);
            httpURLConnection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();

        }
        String removeBrackets = result.substring(1, result.length() - 1);
        String removeQuotes = removeBrackets.replace("\"", "");
        String[] urls = removeQuotes.split(",");

        b.putStringArray("Urls", urls);


        assert rec != null;
        rec.send(Constants.SUCCESS, b);

        Log.d(TAG, "onHandleIntent: Sending");


    }
}



