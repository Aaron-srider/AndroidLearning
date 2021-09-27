package com.example.store1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    //SharedPreferences文件名
    private final static String SharedPreferencesFileName = "config";
    //键
    private final static String Key_UserName = "UserName";
    //用户名
    private final static String Key_LoginDate = "LoginDate";
    //登录时间
    private final static String Key_UserType = "UserType";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获得SharedPreferences实例
        preferences = getSharedPreferences(SharedPreferencesFileName,
                MODE_PRIVATE);
        editor = preferences.edit();
        Button btnWrite = (Button) findViewById(R.id.ButtonWrite);
        Button btnRead = (Button) findViewById(R.id.ButtonRead);

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //写入键值对
                editor.putString("name", "文超");
                editor.putString("studentId", "2019012617");
                //提交修改，此处换成commit()也可以
                editor.apply();
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = preferences.getString("name", null);
                String studentId = preferences.getString("studentId", null);
                if (name != null && studentId != null)
                    Toast.makeText(MainActivity.this, "姓名:" + name + "studentId:" + studentId, Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "无数据", Toast.LENGTH_LONG).show();
            }
        });

    }
}