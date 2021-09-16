package com.example.calculator;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class BaseCalculatorFragment extends androidx.fragment.app.Fragment implements Observer{
    private Exp exp;

    TextView output;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_fragment, container, false);
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
                R.id.plus,
                R.id.minus,
                R.id.multiply,
                R.id.divide,
                R.id.equal,
                R.id.ce
        };


        for (int i = 0; i < idList.length; i++) {
            view.findViewById(idList[i]).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Button button = (Button) view;
                    String userInput = button.getText().toString();

                    if (userInput.equals("=")) {
                        exp.cal();
                    } else {
                        exp.addChar(userInput);
                        if (exp.getExpression().contains("C")) {
                            exp.clearExpress();
                        }
                    }
                }
            });
        }

        output = view.findViewById(R.id.output);

        WebView webView = new WebView(view.getContext());
        webView.getSettings().setJavaScriptEnabled(true);
        exp = new Exp(this, webView);

        return view;
    }

    @Override
    public void update(Observable observable) {
        if(observable instanceof Exp) {
            if(exp.getStatus().equals(Exp.INPUTTING)) {
                output.setText(exp.getExpression());
            }
            else if(exp.getStatus().equals(Exp.RESULTCOMMINGOUT) || exp.getStatus().equals(Exp.ERROR)) {
                output.setText(exp.getExpResult());
            }
        }
    }
}


