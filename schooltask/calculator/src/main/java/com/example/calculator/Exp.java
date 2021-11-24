package com.example.calculator;

import android.webkit.WebView;


import lombok.Data;

@Data
public class Exp implements Observable {

    /**
     * 观察者
     */
    Observer observer;

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

    /**
     * 处于输入状态
     */
    public static final Integer INPUTTING = 1;

    /**
     * 已经算出结果
     */
    public static final Integer RESULTCOMMINGOUT = 2;

    /**
     * 结果出错
     */
    public static final Integer ERROR = 3;

    /**
     * 保存计算器的状态
     */
    Integer status = INPUTTING;

    /**
     * 记录表达式（用户输入）
     */
    private String expression = "";

    /**
     * 保存表达式的结果
     */
    private String expResult = "";



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
                .replace("ln", "Math.log"),
        result -> {
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


}