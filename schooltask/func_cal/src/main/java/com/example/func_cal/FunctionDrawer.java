package com.example.func_cal;

import android.graphics.PointF;

public class FunctionDrawer {

    Chart view;

    public FunctionDrawer(Chart view) {
        this.view = view;
    }

    public void drawTaskOnCanvas(Task task){
        for (int i = 0; i < Task.pointNum - 1; i++) {
            PointF start = task.pointList.get(i);
            PointF end = task.pointList.get(i + 1);
            view.canvas.drawLine(start.x, start.y, end.x, end.y, view.functionLinePaint);
        }
    }


}
