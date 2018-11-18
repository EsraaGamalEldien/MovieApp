package com.example.esraa.movieapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.esraa.movieapp.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
