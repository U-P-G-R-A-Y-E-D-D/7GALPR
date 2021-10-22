package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class MainWindow extends JPanel{

    private ImagePanel imagePanel;
    private JLabel infoLabel;

    private V_RAM vram;

    Scene scene;

    private String currentModel;

    public MainWindow() {

        initialize();
        scene = new Scene();
        vram = new V_RAM(1880, 970);

        GraphicsOperations.fillBrightness(vram, 255);

        clear();

    }




    private void clear() {
        GraphicsOperations.fillBrightness(vram, 255);
        imagePanel.setImage(vram.getImage());
    }

    private void initialize(){

        setLayout(null);
        setFocusable(true);
        requestFocusInWindow();

        JMenuBar menuBar = new JMenuBar();



        imagePanel = new ImagePanel();
        imagePanel.setBounds(10, 60, 1880, 970);
        this.add(imagePanel);



        //open image
        JButton button = new JButton();
        button.setBounds(150,10,120,30);
        button.setText("Clear window");

        button.addActionListener((ActionEvent e) -> {
            clear();
        });

        this.add(button);


        JButton button3 = new JButton();
        button3.setBounds(290,10,120,30);
        button3.setText("Bresenham/DDA");

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                      drawBresenham();
            }
        });

        this.add(button3);

        JButton button5 = new JButton();
        button5.setBounds(420,10,120,30);
        button5.setText("Cubic Bezier");

        button5.addActionListener(e -> {

            // 1.křivka
            Point2D point0 = new Point2D(10, 35);

            Point2D point1 = new Point2D(50, 255);
            Point2D point2 = new Point2D(234, 155);

            //řídící body L a R a jejich vypočítané souřadnice podle bodu 3 ze zadání KU3

            Point2D pointL1 = point1.odecistPoint(point2.odecistPoint(point0).rozdelitPoint(6));
            Point2D pointR1 = point1.pridatPoint(point2.odecistPoint(point0).rozdelitPoint(6));

            Point2D point3 = new Point2D(334, 267);

            Point2D pointL2 = point2.odecistPoint(point3.odecistPoint(point1).rozdelitPoint(6));
            Point2D pointR2 = point2.pridatPoint(point3.odecistPoint(point1).rozdelitPoint(6));


            //2.křivka
            Point2D point4 = new Point2D(478, 155);

            Point2D pointL3 = point3.odecistPoint(point4.odecistPoint(point2).rozdelitPoint(6));
            Point2D pointR3 = point3.pridatPoint(point4.odecistPoint(point2).rozdelitPoint(6));

            Point2D point5 = new Point2D(518, 267);

            Point2D pointL4 = point4.odecistPoint(point5.odecistPoint(point3).rozdelitPoint(6));
            Point2D pointR4 = point4.pridatPoint(point5.odecistPoint(point3).rozdelitPoint(6));


            //3.křivka
            Point2D point6 = new Point2D(428, 355);

            Point2D pointL5 = point5.odecistPoint(point6.odecistPoint(point4).rozdelitPoint(6));
            Point2D pointR5 = point5.pridatPoint(point6.odecistPoint(point4).rozdelitPoint(6));

            Point2D point7 = new Point2D(428, 267);

            Point2D pointL6 = point6.odecistPoint(point7.odecistPoint(point5).rozdelitPoint(6));
            Point2D pointR6 = point6.pridatPoint(point7.odecistPoint(point5).rozdelitPoint(6));

            //4.křivka
            Point2D point8 = new Point2D(638, 205);

            Point2D pointL7 = point7.odecistPoint(point8.odecistPoint(point6).rozdelitPoint(6));
            Point2D pointR7 = point7.pridatPoint(point8.odecistPoint(point6).rozdelitPoint(6));


            Point2D point9 = new Point2D(652, 275);

            Point2D pointL8 = point8.odecistPoint(point9.odecistPoint(point7).rozdelitPoint(6));
            Point2D pointR8 = point8.pridatPoint(point9.odecistPoint(point7).rozdelitPoint(6));

            //5.křivka
            Point2D point10 = new Point2D(590, 255);

            Point2D pointL9 = point9.odecistPoint(point10.odecistPoint(point8).rozdelitPoint(6));
            Point2D pointR9 = point9.pridatPoint(point10.odecistPoint(point8).rozdelitPoint(6));

            Point2D point11 = new Point2D(652, 205);

            Point2D pointL10 = point10.odecistPoint(point11.odecistPoint(point9).rozdelitPoint(6));
            Point2D pointR10 = point10.pridatPoint(point11.odecistPoint(point9).rozdelitPoint(6));


            //6.křivka
            Point2D point12 = new Point2D(652, 275);

            Point2D pointL11 = point11.odecistPoint(point12.odecistPoint(point10).rozdelitPoint(6));
            Point2D pointR11 = point11.pridatPoint(point12.odecistPoint(point10).rozdelitPoint(6));

            Point2D point13 = new Point2D(722, 205);

            Point2D pointL12 = point12.odecistPoint(point13.odecistPoint(point11).rozdelitPoint(6));
            Point2D pointR12 = point12.pridatPoint(point13.odecistPoint(point11).rozdelitPoint(6));


            //7.křivka
            Point2D point14 = new Point2D(792, 255);

            Point2D pointL13 = point13.odecistPoint(point14.odecistPoint(point12).rozdelitPoint(6));
            Point2D pointR13 = point13.pridatPoint(point14.odecistPoint(point12).rozdelitPoint(6));

            Point2D point15 = new Point2D(800, 255);

            Point2D pointL14 = point14.odecistPoint(point15.odecistPoint(point13).rozdelitPoint(6));
            Point2D pointR14 = point14.pridatPoint(point15.odecistPoint(point13).rozdelitPoint(6));

            //8.křivka
            Point2D point16 = new Point2D(800,255);



            //Pomocí vhodné datové struktury spojíme body a vykreslíme tak křivku
            List<Point2D> BezierPoints1 = new ArrayList<>();
            BezierPoints1.add(point1);
            BezierPoints1.add(pointR1);
            BezierPoints1.add(pointL2);
            BezierPoints1.add(point2);

            //Vytvoření nového objektu Bezier1 .... Beziern, kolik chceme
            CubicBezier Bezier1= new CubicBezier(BezierPoints1,0);

            List<Point2D> BezierPoints2 = new ArrayList<>();
            BezierPoints2.add(point2);
            BezierPoints2.add(pointR2);
            BezierPoints2.add(pointL3);
            BezierPoints2.add(point3);

            CubicBezier Bezier2 = new CubicBezier(BezierPoints2,0);

            List<Point2D> BezierPoints3 = new ArrayList<>();
            BezierPoints3.add(point3);
            BezierPoints3.add(pointR3);
            BezierPoints3.add(pointL4);
            BezierPoints3.add(point4);

            CubicBezier Bezier3 = new CubicBezier(BezierPoints3,0);


            List<Point2D> BezierPoints4 = new ArrayList<>();
            BezierPoints4.add(point4);
            BezierPoints4.add(pointR4);
            BezierPoints4.add(pointL5);
            BezierPoints4.add(point5);

            CubicBezier Bezier4 = new CubicBezier(BezierPoints4,0);

            List<Point2D> BezierPoints5 = new ArrayList<>();
            BezierPoints5.add(point5);
            BezierPoints5.add(pointR5);
            BezierPoints5.add(pointL6);
            BezierPoints5.add(point6);

            CubicBezier Bezier5 = new CubicBezier(BezierPoints5,0);

            List<Point2D> BezierPoints6= new ArrayList<>();
            BezierPoints6.add(point6);
            BezierPoints6.add(pointR6);
            BezierPoints6.add(pointL7);
            BezierPoints6.add(point7);

            CubicBezier Bezier6 = new CubicBezier(BezierPoints6,0);

            List<Point2D> BezierPoints7 = new ArrayList<>();
            BezierPoints7.add(point7);
            BezierPoints7.add(pointR7);
            BezierPoints7.add(pointL8);
            BezierPoints7.add(point8);

            CubicBezier Bezier7 = new CubicBezier(BezierPoints7,0);


            List<Point2D> BezierPoints8 = new ArrayList<>();
            BezierPoints8.add(point8);
            BezierPoints8.add(pointR8);
            BezierPoints8.add(pointL9);
            BezierPoints8.add(point9);

            CubicBezier Bezier8 = new CubicBezier(BezierPoints8,0);

            List<Point2D> BezierPoints9 = new ArrayList<>();
            BezierPoints9 .add(point9);
            BezierPoints9 .add(pointR9);
            BezierPoints9 .add(pointL10);
            BezierPoints9 .add(point10);

            CubicBezier Bezier9  = new CubicBezier(BezierPoints9 ,0);


            List<Point2D> BezierPoints10 = new ArrayList<>();
            BezierPoints10 .add(point10);
            BezierPoints10 .add(pointR10);
            BezierPoints10 .add(pointL11);
            BezierPoints10 .add(point11);

            CubicBezier Bezier10  = new CubicBezier(BezierPoints10 ,0);

            List<Point2D> BezierPoints11 = new ArrayList<>();
            BezierPoints11 .add(point11);
            BezierPoints11 .add(pointR11);
            BezierPoints11 .add(pointL12);
            BezierPoints11 .add(point12);


            CubicBezier Bezier11  = new CubicBezier(BezierPoints11 ,0);

            List<Point2D> BezierPoints12 = new ArrayList<>();
            BezierPoints12 .add(point12);
            BezierPoints12.add(pointR12);
            BezierPoints12.add(pointL13);
            BezierPoints12.add(point13);

            CubicBezier Bezier12  = new CubicBezier(BezierPoints12 ,0);

            List<Point2D> BezierPoints13 = new ArrayList<>();
            BezierPoints13 .add(point13);
            BezierPoints13.add(pointR13);
            BezierPoints13.add(pointL14);
            BezierPoints13.add(point14);

            CubicBezier Bezier13  = new CubicBezier(BezierPoints13 ,0);

            //Vykreslení křivek
            GraphicsOperations.drawBezier(vram, Bezier1,10,20);
            GraphicsOperations.drawBezier(vram, Bezier2,10,150);
            GraphicsOperations.drawBezier(vram, Bezier3,10,220);
            GraphicsOperations.drawBezier(vram, Bezier4,10,10);
            GraphicsOperations.drawBezier(vram, Bezier5,10,220);
            GraphicsOperations.drawBezier(vram, Bezier6,10,100);
            GraphicsOperations.drawBezier(vram, Bezier7,10,220);

            GraphicsOperations.drawBezier(vram, Bezier8,10,10);
            GraphicsOperations.drawBezier(vram, Bezier9,10,150);

            GraphicsOperations.drawBezier(vram, Bezier10,10,220);
            GraphicsOperations.drawBezier(vram, Bezier11,10,10);

            GraphicsOperations.drawBezier(vram, Bezier12,10,150);

            GraphicsOperations.drawBezier(vram,Bezier13,10,220);

            imagePanel.setImage(vram.getImage());
        });

        this.add(button5);


        //save image as PNG
        JButton button4 = new JButton();
        button4.setBounds(10,10,120,30);
        button4.setText("Save as PNG");
        button4.addActionListener(e -> {
            saveImageAsPNG();
        });

        this.add(button4);

        infoLabel = new JLabel();
        infoLabel.setBounds(850,10,120,30);
        infoLabel.setText("KU03");
        infoLabel.setFont(new Font(infoLabel.getName(), Font.BOLD, 20));

        this.add(infoLabel);



        JFrame frame = new JFrame("Raster Graphics");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(this);
        frame.setSize(1004, 705);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void drawBresenham() {

            List<Line2D> lines = new ArrayList<>();

            Collections.addAll(lines,
                    new Line2D(new Point2D(50,50), new Point2D(30,70)),
                    new Line2D(new Point2D(50,50), new Point2D(30,60)),
                    new Line2D(new Point2D(50,50), new Point2D(30,50)),
                    new Line2D(new Point2D(50,50), new Point2D(30,40)),
                    new Line2D(new Point2D(50,50), new Point2D(30,30)),
                    new Line2D(new Point2D(50,50), new Point2D(40,30)),
                    new Line2D(new Point2D(50,50), new Point2D(50,30)),
                    new Line2D(new Point2D(50,50), new Point2D(60,30)),
                    new Line2D(new Point2D(50,50), new Point2D(70,30)),
                    new Line2D(new Point2D(50,50), new Point2D(70,40)),
                    new Line2D(new Point2D(50,50), new Point2D(70,50)),
                    new Line2D(new Point2D(50,50), new Point2D(70,60)),
                    new Line2D(new Point2D(50,50), new Point2D(70,70)),
                    new Line2D(new Point2D(50,50), new Point2D(60,70)),
                    new Line2D(new Point2D(50,50), new Point2D(50,70)),
                    new Line2D(new Point2D(50,50), new Point2D(40,70))
            );

            drawLines(lines);

    }


    private void openImage(){

        String userDir = System.getProperty("user.home");
        JFileChooser fc = new JFileChooser(userDir +"/Desktop");
        fc.setDialogTitle("Load Image");

        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

            File file = fc.getSelectedFile();

            try {

                BufferedImage temp = ImageIO.read(file);

                if(temp != null){

                    imagePanel.setImage(temp);

                }

                else {

                    JOptionPane.showMessageDialog(null, "Unable to load image", "Open image: ", JOptionPane.ERROR_MESSAGE);
                }

            }catch (IOException e){

                e.printStackTrace();
            }
        }
    }

    private void saveImageAsPNG(){

        String userDir = System.getProperty("user.home");
        JFileChooser fc = new JFileChooser(userDir +"/Desktop");
        fc.setDialogTitle("Save Image as PNG");

        if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

            File file = fc.getSelectedFile();

            String fname = file.getAbsolutePath();

            if(!fname.endsWith(".png") ) file = new File(fname + ".png");

            try {

                ImageIO.write(imagePanel.getImage(), "png", file);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }





    public static void main(String[] args){
        new MainWindow();
    }

    public void drawLine(Line2D lines){
        GraphicsOperations.drawLine(vram,lines,60);
        imagePanel.setImage(vram.getImage());
    }

    public void drawLines(Collection<Line2D> lines) {
        for (Line2D line2D : lines) {
            drawLine(line2D);
        }
    }


}
