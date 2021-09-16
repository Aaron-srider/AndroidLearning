package com.example.calculator;

import com.example.calculator.model.*;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.calculator.model.BaseConverter;
import com.example.calculator.model.ConvertedNumber;
import com.example.calculator.model.Converter;

import java.math.BigDecimal;

public class ConverterFragment extends androidx.fragment.app.Fragment implements Observer {
    private Converter converter;

    TextView output;

    TextView input;

    Spinner spinner1;

    Spinner spinner2;

    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.convert_fragment, container, false);
        initView();
        initModel();
        initEvent();
        return view;
    }

    public void initView() {
        output = view.findViewById(R.id.output);
        input = view.findViewById(R.id.input);

        spinner1 = (Spinner) view.findViewById(R.id.spinner1);
        spinner2 = (Spinner) view.findViewById(R.id.spinner2);

    }

    public void initEvent() {
        Integer[] idList = new Integer[]{
                R.id.zero,
                R.id.one,
                R.id.two,
                R.id.three,
                R.id.four,
                R.id.five,
                R.id.six,
                R.id.seven,
                R.id.eight,
                R.id.nine,
                R.id.dot,
                R.id.ce
        };


        for (int i = 0; i < idList.length; i++) {
            view.findViewById(idList[i]).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Button btn = (Button) view;
                    String userInput = btn.getText().toString();
                    String inputValue = input.getText().toString();
                    if (userInput.contains("C")) {
                        input.setText("");
                        output.setText("");
                        converter.reset();
                    } else if (userInput.contains(".")) {
                        inputValue += userInput;
                        input.setText(inputValue);
                    } else {
                        inputValue += btn.getText().toString();
                        input.setText(inputValue);
                        String unitsFrom = spinner1.getSelectedItem().toString();
                        String unitsTo = spinner2.getSelectedItem().toString();
                        BigDecimal value = null;
                        try {
                            value = new BigDecimal(input.getText().toString());
                            converter.setConvertedNumber(new ConvertedNumber(value, Utils.getUnitEnum(unitsFrom)));
                            converter.setFrom(Utils.getUnitEnum(unitsFrom));
                            converter.setTo(Utils.getUnitEnum(unitsTo));
                            try {
                                if (converter.canConvert()) {
                                    converter.convert();
                                }
                            } catch (NumberFormatException ex) {
                                String message = "错误";
                                input.setText(message);
                            } catch (IllegalArgumentException ex) {
                                String message = "单位不统一";
                                input.setText(message);
                                output.setText(message);
                            }
                        } catch (NumberFormatException ex) {
                            input.setText("");
                            output.setText("");
                        }

                        //ConvertedNumber result = converter.getResult();
                        //output.setText(result.getValue().toString());
                    }

                }
            });
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.items1, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView,
                                       View view, int i, long l) {
                UnitsEnum unitEnum = Utils.getUnitEnum(adapterView.getItemAtPosition(i).toString());

                converter.setFrom(unitEnum);

                try {
                    if (converter.canConvert()) {
                        converter.convert();
                        ConvertedNumber result = converter.getResult();
                        output.setText(result.getValue().toString());
                    }
                } catch (NumberFormatException ex) {
                    String message = "错误";
                    input.setText(message);
                } catch (IllegalArgumentException ex) {
                    String message = "单位不统一";
                    input.setText(message);
                    output.setText(message);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(view.getContext(), R.array.items2, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView,
                                       View view, int i, long l) {
                UnitsEnum unitEnum = Utils.getUnitEnum(adapterView.getItemAtPosition(i).toString());

                converter.setTo(unitEnum);

                try {
                    if (converter.canConvert()) {
                        converter.convert();
                        ConvertedNumber result = converter.getResult();
                        output.setText(result.getValue().toString());
                    }
                } catch (NumberFormatException ex) {
                    String message = "错误";
                    input.setText(message);
                } catch (IllegalArgumentException ex) {
                    String message = "单位不统一";
                    input.setText(message);
                    output.setText(message);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void initModel() {
        converter = new BaseConverter(this);
    }


    @Override
    public void update(Observable observable) {
        if (observable instanceof Converter) {
            if (converter.hasResult()) {
                output.setText(converter.getResult().getValue().toString());
            }
        }
    }
}
