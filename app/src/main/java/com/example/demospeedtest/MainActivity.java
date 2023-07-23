package com.example.demospeedtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.demospeedtest.databinding.ActivityMainBinding;

import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        SpeedViewModel speedViewModel=new SpeedViewModel(this.getApplication(),this);
        activityMainBinding.setViewModel(speedViewModel);
        activityMainBinding.executePendingBindings();
    }
}