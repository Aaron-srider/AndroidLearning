package com.example.calservice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    ServiceConnection mServiceConnection;
    CalcService mCalcService;
    EditText editText1;
    EditText editText2;
    EditText resultText;
    double a;
    double b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        resultText = findViewById(R.id.result);

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

        Intent intent = new Intent(MainActivity.this, CalcService.class);
        bindService(intent, mServiceConnection, Service.BIND_AUTO_CREATE);
        Log.v(TAG, "bind");


        Button add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEditText();
                String result = mCalcService.add(a, b);
                resultText.setText(result);


            }
        });
        Button sub = findViewById(R.id.sub);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEditText();
                String result = mCalcService.sub(a, b);
                resultText.setText(result);
            }
        });
        Button multiply = findViewById(R.id.multiply);
        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEditText();
                String result = mCalcService.multiply(a, b);
                resultText.setText(result);
            }
        });

        Button divide = findViewById(R.id.divide);
        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEditText();
                String result = mCalcService.divide(a, b);
                resultText.setText(result);
            }
        });
        Log.v(TAG, "hello");

    }

    public void getEditText() {
        a = Double.parseDouble(editText1.getText().toString());
        b = Double.parseDouble(editText2.getText().toString());
    }

}

