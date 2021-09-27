package com.example.func_cal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CoordinateAxisChart coordinateAxisChart;
    Button largerBtn;
    Button smallerBtn;
    EditText input;
    Button cal;
    //Button largerBtn;
    //Button largerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinateAxisChart = (CoordinateAxisChart) findViewById(R.id.coordinateAxisChart);

        input= (EditText) findViewById(R.id.input);

        ChartConfig config = new ChartConfig();
        config.setMax(50);
        config.setPrecision(1);
        config.setSegmentSize(50);
        config.setAxisPointRadius(3);
        coordinateAxisChart.setConfig(config);

        largerBtn = (Button) findViewById(R.id.largerBtn);
        largerBtn.setOnClickListener((View view) -> {

            if (coordinateAxisChart.unitLength > 10) {
                smallerBtn.setEnabled(true);
                smallerBtn.setText("缩小");

            }

            coordinateAxisChart.unitLength += 10;
            coordinateAxisChart.invalidate();

        });

        smallerBtn = (Button) findViewById(R.id.smallerBtn);
        smallerBtn.setOnClickListener((View view) -> {
            if (coordinateAxisChart.unitLength < 15) {
                Button btn = (Button) view;
                btn.setEnabled(false);
                btn.setText("最小化");

            }else{
                coordinateAxisChart.unitLength -= 10;
                coordinateAxisChart.invalidate();
            }

        });


        cal = (Button) findViewById(R.id.cal);
        cal.setOnClickListener((View view) -> {
            String text = input.getText().toString();
            coordinateAxisChart.expression=text;
            coordinateAxisChart.invalidate();



        });



    }

    @Override
    public void onClick(View v) {

    }
}
