package com.company;

import java.util.List;

public class CubicBezier {

    public Point2D p0;
    public Point2D p1;
    public Point2D p2;
    public Point2D p3;

    private double qx0;
    private double qx1;
    private double qx2;
    private double qx3;

    private double qy0;
    private double qy1;
    private double qy2;
    private double qy3;

    public CubicBezier(List<Point2D> points, int startIndex) {
        p0 = points.get(startIndex);
        p1 = points.get(startIndex + 1);
        p2 = points.get(startIndex + 2);
        p3 = points.get(startIndex + 3);
    }

    public void recomputeQValues() {
        qx0 = p0.Values[0];
        qx1 = 3 * (p1.Values[0] - p0.Values[0]);
        qx2 = 3 * (p2.Values[0] - 2 * p1.Values[0] + p0.Values[0]);
        qx3 = p3.Values[0] - 3 * p2.Values[0] + 3 * p1.Values[0] - p0.Values[0];

        qy0 = p0.Values[1];
        qy1 = 3 * (p1.Values[1] - p0.Values[1]);
        qy2 = 3 * (p2.Values[1] - 2 * p1.Values[1] + p0.Values[1]);
        qy3 = p3.Values[1] - 3 * p2.Values[1] + 3 * p1.Values[1] - p0.Values[1];
    }

    //pouzity pseudokod ze skript
    public Point2D getPointForT(double t) {
        double tt = t * t;
        double ttt = tt * t;

        double x = qx0 + qx1 * t + qx2 * tt + qx3 * ttt;
        double y = qy0 + qy1 * t + qy2 * tt + qy3 * ttt;

        return new Point2D(x, y);
    }
}
