package com.company;

import java.awt.*;

public class GraphicsOperations {


    public static void fillBrightness(V_RAM vram, int brightness) {

        brightness = Math.min(255, Math.max(0, brightness));

        for (int y = 0; y < vram.getHeight(); y++)
            for (int x = 0; x < vram.getWidth(); x++)
                vram.getRawData()[y][x] = brightness;
    }

    public static void drawBezier(V_RAM vram, CubicBezier cubicBezier, int steps, int brightness) {
        cubicBezier.recomputeQValues();

        Point2D startPoint = cubicBezier.p0;


        for (int i = 1; i <= steps; i++) {
            Point2D endPoint = cubicBezier.getPointForT(1.0 * i / steps);

            GraphicsOperations.drawLine(vram, new Line2D(startPoint, endPoint), brightness);

            startPoint = endPoint;
        }
    }

    public static void drawLine(V_RAM vram, Line2D line, int brightness){

        LineDrawing(vram, line, brightness);
    }


    public static void LineDrawing(V_RAM vram, Line2D line, int brightness) {

        Point startPoint = line.pointA.getRoundedPoint();
        Point endPoint = line.pointB.getRoundedPoint();

        int dx = endPoint.x - startPoint.x;
        int dy = endPoint.y - startPoint.y;

        if (dx == 0){
            //svisla usecka
            System.out.println("svisla usecka");
            if(startPoint.y > endPoint.y) switchPoints(startPoint, endPoint);
        }
        else if (dy == 0){
            //vodorovna usecka
            if(startPoint.x > endPoint.x) switchPoints(startPoint, endPoint);
            System.out.println("vodorovna usecka");

        } else if(startPoint.x == endPoint.x && startPoint.y == endPoint.y){
            vram.setPixel(startPoint.x, startPoint.y, brightness);
            return;
        }
        float a = (float)dy / dx;
        if(Math.abs(a)<=1){
            if(startPoint.x > endPoint.x) switchPoints(startPoint, endPoint);
           // DDALow(startPoint, endPoint, vram, brightness);
           BresenhamLow(startPoint, endPoint, vram, brightness);
        } else{
            if(startPoint.y > endPoint.y) switchPoints(startPoint,endPoint);
           // DDAHigh(startPoint, endPoint, vram, brightness);
            BresenhamHigh(startPoint, endPoint, vram, brightness);
        }
    }
    //0°-45°
    private static void BresenhamLow(Point startPoint, Point endPoint, V_RAM vram, int brightness){

        int dx = endPoint.x - startPoint.x;
        int dy = endPoint.y - startPoint.y;

        int d;
        if (startPoint.y > endPoint.y){
            d=-1;
        }else{
            d=1;
        }
        int h1 = 2 * Math.abs(dy);
        int h2 = h1 - 2 * dx;
        int h = h1 - dx;

        for (int x=startPoint.x; x < endPoint.x; x++) {

            vram.setPixel(x, startPoint.y, brightness);
            if (h>0){
                startPoint.y+=d;
                h+=h2;
            }else{
                h+=h1;
            }
        }
        vram.setPixel(endPoint.x,endPoint.y,brightness);
    }
    //45°-90°
    private static void BresenhamHigh(Point startPoint, Point endPoint, V_RAM vram, int brightness){
        int dx = endPoint.x - startPoint.x;
        int dy = endPoint.y - startPoint.y;

        int d;
        if (startPoint.x > endPoint.x){
            d=-1;
        }else{
            d=1;
        }
        int h1 = 2 * Math.abs(dx);
        int h2 = h1 - 2 * dy;
        int h = h1 - dy;

        for (int y=startPoint.y; y < endPoint.y; y++) {

            vram.setPixel(startPoint.x, y, brightness);
            if (h>0){
                startPoint.x+=d;
                h+=h2;
            }else{
                h+=h1;
            }
        }
        vram.setPixel(endPoint.x,endPoint.y,brightness);
    }


    private static void switchPoints(Point startPoint, Point endPoint) {
        int x = startPoint.x;
        int y = startPoint.y;

        startPoint.x = endPoint.x;
        startPoint.y = endPoint.y;

        endPoint.x = x;
        endPoint.y = y;
    }



    private static void DDAHigh(Point startPoint, Point endPoint, V_RAM vram, int brightness) {

        float a = (float)(endPoint.x -startPoint.x)/(endPoint.y-startPoint.y);
        float x = startPoint.x;

        for (int y = startPoint.y; y <= endPoint.y; y++) {
            x += a;
            vram.setPixel(Math.round(x), y, brightness);
        }
    }

    private static void DDALow(Point startPoint, Point endPoint, V_RAM vram, int brightness) {
        float a = (float)(endPoint.y -startPoint.y)/(endPoint.x-startPoint.x);
        float y = startPoint.y;

        for (int x = startPoint.x; x <= endPoint.x; x++) {
            y += a;
            vram.setPixel(x, Math.round(y), brightness);
        }
    }

    private static void naiveLineHigh(Point startPoint, Point endPoint, V_RAM vram, int brightness) {

        float a = (float) (endPoint.x - startPoint.x) / (endPoint.y - startPoint.y);
        float b = (float)(endPoint.y*startPoint.x-startPoint.y*endPoint.x) / (endPoint.y-startPoint.y);
        int x;

        for (int y = startPoint.y; y <= endPoint.y; y++) {
            x = Math.round(a*y+b);
            vram.setPixel(y, x, brightness);
        }
    }


    private static void naiveLineLow(Point startPoint, Point endPoint, V_RAM vram, int brightness) {
        float a = (float) (endPoint.y - startPoint.y) / (endPoint.x - startPoint.x);
        float b = (float)(endPoint.x*startPoint.y-startPoint.x*endPoint.y) / (endPoint.x-startPoint.x);
        int y;

        for (int x = startPoint.x; x <= endPoint.x; x++) {
            y = Math.round(a*x+b);
            vram.setPixel(x, y, brightness);
        }

    }

}