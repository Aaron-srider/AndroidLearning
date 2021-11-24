package com.example.store2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    public static final String MyFileName = "myinfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnWrite = findViewById(R.id.ButtonWrite);
        Button btnRead = findViewById(R.id.ButtonRead);

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                OutputStream out = null;
                try {
                    FileOutputStream fileOutputStream = openFileOutput(MyFileName, MODE_PRIVATE);
                    out = new BufferedOutputStream(fileOutputStream);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
                    User user = new User();
                    user.setName("文超");
                    user.setStudentId("2019012617");

                    try {
                        objectOutputStream.writeObject(user);
                    } finally {
                        if (objectOutputStream != null) objectOutputStream.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputStream in = null;
                try {
                    FileInputStream fileInputStream = openFileInput(MyFileName);
                    in = new BufferedInputStream(fileInputStream);
                    ObjectInputStream objectInputStream = new ObjectInputStream(in);

                    int c;
                    StringBuilder stringBuilder = new StringBuilder("");
                    try {
                        User user = (User)objectInputStream.readObject();
                        Toast.makeText(MainActivity.this, "name:"+user.getName()+"; studentId:"+user.getStudentId()+"", Toast.LENGTH_LONG).show();
                    } finally {
                        if (in != null) in.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }
}


