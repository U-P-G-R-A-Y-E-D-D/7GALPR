package cz.osu.cz.osu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainWindow extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ImagePanel imagePanel;
    private V_RAM vram;

    // private Triangle2D t1;
    // private Triangle2D t2;

    public MainWindow() {

        initialize();

        vram = new V_RAM(1880, 970);

        GraphicsOperations.fillBrightness(vram, 255);

        imagePanel.setImage(vram.getImage());
    }

    private void initialize() {

        setLayout(null);
        setFocusable(true);
        requestFocusInWindow();

        imagePanel = new ImagePanel();
        imagePanel.setBounds(10, 60, 1880, 970);
        this.add(imagePanel);

        // open image
        JButton button = new JButton();
        button.setBounds(100, 10, 100, 30);
        button.setText("Load Image");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                openImage();
            }
        });

        this.add(button);

        // save image as PNG
        JButton button4 = new JButton();
        button4.setBounds(0, 10, 100, 30);
        button4.setText("Save as PNG");
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveImageAsPNG();
            }
        });

        this.add(button4);

        // Convolve
        JButton button2 = new JButton();
        button2.setBounds(200, 10, 100, 30);
        button2.setText("Rozmazání");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                double[][] kernel = { { 1.0 / 9.0, 1.0 / 9.0, 1.0 / 9.0

                        }, { 1.0 / 9.0, 1.0 / 9.0, 1.0 / 9.0 }, { 1.0 / 9.0, 1.0 / 9.0, 1.0 / 9.0 } };

                BufferedImage img = imagePanel.getImage();

                // img = GraphicsOperations.convolve(img, kernel, 10000000);
                img = GraphicsOperations.convolve(img, kernel, 11000000);
                // img = GraphicsOperations.convolve(img, kernel, 18000000);
                // img = GraphicsOperations.convolve(img, kernel, 80000000);
                imagePanel.setImage(img);

            }
        });

        this.add(button2);

        JButton button5 = new JButton();
        button5.setBounds(300, 10, 100, 30);
        button5.setText("Kirsch");
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                double[][] kernel = { { 3, 3, -5 }, { 3, 0, -5 }, { 3, 3, -5 } };

                BufferedImage img = imagePanel.getImage();

                img = GraphicsOperations.convolve(img, kernel, 11000000);
                // img = GraphicsOperations.convolve(img, kernel, 20000000);
                // img = GraphicsOperations.convolve(img, kernel, 50000000);
                imagePanel.setImage(img);

            }
        });

        this.add(button5);

        JButton button6 = new JButton();
        button6.setBounds(400, 10, 100, 30);
        button6.setText("Robinson");
        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                double[][] kernel = { { 1, 1, -1 }, { 1, -2, -1 }, { 1, 1, -1 } };

                BufferedImage img = imagePanel.getImage();

                // img = GraphicsOperations.convolve(img, kernel, 20000000);
                img = GraphicsOperations.convolve(img, kernel, 10000000);
                // img = GraphicsOperations.convolve(img, kernel, 50000000);
                imagePanel.setImage(img);

            }
        });

        this.add(button6);

        JButton button7 = new JButton();
        button7.setBounds(500, 10, 100, 30);
        button7.setText("Prewitt");
        button7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                double[][] kernel = { { 1, 0, -1 }, { 1, 0, -1 }, { 1, 0, -1 } };

                BufferedImage img = imagePanel.getImage();

                // img = GraphicsOperations.convolve(img, kernel, 10000000);
                // img = GraphicsOperations.convolve(img, kernel, 15000000);
                img = GraphicsOperations.convolve(img, kernel, 11000000);
                imagePanel.setImage(img);

            }
        });

        this.add(button7);

        JButton button8 = new JButton();
        button8.setBounds(600, 10, 100, 30);
        button8.setText("Solbe");
        button8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                double[][] kernel = { { 1, 0, -1 }, { 2, 0, -2 }, { 1, 0, -1 } }; // solbeho alg

                BufferedImage img = imagePanel.getImage();

                img = GraphicsOperations.convolve(img, kernel, 11000000);
                // img = GraphicsOperations.convolve(img, kernel, 20000000);
                // img = GraphicsOperations.convolve(img, kernel, 50000000);
                imagePanel.setImage(img);

            }
        });

        this.add(button8);

        JButton button9 = new JButton();
        button9.setBounds(700, 10, 100, 30);
        button9.setText("Sharpen");
        button9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // double[][] kernel = { { -1, -1, -1 }, { -1, 9, -1, }, { -1, -1, -1 } };

                double[][] kernel = { { 0, -1, 0 }, { -1, 5, -1, }, { 0, -1, 0 } };
                BufferedImage img = imagePanel.getImage();

                // img = GraphicsOperations.convolve(img, kernel, 10000000);
                img = GraphicsOperations.convolve(img, kernel, 11000000);
                // img = GraphicsOperations.convolve(img, kernel, 20000000);
                // img = GraphicsOperations.convolve(img, kernel, 50000000);
                imagePanel.setImage(img);

            }
        });

        this.add(button9);

        JButton button10 = new JButton();
        button10.setBounds(800, 10, 100, 30);
        button10.setText("Edgeenhancement");
        button10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                double[][] kernel = { { 0, 0, 0 }, { -1, 1, 0 }, { 0, 0, 0 } };

                BufferedImage img = imagePanel.getImage();

                // img = GraphicsOperations.convolve(img, kernel, 10000000);
                img = GraphicsOperations.convolve(img, kernel, 11000000);
                // img = GraphicsOperations.convolve(img, kernel, 20000000);
                // img = GraphicsOperations.convolve(img, kernel, 50000000);
                imagePanel.setImage(img);

            }
        });

        this.add(button10);

        JButton button11 = new JButton();
        button11.setBounds(900, 10, 100, 30);
        button11.setText("Findedges");
        button11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                double[][] kernel = { { 0, 1, 0 }, { 1, -4, 1 }, { 0, 1, 0 } };

                BufferedImage img = imagePanel.getImage();
                // img = GraphicsOperations.convolve(img, kernel, 10000000);
                img = GraphicsOperations.convolve(img, kernel, 11000000);
                // img = GraphicsOperations.convolve(img, kernel, 20000000);
                // img = GraphicsOperations.convolve(img, kernel, 50000000);
                imagePanel.setImage(img);

            }
        });

        this.add(button11);

        JButton button12 = new JButton();
        button12.setBounds(1000, 10, 100, 30);
        button12.setText("Emboss");
        button12.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                double[][] kernel = { { -2, -1, 0 }, { -1, 1, 1 }, { 0, 1, 2 } };

                BufferedImage img = imagePanel.getImage();

                // img = GraphicsOperations.convolve(img, kernel, 12000000);
                img = GraphicsOperations.convolve(img, kernel, 1100000);
                // img = GraphicsOperations.convolve(img, kernel, 20000000);
                // img = GraphicsOperations.convolve(img, kernel, 50000000);
                imagePanel.setImage(img);

            }
        });

        this.add(button12);

        JButton button13 = new JButton();
        button13.setBounds(1100, 10, 100, 30);
        button13.setText("Outline");
        button13.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                double[][] kernel = { { -1, -1, -1 }, { -1, 8, -1 }, { -1, -1, -1 } };

                BufferedImage img = imagePanel.getImage();

                // img = GraphicsOperations.convolve(img, kernel, 10000000);
                img = GraphicsOperations.convolve(img, kernel, 11000000);
                // img = GraphicsOperations.convolve(img, kernel, 20000000);
                // img = GraphicsOperations.convolve(img, kernel, 50000000);
                imagePanel.setImage(img);

            }
        });

        this.add(button13);

        // delitel = 45; //Laplaceův algo.
        JButton button14 = new JButton();
        button14.setBounds(1200, 10, 100, 30);
        button14.setText("Laplacen2");
        button14.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                double[][] kernel = { { 2, -1, 2 }, { -1, -4, -1 }, { 2, -1, 2 } };

                BufferedImage img = imagePanel.getImage();

                // img = GraphicsOperations.convolve(img, kernel, 10000000);
                img = GraphicsOperations.convolve(img, kernel, 11000000);
                // img = GraphicsOperations.convolve(img, kernel, 20000000);
                // img = GraphicsOperations.convolve(img, kernel, 50000000);
                imagePanel.setImage(img);

            }
        });

        this.add(button14);

        // Laplaceův algo
        JButton button18 = new JButton();
        button18.setBounds(1300, 10, 100, 30);
        button18.setText("Laplacen1");
        button18.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                double[][] kernel = { { 0, 1, 0 }, { 1, -4, 1 }, { 0, 1, 0 } };

                BufferedImage img = imagePanel.getImage();

                // img = GraphicsOperations.convolve(img, kernel, 10000000);
                img = GraphicsOperations.convolve(img, kernel, 11000000);
                // img = GraphicsOperations.convolve(img, kernel, 20000000);
                // img = GraphicsOperations.convolve(img, kernel, 50000000);
                imagePanel.setImage(img);

            }
        });

        this.add(button18);

        JButton button19 = new JButton();
        button19.setBounds(1400, 10, 100, 30);
        button19.setText("Identity");
        button19.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                double[][] kernel = { { 0, 0, 0 }, { 0, 1, 0 }, { 0, 0, 0 } };

                BufferedImage img = imagePanel.getImage();

                // img = GraphicsOperations.convolve(img, kernel, 10000000);
                img = GraphicsOperations.convolve(img, kernel, 11000000);
                // img = GraphicsOperations.convolve(img, kernel, 20000000);
                // img = GraphicsOperations.convolve(img, kernel, 50000000);
                imagePanel.setImage(img);

            }
        });

        this.add(button19);

        JButton button20 = new JButton();
        button20.setBounds(1500, 10, 100, 30);
        button20.setText("Median");
        button20.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // double[][] kernel = { { 1, 4, 7 }, { 8, 9, 5 }, { 2, 3, 6 } };

                double[][] kernel = { { 1, 1, 1 }, { 1, 3, 1 }, { 1, 1, 1 } };

                BufferedImage img = imagePanel.getImage();

                // img = GraphicsOperations.convolve(img, kernel, 10000000);
                img = GraphicsOperations.convolve(img, kernel, 11000000);
                // img = GraphicsOperations.convolve(img, kernel, 20000000);
                // img = GraphicsOperations.convolve(img, kernel, 50000000);
                imagePanel.setImage(img);

            }
        });

        this.add(button20);

        JFrame frame = new JFrame("Raster Graphics");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.setSize(1920, 1080);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void openImage() {

        String userDir = System.getProperty("user.home");
        JFileChooser fc = new JFileChooser(userDir + "/Desktop");
        fc.setDialogTitle("Load Image");

        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

            File file = fc.getSelectedFile();

            try {

                BufferedImage temp = ImageIO.read(file);

                if (temp != null) {

                    imagePanel.setImage(temp);

                } else {

                    JOptionPane.showMessageDialog(null, "Unable to load image", "Open image: ",
                            JOptionPane.ERROR_MESSAGE);
                }

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    private void saveImageAsPNG() {

        String userDir = System.getProperty("user.home");
        JFileChooser fc = new JFileChooser(userDir + "/Desktop");
        fc.setDialogTitle("Save Image as PNG");

        if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

            File file = fc.getSelectedFile();

            String fname = file.getAbsolutePath();

            if (!fname.endsWith(".png"))
                file = new File(fname + ".png");

            try {

                ImageIO.write(imagePanel.getImage(), "png", file);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        new MainWindow();
    }
}
