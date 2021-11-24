package com.example.toanother;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    private Button Thesender;
    private TextView YouGet;
    private EditText YouSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Thesender = findViewById(R.id.Send);
        YouGet = findViewById(R.id.TheGetter);
        YouSend = findViewById(R.id.TheSender);
        Intent msg = getIntent();
        String MsgData = msg.getStringExtra("Msg");
        YouGet.setText(MsgData);
        Thesender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (YouSend.getText().toString().equals(""))
                    Toast.makeText(MainActivity2.this, "输入返回值", Toast.LENGTH_SHORT).show();
                else {
                    msg.putExtra("Msg", YouSend.getText().toString());
                    setResult(1, msg);
                    finish();
                }
            }

        });

    }
    }