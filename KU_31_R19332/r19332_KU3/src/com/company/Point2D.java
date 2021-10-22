package com.company;

import java.awt.*;

public class Point2D {

    public double[] Values;
    private double x;
    private double y;


    public Point2D(){

        Values = new double[3];
    }

    public Point2D(double x, double y){

        Values = new double[3];

        Values[0] = x;
        Values[1] = y;
        Values[2] = 1;
    }


    //medoda pro odčítání bodů --- subtractPoint

    public Point2D odecistPoint(Point2D toPoint) {
        return new Point2D(Values[0] - toPoint.Values[0], Values[1] - toPoint.Values[1]);
    }

    //metoda pro sčítání (přidání) bodů --- addPoint
    public Point2D pridatPoint(Point2D toPoint) {
        return new Point2D(Values[0] + toPoint.Values[0], Values[1] + toPoint.Values[1]);
    }

    //metoda pro rozdělení bodů --- splitPoint
    public Point2D rozdelitPoint(double number) {
        return new Point2D(Values[0] / 6, Values[1] / 6);
    }

    public Point2D clone(){

        return new Point2D(Values[0], Values[1]);
    }

    public Point getRoundedPoint(){

        return new Point((int)Math.round(Values[0]), (int)Math.round(Values[1]));
    }

    public double getDistanceTo(Point2D toPoint) {
        return Math.sqrt((Values[0] - toPoint.x)*(Values[0]-toPoint.x)+(Values[1]-toPoint.y)*(Values[1]-toPoint.y));
        
    }





}
