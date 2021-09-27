package com.example.calservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class CalcService extends Service {
    private static final String TAG = "TAG";
    private LocalBinder mLocalBinder = new LocalBinder();

    public String add(double x, double y) {
        return (x + y) + "";
    }

    public String divide(double x, double y) {
        if(y != 0) {
            return x/y + "";
        }
        return "error";
    }

    public String sub(double x, double y) {
        return (x-y) + "";
    }

    public String multiply(double x, double y) {
        return x*y+ "";
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
