package com.example.shibeintentservice;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.shibeintentservice.sync.MyResultReceiver;
import com.example.shibeintentservice.sync.ShibeIntentService;
import com.example.shibeintentservice.utils.NotificationUtils;


import java.net.HttpURLConnection;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity implements ShibeAdapter.OnShibeClicked,MyResultReceiver.Receiver {
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private ShibeAdapter shibeAdapter;
    public MyResultReceiver mReceiver;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpURLConnection httpURLConnection;
        recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setHasFixedSize(true);
        mReceiver = new MyResultReceiver(new Handler());
        mReceiver.setReceiver((MyResultReceiver.Receiver)this);
        final Intent intent = new Intent(Intent.ACTION_SYNC, null, this, ShibeIntentService.class);

        intent.putExtra("receiver", mReceiver);
        intent.putExtra("count", 100);
        startService(intent);

        Log.d(TAG, "onCreate: Service Started");
    }





    @Override
    public void shibeClicked(String url) {
        Intent intent = new Intent(MainActivity.this, ZoomActivity.class);
        intent.putExtra("PassingUrls", url);
        startActivity(intent);
    }


    private void loadRecyclerview(List<String> strings) {
        shibeAdapter = new ShibeAdapter(strings, MainActivity.this);
        recyclerView.setAdapter(shibeAdapter);


        Log.d(TAG, "loadRecyclerview: Loading RV");


    }
    public void onPause() {
        super.onPause();
        mReceiver.setReceiver(null); // clear receiver so no leaks.
    }

    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case Constants.SUCCESS:
                String[] url= resultData.getStringArray("Urls");
                Log.d(TAG, "onReceiveResult: Success");
                List<String>urls= Arrays.asList(url);
                loadRecyclerview(urls);

                //show progress
                break;
            case Constants.FAIL:

                Log.d(TAG, "onReceiveResult: Failed");
                // do something interesting
                // hide progress
                break;
        }
    }public void testNotification(View view){
        NotificationUtils.shibesUploaded(this); }
}


