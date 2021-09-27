package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] countryArray = {"李白", "杜甫", "白居易", "王维", "李商隐", "杜牧"};

        ListView listView = (ListView)findViewById(R.id.listview);

        ArrayAdapter<String> arrayAdapter=
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                        countryArray);

        listView.setAdapter(arrayAdapter);

    }
}