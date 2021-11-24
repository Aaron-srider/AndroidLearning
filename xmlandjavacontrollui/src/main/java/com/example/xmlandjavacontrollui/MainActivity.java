package com.example.xmlandjavacontrollui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
     private ImageView[] imgArr = new ImageView[6];
     private Integer[] imgPathArr = new Integer[] {
             R.mipmap.img01,
             R.mipmap.img02,
             R.mipmap.img03,
             R.mipmap.img04,
             R.mipmap.img05,
             R.mipmap.img06
     };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        选用配置文件
        setContentView(R.layout.activity_main);

//        自定义布局
//        获取布局文件 findViewById
        GridLayout layout = findViewById(R.id.layout);


        for (int i = 0; i < imgPathArr.length; i++) {
            imgArr[i] = new ImageView(MainActivity.this);
            imgArr[i].setImageResource(imgPathArr[i]);
            imgArr[i].setPadding(2,2,2,2);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(1160,680);
            imgArr[i].setLayoutParams(params);
            layout.addView(imgArr[i]);
        }

    }
}