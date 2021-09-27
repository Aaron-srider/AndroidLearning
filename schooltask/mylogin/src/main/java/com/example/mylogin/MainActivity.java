package com.example.mylogin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText username = null;
    EditText password = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        Button loginbtn = findViewById(R.id.loginbtn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //登录成功
                if (check(username.getText().toString(), password.getText().toString())) {
                    new AlertDialog.Builder(MainActivity.this).setTitle("登录提示").setMessage("登录成功")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Log.i("登录状态", "登录成功");
                                }
                            })
                            .show();
                } else {
                    new AlertDialog.Builder(MainActivity.this).setTitle("登录提示").setMessage("登录失败")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Log.i("登录状态", "登陆失败");
                                }
                            })
                            .show();
                }
            }
        });
    }

    public boolean check(String username, String password) {
        if (username.equals("abc") && password.equals("123")) {
            return true;
        }
        return false;
    }
}