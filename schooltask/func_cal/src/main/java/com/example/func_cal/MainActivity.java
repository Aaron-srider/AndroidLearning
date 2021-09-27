package com.example.func_cal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Chart chart;
    Button largerBtn;
    Button smallerBtn;
    EditText input;
    Button cal;

    public void initField() {

        input = (EditText) findViewById(R.id.input);

        largerBtn = (Button) findViewById(R.id.largerBtn);
        smallerBtn = (Button) findViewById(R.id.smallerBtn);
        cal = (Button) findViewById(R.id.cal);

        chart = (Chart) findViewById(R.id.coordinateAxisChart);
        ChartConfig config = new ChartConfig();
        config.setMax(50);
        config.setPrecision(1);
        config.setSegmentSize(50);
        config.setAxisPointRadius(3);
        chart.setChartConfig(config);

    }


    public void registerEvent() {
        largerBtn.setOnClickListener((View view) -> {
            if (chart.unitLength > 10) {
                smallerBtn.setEnabled(true);
                smallerBtn.setText("缩小");
            }

            chart.unitLength += 10;
            chart.invalidate();
        });

        smallerBtn.setOnClickListener((View view) -> {
            if (chart.unitLength < 15) {
                Button btn = (Button) view;
                btn.setEnabled(false);
                btn.setText("最小化");

            } else {
                chart.unitLength -= 10;
                chart.invalidate();
            }

        });

        cal.setOnClickListener((View view) -> {
            String text = input.getText().toString();
            chart.expression_user_input = text;
            chart.invalidate();
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化属性
        initField();

        registerEvent();
    }
}
