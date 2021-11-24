package com.example.androidlearning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("lifecycle", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("lifecycle", "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("lifecycle", "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("lifecycle", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("lifecycle", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("lifecycle", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("lifecycle", "onDestroy");
    }

}