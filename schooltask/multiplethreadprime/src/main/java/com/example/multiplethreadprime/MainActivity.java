package com.example.multiplethreadprime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.input);

        final TextView textView = (TextView) findViewById(R.id.textContext);
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                textView.setText(msg.arg1 == 1 ? "是":"否");
            }
        };
        final Runnable myWorker = new Runnable() {
            @Override
            public void run() {

                boolean isPrime = isPrime(Integer.parseInt(editText.getText().toString()));

                int result = isPrime?1:0;

                Message msg = handler.obtainMessage();
                msg.arg1 = result;
                handler.sendMessage(msg);
            }
        };

        Button button = (Button) findViewById(R.id.btnStart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread workThread = new Thread(null, myWorker, "WorkThread");
                workThread.start();
            }
        });
    }

    public static boolean isPrime(int n) {
        int i = 2;
        for (; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }


}
