package com.example.bindservice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    ServiceConnection mServiceConnection;
    CalcService mCalcService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.v(TAG, "onServiceConnected");
//                绑定时获取service
                mCalcService = ((CalcService.LocalBinder) iBinder).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Log.v(TAG, "onServiceDisconnected");
            }
        };

        Button start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(
                        MainActivity.this,
                        "start",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, CalcService.class);
                bindService(intent, mServiceConnection, Service.BIND_AUTO_CREATE);
                Log.v(TAG, "bind");
            }
        });
        Button end = findViewById(R.id.end);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCalcService != null) unbindService(mServiceConnection);
                Log.v(TAG, "unbind");
            }
        });
        Button add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCalcService != null) {
                    Toast.makeText(
                            MainActivity.this,
                            "Using service add:" + mCalcService.add(10, 5),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        Log.v(TAG, "hello");

    }

}

