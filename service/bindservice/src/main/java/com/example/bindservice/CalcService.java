package com.example.bindservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class CalcService extends Service {
    private static final String TAG = "TAG";
    private LocalBinder mLocalBinder = new LocalBinder();

    public int add(int x, int y) {
        return x + y;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.v(TAG, "Service onBind");
        return mLocalBinder;
    }

    public class LocalBinder extends Binder {
        CalcService getService() {
            return CalcService.this;
        }
    }
}
