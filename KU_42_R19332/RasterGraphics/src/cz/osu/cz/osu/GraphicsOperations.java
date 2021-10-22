package cz.osu.cz.osu;

import java.awt.image.BufferedImage;

public class GraphicsOperations {

    public static void fillBrightness(V_RAM vram, int brightness) {

        brightness = Math.min(255, Math.max(0, brightness));

        for (int y = 0; y < vram.getHeight(); y++)
            for (int x = 0; x < vram.getWidth(); x++)
                vram.getRawData()[y][x] = brightness;
    }

    public static BufferedImage convolve(BufferedImage img, double[][] kernel, double T) {

        BufferedImage outImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        int ry = (kernel.length - 1) / 2;
        int rx = (kernel[0].length - 1) / 2;

        double convolution;

        int r, g, b, gray, intConv;

        for (int y = ry; y < img.getHeight() - ry; y++) {
            for (int x = rx; x < img.getWidth() - rx; x++) {
                convolution = 0;

                for (int ky = -ry; ky <= ry; ky++) {
                    for (int kx = -rx; kx <= rx; kx++) {
                        r = (img.getRGB(x + kx, y + ky) >> 16) & 0xff;
                        g = (img.getRGB(x + kx, y + ky) >> 8) & 0xff;
                        b = (img.getRGB(x + kx, y + ky) >> 0) & 0xff;
                        gray = (int) (r * 0.299 + g * 0.587 + b * 0.114);
                        // gray = (0.3 * r + 0.59 * g + 0.11 * b)/255;
                        convolution += gray * kernel[ky + ry][kx + rx];
                    }
                }

                intConv = convolution < 0 ? 0 : convolution > 255 ? 255 : (int) Math.round(convolution);

                // Math.abs -> pro získání absolutní hodnoty
                int i = Math.abs(intConv - img.getRGB(x, y));
                /*
                 * if (Math.abs(i) < T) { outImage.setRGB(x, y, img.getRGB(x, y)); continue; }
                 */
                // pokud je absolutní rozdíl větší nebo roven prahu T (Math.abs(i)>=T), potom
                // zapiš původní hodnotu pixelu

                if (Math.abs(i) >= T) {
                    outImage.setRGB(x, y, img.getRGB(x, y));

                } else {
                    intConv = 255 << 24 | intConv << 16 | intConv << 8 | intConv;

                    outImage.setRGB(x, y, intConv);

                }

            }
        }

        return outImage;
    }

}
