package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyService extends Service {
    private static final String TAG = "myServicetag";

    public MyService() {
    }

    @Override

    public void onCreate() {
        Log.v(TAG, "onCreate()");
        super.onCreate();
    }

    @Override

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAG, "onStartCommand()");
        int n = intent.getIntExtra("num", 0);
        Log.v(TAG, "int is:" + n);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override

    public void onDestroy() {
        Log.v(TAG, "onDestroy()");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

