package src;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class EqualizedImage extends Image {

    public EqualizedImage(String filePath) {
        super(filePath);
    }

    public int[][][] getEqualizedRGBMatrix(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][][] rgbValues = getPixelRGB(image);
        int[][][] equalizedRGB = new int[width][height][3];

        for (int channel = 0; channel < 3; channel++) {
            int[] histogram = new int[256];
            int[] cdf = new int[256];

            // Compute histogram
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    histogram[rgbValues[x][y][channel]]++;
                }
            }

            // Compute cumulative distribution function (CDF)
            cdf[0] = histogram[0];
            for (int i = 1; i < 256; i++) {
                cdf[i] = cdf[i - 1] + histogram[i];
            }

            // Normalize CDF
            int totalPixels = width * height;
            int cdfMin = 0;
            for (int i = 0; i < 256; i++) {
                if (cdf[i] != 0) {
                    cdfMin = cdf[i];
                    break;
                }
            }

            int[] equalized = new int[256];
            for (int i = 0; i < 256; i++) {
                equalized[i] = Math.round(((float)(cdf[i] - cdfMin) / (totalPixels - cdfMin)) * 255);
                if (equalized[i] < 0) equalized[i] = 0;
            }

            // Apply equalization to channel
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int originalValue = rgbValues[x][y][channel];
                    equalizedRGB[x][y][channel] = equalized[originalValue];
                }
            }
        }

        return equalizedRGB;
    }

    public BufferedImage buildImageFromRGBMatrix(int[][][] rgbMatrix) {
        int width = rgbMatrix.length;
        int height = rgbMatrix[0].length;

        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = resultImage.getRaster();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int red = rgbMatrix[x][y][0];
                int green = rgbMatrix[x][y][2];
                int blue = rgbMatrix[x][y][1];
                int[] rgb = { red, green, blue };
                raster.setPixel(x, y, rgb);
            }
        }

        return resultImage;
    }
}
