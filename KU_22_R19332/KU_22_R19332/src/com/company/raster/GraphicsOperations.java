package com.company.raster;

import javax.sound.sampled.Line;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GraphicsOperations {

    public static void fillBrightness(V_RAM vram, int brightness){

        brightness = Math.min(255, Math.max(0, brightness));

        for(int y = 0; y < vram.getHeight(); y++)
            for(int x = 0; x < vram.getWidth(); x++)
                vram.getRawData()[y][x] = brightness;
    }

    public static void drawLine(V_RAM vram, Line2D line, int brightness){
        line(vram, line, brightness);
    }

    public static void drawLine3D(V_RAM vram, Line3D line, int brightness) {
        Point2D pointA = new Point2D(line.pointA.vector[0], line.pointA.vector[1]);
        Point2D pointB = new Point2D(line.pointB.vector[0], line.pointB.vector[1]);

        Line2D convertedLine = new Line2D(pointA, pointB);

        line(vram, convertedLine, brightness);
    }

    public static void drawTriangle(V_RAM vram, Triangle2D triangle, int brightness){
        line(vram, new Line2D(triangle.pointA, triangle.pointB), brightness);
        line(vram, new Line2D(triangle.pointB, triangle.pointC), brightness);
        line(vram, new Line2D(triangle.pointC, triangle.pointA), brightness);
    }

    public static void drawTriangle3D(V_RAM vram, Triangle3D triangle, int brightness){
        drawLine3D(vram, new Line3D(triangle.pointA, triangle.pointB), brightness);
        drawLine3D(vram, new Line3D(triangle.pointB, triangle.pointC), brightness);
        drawLine3D(vram, new Line3D(triangle.pointC, triangle.pointA), brightness);
    }



    private static void line(V_RAM vram, Line2D line, int brightness) {

        Point startPoint = line.pointA.getRoundedPoint();
        Point endPoint = line.pointB.getRoundedPoint();

        LineDelta lineDelta = LineDelta.createLineDelta(startPoint, endPoint);

        if (isOnlyPixel(startPoint, endPoint)) {
            vram.setPixel(startPoint.x,startPoint.y,brightness);

            return;
        }

        if (isVerticalLine(startPoint, endPoint)) {
            if (isAxisYSwitched(startPoint, endPoint)) {
                switchPoints(startPoint,endPoint);
            }

            drawVerticalLine(vram, startPoint, endPoint, brightness);

            return;
        }

        if (isHorizontalLine(startPoint, endPoint)) {
            if (isAxisXSwitched(startPoint, endPoint)) {
                switchPoints(startPoint,endPoint);
            }

            drawHorizontalLine(vram, startPoint, endPoint, brightness);

            return;
        }

        float a = (float)lineDelta.getDeltaY() / lineDelta.getDeltaX();

        //Pokud je sklon čáry menší nebo roven 1, znamená to, že
        // Osa X je větší nebo rovna ose Y, takže pro každý X bude
        if (Math.abs(a) <= 1) {
            if (isAxisXSwitched(startPoint, endPoint)) {
                switchPoints(startPoint,endPoint);
            }

            bresenhamLow(startPoint, endPoint, vram, brightness);
        }

        // To je vrácena cesta, protože sklon čáry je větší než 1 to znamená
        // tam je definitivně větší množství pixelů Y než X.
        // Takže můžeme přepnout Y s X kreslit pixel pro každý Y místo X.
        else {
            if (isAxisYSwitched(startPoint, endPoint)) {
                switchPoints(startPoint,endPoint);
            }

            bresenhamHigh(startPoint, endPoint, vram, brightness);
        }
    }



    //<editor-fold desc="Bresenham">

    private static void bresenhamHigh(Point startPoint, Point endPoint, V_RAM vram, int brightness) {

        LineDelta lineDelta = LineDelta.createLineDelta(startPoint,endPoint);

        int d = 1;
        if (startPoint.x > endPoint.x) {
            d = -1;
        }

        int h1 = 2 * Math.abs(lineDelta.getDeltaX());
        int h2 = h1 - 2 * (lineDelta.getDeltaY());

        int h = h1 - lineDelta.getDeltaY(); // To je h0 v tomto bodě

        int x = startPoint.x;

        for (int y = startPoint.y; y <= endPoint.y; y++) {
            vram.setPixel(x, y, brightness);

            if (h > 0) {
                x += d;
                h+= h2;
            }

            else {
                h +=h1;
            }
        }
    }

    private static void bresenhamLow(Point startPoint, Point endPoint, V_RAM vram, int brightness) {

        LineDelta lineDelta = LineDelta.createLineDelta(startPoint,endPoint);

        int d = 1;
        if (startPoint.y > endPoint.y) {
            d = -1;
        }

        int h1 = 2 * Math.abs(lineDelta.getDeltaY());
        int h2 = h1 - 2 * (lineDelta.getDeltaX());

        int h = h1 - lineDelta.getDeltaX();

        int y = startPoint.y;

        for (int x = startPoint.x; x <= endPoint.x; x++) {
            vram.setPixel(x, y, brightness);

            if (h > 0) {
                y += d;
                h += h2;
            }

            else {
                h +=h1;
            }
        }
    }



    /**
     *
     * @param startPoint Začátek {@link Point} řádku.
     * @param endPoint Konec {@link Point} řádku.
     * @param vram Obrázek VRAM, kde je čára nakreslena..
     * @param brightness Jas (0 - 255) pixelu.
     */
    private static void ddaLow(Point startPoint, Point endPoint, V_RAM vram, int brightness) {
        LineDelta lineDelta = LineDelta.createLineDelta(startPoint,endPoint);

        float a = (float)lineDelta.getDeltaY() / lineDelta.getDeltaX();
        float y = startPoint.y;

        for (int x = startPoint.x; x <= endPoint.x; x++) {
            y += a;
            vram.setPixel(x, Math.round(y), brightness);
        }
    }

    private static void ddaHigh(Point startPoint, Point endPoint, V_RAM vram, int brightness) {
        LineDelta lineDelta = LineDelta.createLineDelta(startPoint,endPoint);

        float a = (float)lineDelta.getDeltaX() / lineDelta.getDeltaY();
        float x = startPoint.x;

        for (int y = startPoint.y; y <= endPoint.y; y++) {
            x += a;
            vram.setPixel(Math.round(x), y, brightness);
        }
    }

    /**
     * <p>
     *      This algorithm is executed whenever the abs(slope) of a line is greater than 1.
     *</p>
     *
     * <p>
     *      The principle of this algorithm is that we perceive the Y axis as the X axis and vice versa.
     *      Because the Y axis has more pixels than the X axis.
     * </p>
     * @param startPoint The start {@link Point} of the line.
     * @param endPoint The end {@link Point} of the line.
     * @param vram The VRAM image where the line is drawn.
     * @param brightness The brightness (0 - 255) of a pixel.
     */
    private static void naiveLineHigh(Point startPoint, Point endPoint, V_RAM vram, int brightness) {
        LineDelta lineDelta = LineDelta.createLineDelta(startPoint,endPoint);

        float a = (float)lineDelta.getDeltaX() / lineDelta.getDeltaY();
        float b = startPoint.x - (a * startPoint.y);
        int x;


        for (int y = startPoint.y;  y <= endPoint.y; y++) {
            x = Math.round(a * y + b);
            vram.setPixel(x,y,brightness);
        }
    }


    /**

     * @param startPoint The start {@link Point} of the line.
     * @param endPoint The end {@link Point} of the line.
     * @param vram The VRAM image where the line is drawn.
     * @param brightness The brightness (0 - 255) of a pixel.
     */
    private static void naiveLineLow(Point startPoint, Point endPoint, V_RAM vram, int brightness) {

        LineDelta lineDelta = LineDelta.createLineDelta(startPoint,endPoint);

        float a = (float)lineDelta.getDeltaY() / lineDelta.getDeltaX(); // deltaY / deltaX
        float b = startPoint.y - (a * startPoint.x); // startY - (sklon čáry * startX)
        int y;

        for (int x = startPoint.x;  x <= endPoint.x; x++) {
            y = Math.round(a * x + b);
            vram.setPixel(x,y,brightness);
        }
    }

    /**
     * Nakreslí svislou čáru od startPoint do endPoint.
     * @param vram Obrázek VRAM, kde je čára nakreslena.
     * @param startPoint Začátek {@link Point} řádku.
     * @param endPoint Konec {@link Point} řádku.
     * @param brightness Jas (0 - 255) pixelu.
     */
    private static void drawVerticalLine(V_RAM vram, Point startPoint, Point endPoint, int brightness) {
        for (int y = startPoint.y; y <= endPoint.y; y++) {
            vram.setPixel(endPoint.x, y, brightness);
        }
    }

    /**
     * Nakreslí vodorovnou čáru z startPoint na endPoint.
     * @param vram Obrázek VRAM, kde je čára nakreslena.
     * @param startPoint Začátek {@link Point} řádku.
     * @param endPoint Konec {@link Point} řádku.
     * @param brightness Jas (0 - 255) pixelu.
     */
    private static void drawHorizontalLine(V_RAM vram, Point startPoint, Point endPoint, int brightness) {
        for (int x = startPoint.x; x <= endPoint.x; x++) {
            vram.setPixel(x, endPoint.y, brightness);
        }
    }

    /**
     * Přepíná hodnoty obou bodů.
     * @param startPoint Začátek {@link Point} řádku.
     * @param endPoint Konec {@link Point} řádku.
     */
    private static void switchPoints(Point startPoint, Point endPoint) {
        int x = startPoint.x;
        int y = startPoint.y;

        startPoint.x = endPoint.x;
        startPoint.y = endPoint.y;

        endPoint.x = x;
        endPoint.y = y;
    }

    /**
     * Checks if start X is greater than end X.
     * @param start koordinace spuštění {@linkplain Point}.
     * @param end Konec {@linkplain Point} koordinace.
     * @return pokud je bod skutečně přepnut (osa X je obrácená).
     */
    private static boolean isAxisXSwitched(Point start, Point end) {
        return (start.x > end.x);
    }

    /**
     * Zkontroluje, zda je začátek Y větší než konec Y.
     * @param start koordinace spuštění {@linkplain Point}.
     * @param end Konec {@linkplain Point} koordinace.
     * @return pokud je bod skutečně přepnut (osa Y je obrácená).
     */
    private static boolean isAxisYSwitched(Point start, Point end) {
        return (start.y > end.y);
    }

    /**
     * Zkontroluje, zda je řádek podle {@link LineDelta} vodorovná čára.
     * @param start Počáteční bod řádku.
     * @param end Konec řádku.
     * @return pokud je bod skutečně přepnut (osa Y je obrácená).
     */
    private static boolean isHorizontalLine(Point start, Point end) {

        LineDelta lineDelta = LineDelta.createLineDelta(start,end);
        return lineDelta.getDeltaY() == 0;
    }

    /**
     * Zkontroluje, zda je čára podle {@link LineDelta} svislá čára.
     * @param start Počáteční bod řádku.
     * @param end Konec řádku.
     * @return pokud je aktuální čára svislá čára.
     */
    private static boolean isVerticalLine(Point start, Point end) {
        LineDelta lineDelta = LineDelta.createLineDelta(start,end);
        return lineDelta.getDeltaX() == 0;
    }

    /**
     * Zkontroluje, zda je řádek pouze 1 pixel.
     * @param start Počáteční bod řádku.
     * @param end Konec řádku.
     */
    private static boolean isOnlyPixel(Point start, Point end) {
        return start.x == end.x && start.y == end.y;
    }
}
