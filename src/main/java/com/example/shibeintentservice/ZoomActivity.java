package com.example.shibeintentservice;



import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;


public class ZoomActivity extends AppCompatActivity implements View.OnClickListener {
ImageView imageView;
Button btnload;
    private static final String TAG = "ZoomActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zoomactivity);
        imageView=findViewById(R.id.container);
        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("PassingUrls");
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Glide.with(this).load(message).into(imageView);
        btnload= findViewById(R.id.btn_load);
        btnload.setOnClickListener(this);


        Log.d(TAG, "onCreate: Zooming");


    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
