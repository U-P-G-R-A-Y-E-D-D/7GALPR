package com.company;

import java.awt.*;

public class Line2D {

    public Point2D pointA;
    public Point2D pointB;

    public Line2D(Point2D pA, Point2D pB){

        pointA = pA;
        pointB = pB;
    }


    public Line2D clone(){

        return new Line2D(pointA.clone(), pointB.clone());
    }
}
