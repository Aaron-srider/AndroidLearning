package com.example.func_cal;

import android.graphics.PointF;
import android.util.Log;
import android.webkit.WebView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FunctionCalculatorImpl implements FunctionCalculator {

    CoordinateAxisChart view;

    Integer pointNum = 200;

    WebView webView;

    public FunctionCalculatorImpl(CoordinateAxisChart view) {
        this.view = view;
    }

    private void solve(Task task) {
        //for (int i = 0; i < Task.pointNum; i++) {
        //    task.isFinished.add(false);
        //}
AtomicInteger count= new AtomicInteger();
        //批量计算表达式
        for (String expr : task.expressions) {
            webView.evaluateJavascript("javascript:" + expr
                    .replace("sin", "Math.sin")
                    .replace("cos", "Math.cos")
                    .replace("tan", "Math.tan")
                    .replace("log", "Math.log"), result -> {
                count.getAndIncrement();
                Log.v("jalsjdf;lajsd", String.valueOf(count));

                if (result.equals("null sdfcs")) {
                    task.pointList.add(null);

                } else {
                    //移除一个任务点
                    Float x = task.xList.remove();

                    //获取结果
                    Float y;
                    try {
                        y = Float.parseFloat(result);
                    } catch (Exception e) {
                        y = Float.NaN;
                    }

                    //得到计算的逻辑点结果
                    PointF logicPoint = new PointF(x, y);

                    //转换到屏幕上的点
                    PointF rowPoint = CoordinateAxisChart.convertLogicalPoint2Raw(logicPoint, view.unitLength, view.origin);

                    //加入结果点集
                    task.pointList.add(rowPoint);

                    //加入结果点集
                    task.logicPointList.add(logicPoint);

                    //如果一批计算量已经完成，绘制即可
                    if (solveEnd(task)) {
                        drawLine(task);
                    }

                }

            });
        }
    }

    private boolean solveEnd(Task task) {
        return task.xList.isEmpty();
    }

    @Override
    public void getPoints(String expression) {
        //获取js引擎
        webView = new WebView(view.getContext());
        webView.getSettings().setJavaScriptEnabled(true);
        List<PointF> rowPointList = new ArrayList<>();

        PointF logicLeft = CoordinateAxisChart.convertRawPoint2Logical(view.leftPoint, view.unitLength, view.origin);
        PointF logicRight = CoordinateAxisChart.convertRawPoint2Logical(view.rightPoint, view.unitLength, view.origin);

        double logicLen = logicRight.x - logicLeft.x;

        double logicDelt = logicLen / pointNum;

        Task task = new Task();

        for (int i = 0; i < pointNum; i++) {
            task.xList.add((float) logicLeft.x);
            task.expressions.add(expression.replace("x", String.valueOf(logicLeft.x)));
            logicLeft.x += logicDelt;
        }

        solve(task);

    }

    @Override
    public void drawLine(Task task) {
        for (int i = 0; i < Task.pointNum - 1; i++) {
            PointF start = task.pointList.get(i);
            PointF end = task.pointList.get(i + 1);
            view.canvas.drawLine(start.x, start.y, end.x, end.y, view.functionLinePaint);
        }
    }
}
