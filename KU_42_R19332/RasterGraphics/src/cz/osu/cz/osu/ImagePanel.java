package cz.osu.cz.osu;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private BufferedImage image;

    public ImagePanel() {

        setOpaque(true);
        setBackground(new Color(179, 187, 203, 255));
    }

    public BufferedImage getImage() {

        return image;
    }

    public void setImage(BufferedImage image) {

        this.image = image;
        this.repaint();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (image != null) {

            double ratioX = 1.0 * this.getWidth() / image.getWidth();
            double ratioY = 1.0 * this.getHeight() / image.getHeight();

            double ratio = Math.min(ratioX, ratioY);

            int w = (int) (image.getWidth() * ratio);
            int h = (int) (image.getHeight() * ratio);

            g.drawImage(image, (this.getWidth() - w) / 2, (this.getHeight() - h) / 2, w, h, null);

        }
    }
}
