package src;

import java.awt.image.BufferedImage;

public class DynamicRangeCompression {
    private String imagePath;

    public DynamicRangeCompression(String imagePath) {
        this.imagePath = imagePath;
    }

    public int[][][] applyDynamicRangeCompressionFilter(BufferedImage image, double c, double gamma) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][][] result = new int[height][width][3];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);

                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                // Normaliza e aplica a fórmula: S = c * r^gamma
                int newR = (int) Math.min(255, c * Math.pow(r / 255.0, gamma) * 255);
                int newG = (int) Math.min(255, c * Math.pow(g / 255.0, gamma) * 255);
                int newB = (int) Math.min(255, c * Math.pow(b / 255.0, gamma) * 255);

                result[y][x][0] = newR;
                result[y][x][1] = newG;
                result[y][x][2] = newB;
            }
        }

        return result;
    }

    public BufferedImage buildImageFromRGBMatrix(int[][][] rgbMatrix) {
        int height = rgbMatrix.length;
        int width = rgbMatrix[0].length;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int r = rgbMatrix[y][x][0];
                int g = rgbMatrix[y][x][1];
                int b = rgbMatrix[y][x][2];
                int rgb = (r << 16) | (g << 8) | b;
                image.setRGB(x, y, rgb);
            }
        }

        return image;
    }
}
