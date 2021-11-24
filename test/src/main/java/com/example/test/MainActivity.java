package com.example.test;

import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    Queue<String> expressions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        expressions = new LinkedList<>();
        for (float x = 0; x < 100; x++) {
            expressions.add(x + "*" + x);
        }
        yList = new ArrayList<>();

        findViewById(R.id.cc).setOnClickListener(view -> solve());
    }

    ArrayList<Float> yList;

    void solveFinish() {
        for (float f : yList) {
            System.out.println(f);
        }
    }

    void solve() {
        if (expressions.size() == 0) {
            solveFinish();
            return;
        }
        String expr = expressions.remove();
        webView.evaluateJavascript("javascript:" + expr, result -> {
            yList.add((float)Double.parseDouble(result));
            solve();
        });
    }
}