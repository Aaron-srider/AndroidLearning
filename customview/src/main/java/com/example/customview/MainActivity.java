package com.example.customview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout frameLayout = findViewById(R.id.mylayout);

        RabbitView rabbitView = new RabbitView(this);
//        触摸事件
        rabbitView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
//                为小兔子设置位置
                rabbitView.x = motionEvent.getX();
                rabbitView.y = motionEvent.getY();
//                重绘小兔子
                rabbitView.invalidate();
                return true;
            }
        });

        frameLayout.addView(rabbitView);
    }
}