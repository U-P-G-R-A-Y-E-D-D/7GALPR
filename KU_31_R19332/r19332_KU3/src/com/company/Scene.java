package com.company;

import java.awt.*;
import java.util.ArrayList;

public class Scene {

    private final ArrayList<Point2D> points;
    private ArrayList<CubicBezier> cubicBeziers;

    public Scene() {
        points = new ArrayList<>();
    }

    public void addPoint(Point2D point) {
        points.add(point);


        int beziersCount = (points.size() - 1) / 3;

        cubicBeziers = new ArrayList<>(beziersCount);

        for (int i = 0; i < beziersCount; i++) {
            cubicBeziers.add(new CubicBezier(points, 3 * i));
        }
    }

    /*public Point2D getPointNearToPoint(Point toPoint, double maxDistance){
        for(Point2D point : points){
            if(point.getDistanceTo(toPoint)<maxDistance)return point;
        }
        return null;
    }
*/

    public void drawToVram(V_RAM vram) {
        GraphicsOperations.fillBrightness(vram,255);


        for (int i = 1; i < points.size(); i++) {
            GraphicsOperations.LineDrawing(vram, new Line2D(points.get(i - 1), points.get(i)), 200);
        }


        for (CubicBezier cubicBezier : cubicBeziers) {
            GraphicsOperations.drawBezier(vram,cubicBezier,100,10);
        }
    }
}
