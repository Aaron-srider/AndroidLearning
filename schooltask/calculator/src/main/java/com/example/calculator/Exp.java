package com.example.calculator;

import android.webkit.WebView;


import lombok.Data;

@Data
public class Exp implements Observable {

    public static final Integer INPUTTING = 1;
    public static final Integer RESULTCOMMINGOUT = 2;
    public static final Integer ERROR = 3;

    Integer status = INPUTTING;

    private String expression = "";
    private String expResult = "";

    Observer observer;

    WebView webView;

    public Exp(Observer observer, WebView webView) {
        this.observer = observer;
        this.webView = webView;
    }

    public void clearExpress() {
        setExpression("");
    }

    private void setExpression(String expression) {
        this.expression = expression;
        status = INPUTTING;
        notifyObserver();
    }

    public void addChar(String str) {
        if(status.equals(ERROR)) {
            clearExpress();
        }
        expression += str;
        status = INPUTTING;
        notifyObserver();
    }

    public void cal() {
        webView.evaluateJavascript("javascript:" + expression
                .replace("sin", "Math.sin")
                .replace("cos", "Math.cos")
                .replace("tan", "Math.tan")
                .replace("ln", "Math.log"), result -> {
            if (result.equals("null") || result.equals("undefined")) {
                expression = expResult = "错误";
                status = ERROR;
            } else {
                expression = expResult = result;
                status = RESULTCOMMINGOUT;
            }
            notifyObserver();
        });
    }

    @Override
    public void registerObserver(Observer observer) {
        this.observer = observer;
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observer = null;
    }

    @Override
    public void notifyObserver() {
        observer.update(this);
    }
}