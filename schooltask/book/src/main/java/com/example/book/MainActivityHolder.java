package com.example.book;

import android.content.ContentResolver;

public class MainActivityHolder {
    public static MainActivity mainActivity;

    public MainActivityHolder(MainActivity mainActivity) {
        if (mainActivity!=null) {
            this.mainActivity = mainActivity;
        }
    }

    public static MainActivity getInstance() {
        if(mainActivity!=null) {
            return mainActivity;
        } else {
            throw new NullPointerException("单件ContentResolver未初始化");
        }
    }
}
