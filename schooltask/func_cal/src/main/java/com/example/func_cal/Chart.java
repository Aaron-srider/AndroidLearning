package com.example.func_cal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by KiBa-PC on 2017/4/18.
 */

public class Chart extends View {

    public FunctionCalculator functionCalculator = new FunctionCalculatorImpl(this);

    public final int DEFAULT_AXIS_WIDTH = 2;
    public final int DEFAULT_FUNCTION_LINE_WIDTH = 3;
    public final int DEFAULT_COORDINATE_TEXT_SIZE = 16;
    public final int DEFAULT_SEGMENT_SIZE = 50;
    public final int DEFAULT_PRECISION = 1;
    public final int DEFAULT_AXIS_POINT_RADIUS = 3;
    public final int DEFAULT_AXIS_COLOR = Color.BLACK;
    public final int DEFAULT_MAX = 5;
    public final int DEFAULT_SINGLE_POINT_COLOR = DEFAULT_AXIS_COLOR;

    public Canvas canvas;
    public float width; // view width
    public float height; // view height

    /**
     * 坐标轴画笔
     */
    public Paint axisPaint;

    /**
     * 坐标轴宽度
     */
    public int axisWidth = DEFAULT_AXIS_WIDTH;

    /**
     * 函数画笔
     */
    public Paint functionLinePaint;

    //public Paint pointPaint;

    public int lineColor = Color.RED;
    public int axisColor = DEFAULT_AXIS_COLOR; // axis color
    public int functionLineWidth = DEFAULT_FUNCTION_LINE_WIDTH;
    public int axisPointRadius = DEFAULT_AXIS_POINT_RADIUS;
    public int segmentSize = DEFAULT_SEGMENT_SIZE; // by default, 50 points will be taken
    public int dx = DEFAULT_PRECISION;
    public int coordinateTextSize = DEFAULT_COORDINATE_TEXT_SIZE; // the text size of text beside axis
    public int max = 100; // the max of the axis value

    public double unitLength = 10; // the length between two neighbour points of axises
    public double xMax = 0;
    public double yMax = 0;

    // all points are points with raw coordinates
    public PointF origin;
    public PointF leftPoint;
    public PointF rightPoint;
    public PointF topPoint;
    public PointF bottomPoint;

    /**
     * 用户输入的表达式
     */
    public String expression_user_input;

    //public PointF[] xPointsValues; // logic points, not raw points

    /**
     * 表的配置信息
     */
    public ChartConfig chartConfig;

    public Chart(Context context) {
        super(context);
        init(context);
    }

    public Chart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Chart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        setConfig(new ChartConfig(), false);

        axisPaint = new Paint();
        axisPaint.setStrokeWidth(axisWidth);
        axisPaint.setColor(axisColor);
        axisPaint.setAntiAlias(true);
        axisPaint.setStyle(Paint.Style.STROKE);
        axisPaint.setTextSize(coordinateTextSize);

        functionLinePaint = new Paint();
        functionLinePaint.setStrokeWidth(functionLineWidth);
        functionLinePaint.setColor(lineColor);
        functionLinePaint.setAntiAlias(true);
        functionLinePaint.setDither(true);
        functionLinePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 为view设置宽和高
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        //要求绘制之前必须设置图像属性
        if (chartConfig == null) {
            //如果没有设置图像属性，不予绘制
            return;
        }

        //为坐标轴的关键点定位
        locateView();

        //绘制坐标轴
        drawAxis(canvas);

        getPoints(expression_user_input);

    }

    private void locateView() {
        if (origin == null) {
            origin = new PointF();
            origin.set(width / 2f, height / 2f);

            leftPoint = new PointF();
            leftPoint.set(0, height / 2f);

            rightPoint = new PointF();
            rightPoint.set(width, height / 2f);

            topPoint = new PointF();
            topPoint.set(width / 2f, 0);

            bottomPoint = new PointF();
            bottomPoint.set(width / 2f, height);
        }
    }

    /**
     * 绘制坐标系
     */
    private void drawAxis(Canvas canvas) {
        //绘制横纵坐标
        canvas.drawLine(leftPoint.x, leftPoint.y, rightPoint.x, rightPoint.y, axisPaint);
        canvas.drawLine(topPoint.x, topPoint.y, bottomPoint.x, bottomPoint.y, axisPaint);

        //绘制纵坐标箭头
        Path path = new Path();
        path.moveTo(topPoint.x, topPoint.y);
        path.lineTo(topPoint.x - 10, topPoint.y + 20);
        path.lineTo(topPoint.x + 10, topPoint.y + 20);
        path.close();
        axisPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, axisPaint);
        //绘制横坐标箭头
        path.moveTo(rightPoint.x, rightPoint.y);
        path.lineTo(rightPoint.x - 20, rightPoint.y - 10);
        path.lineTo(rightPoint.x - 20, rightPoint.y + 10);
        path.close();
        canvas.drawPath(path, axisPaint);

        ////选择以短的坐标轴计算单位长度，使得单位长度尽可能短，坐标轴上的点尽可能多
        //unitLength = width > height ? width / 2 / (max + 1) : height / 2 / (max + 1); // +1 is for not to overlap with arrows

        //计算x和y轴的最大长度(逻辑长度)
        xMax = width / unitLength;
        yMax = height / unitLength;
        //xMax = (width > height ? width / unitLength : height / unitLength);
        //if (xMax >= max) {
        //    yMax = max;
        //} else {
        //    yMax = xMax;
        //    xMax = max;
        //}

        //绘制坐标轴上的点
        //横坐标负
        double circleDistance = (xMax / 20);
        for (double i = 0; i < xMax; i += circleDistance) {
            float x = (float) (origin.x - unitLength * (i + circleDistance));
            float y = origin.y;
            if (x > leftPoint.x) {
                path.moveTo(x, y);
                path.close();
                //绘制点
                canvas.drawCircle(x, y, axisPointRadius, axisPaint);

                //绘制数字
                //String coorText = String.valueOf(-(i + circleDistance));
                String coorText = String.format("%.2f", -(i + circleDistance));
                canvas.drawText(coorText, x, y + coordinateTextSize, axisPaint);
            }
        }

        //横坐标正
        for (double i = 0; i < xMax; i += circleDistance) {
            float x = (float) (origin.x + unitLength * (i + circleDistance));
            float y = origin.y;
            if (x < rightPoint.x) {
                path.moveTo(x, y);
                path.close();
                canvas.drawCircle(x, y, axisPointRadius, axisPaint);
                String coorText = String.format("%.2f", i + circleDistance);
                //String coorText = String.valueOf(i + circleDistance);
                canvas.drawText(coorText, x, y + coordinateTextSize, axisPaint);
            }
        }
        //纵轴正
        for (double i = 0; i < yMax; i += circleDistance) {
            float x = origin.x;
            float y = (float) (origin.y - unitLength * (i + circleDistance));
            if (y > topPoint.y) {
                path.moveTo(x, y);
                path.close();
                canvas.drawCircle(x, y, axisPointRadius, axisPaint);
                //String coorText = String.valueOf(i + circleDistance);
                String coorText = String.format("%.2f", i + circleDistance);
                canvas.drawText(coorText, x - coordinateTextSize, y, axisPaint);
            }
        }
        //纵轴负
        for (double i = 0; i < yMax; i += circleDistance) {
            float x = origin.x;
            float y = (float) (origin.y + unitLength * (i + circleDistance));
            if (y < bottomPoint.y) {
                path.moveTo(x, y);
                path.close();
                canvas.drawCircle(x, y, axisPointRadius, axisPaint);
                //String coorText = String.valueOf(-(i + circleDistance));
                String coorText = String.format("%.2f", -(i + circleDistance));
                canvas.drawText(coorText, x - coordinateTextSize * 1.2f, y, axisPaint);
            }
        }

        axisPaint.setStyle(Paint.Style.STROKE);
    }

    /**
     * 获取函数点集
     *
     * @param exp 函数表达式
     */
    private void getPoints(String exp) {
        if (!(exp == null || exp.equals(""))) {
            functionCalculator.getPoints(exp);
        }
    }


    public static PointF convertLogicalPoint2Raw(PointF logicalPoint, double unitLength, PointF origin) {
        float rawX = (float) (origin.x + logicalPoint.x * unitLength);
        float rawY = (float) (origin.y - logicalPoint.y * unitLength);
        return new PointF(rawX, rawY);
    }


    public static PointF convertRawPoint2Logical(PointF rowPoint, double unitLength, PointF origin) {
        float logicalX = (float) ((rowPoint.x - origin.x) / unitLength);
        float logicalY = (float) ((origin.y - rowPoint.y) / unitLength);
        return new PointF(logicalX, logicalY);
    }


    public void setChartConfig(ChartConfig chartConfig) {
        setConfig(chartConfig, true);
    }

    private void setConfig(ChartConfig config, boolean invalidate) {
        this.chartConfig = config;
        // axis color
        if (config.getAxisColor() != null) {
            setAxisColor(config.getAxisColor());
        } else {
            setAxisColor(DEFAULT_AXIS_COLOR);
            this.chartConfig.setAxisColor(DEFAULT_AXIS_COLOR);
        }
        // axis width
        if (config.getAxisWidth() != null) {
            setAxisWidth(config.getAxisWidth());
        } else {
            setAxisWidth(DEFAULT_AXIS_WIDTH);
            this.chartConfig.setAxisWidth(DEFAULT_AXIS_WIDTH);
        }
        // max values
        if (config.getMax() != null) {
            setMax(config.getMax());
        } else {
            setMax(DEFAULT_MAX);
            this.chartConfig.setMax(DEFAULT_MAX);
        }
        // dx
        if (config.getPrecision() != null) {
            setPrecision(config.getPrecision());
        } else {
            setPrecision(DEFAULT_PRECISION);
            this.chartConfig.setPrecision(DEFAULT_PRECISION);
        }
        // segment size
        if (config.getSegmentSize() != null) {
            setSegmentSize(config.getSegmentSize());
        } else {
            setSegmentSize(DEFAULT_SEGMENT_SIZE);
            this.chartConfig.setSegmentSize(DEFAULT_SEGMENT_SIZE);
        }
        // axis point radius
        if (config.getAxisPointRadius() != null) {
            setAxisPointRadius(config.getAxisPointRadius());
        } else {
            setAxisPointRadius(DEFAULT_AXIS_POINT_RADIUS);
            this.chartConfig.setAxisPointRadius(DEFAULT_AXIS_POINT_RADIUS);
        }
        if (invalidate) {
            invalidate();
        }
    }

    public void setAxisWidth(int axisWidth) {
        this.axisWidth = axisWidth;
    }

    /**
     * 函数曲线的精度，这个精度用于计算两点间切线的交点。推荐值：1-10<br>
     * The precision of the function curve, it's used to calculate the intersection point of two points' tangent lines.
     * value recommended: 1-10
     *
     * @param precision precision of the function curve
     */
    public void setPrecision(int precision) {
        this.dx = precision;
    }

    /**
     * 将x轴分割成segmentSize个点，成像时会将这些点连接起来。<br>
     * 注：size并不是越大越好，根据不同函数可做不同的调整（推荐值在30-100之间），尤其是tan和cot函数（目前尚未做优化）。<br>
     * The x axis will be equally separated to some segment points according to segmentSize,
     * and will connect these points when drawing the function.<br>
     * <b>ATTENTION</b>: size is not the bigger the better,
     * you have to adjust the size by different function types(30-100 is recommended),
     * especially <b>tan() and cot()</b> function (not optimized yet) need adjustment.
     *
     * @param segmentSize segment size
     */
    public void setSegmentSize(int segmentSize) {
        this.segmentSize = segmentSize;
    }

    public void setAxisColor(int axisColor) {
        this.axisColor = axisColor;
    }

    public void setAxisPointRadius(int axisPointRadius) {
        this.axisPointRadius = axisPointRadius;
    }

    /**
     * The max value that the axises have.
     *
     * @param max axis max value
     */
    public void setMax(int max) {
        this.max = max;
    }
}
