package com.example.func_cal;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

//import lombok.Data;

//@Data
public class Task {

    public static int pointNum=200;

    /**
     * 原始x坐标
     */
    Queue<Float> xList = new ConcurrentLinkedQueue<>();

    /**
     * 由原始的x坐标生成的表达式集合
     */
    Queue<String> expressions = new ConcurrentLinkedQueue<>();


    /**
     * 存放计算结果
     */
    public List<PointF> pointList = new ArrayList<>();

    /**
     * 逻辑上的点集
     */
    public List<PointF> logicPointList = new ArrayList<>();
    
    //public Queue<Boolean> isFinished  = new ConcurrentLinkedQueue<>();

}
