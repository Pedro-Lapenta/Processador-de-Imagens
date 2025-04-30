package src;

import java.awt.image.BufferedImage;

public class SobelFilter {
    private String imagePath;

    public SobelFilter(String imagePath) {
        this.imagePath = imagePath;
    }

    public int[][][] applySobelFilter(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][][] result = new int[height][width][3];

        // Máscaras de Sobel
        int[][] gxMask = {
            { -1, 0, 1 },
            { -2, 0, 2 },
            { -1, 0, 1 }
        };

        int[][] gyMask = {
            { -1, -2, -1 },
            {  0,  0,  0 },
            {  1,  2,  1 }
        };

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                for (int c = 0; c < 3; c++) { // 0 = R, 1 = G, 2 = B
                    int gx = 0;
                    int gy = 0;

                    for (int ky = -1; ky <= 1; ky++) {
                        for (int kx = -1; kx <= 1; kx++) {
                            int pixel = (image.getRGB(x + kx, y + ky) >> (8 * (2 - c))) & 0xFF;
                            gx += pixel * gxMask[ky + 1][kx + 1];
                            gy += pixel * gyMask[ky + 1][kx + 1];
                        }
                    }

                    int magnitude = (int) Math.min(255, Math.sqrt(gx * gx + gy * gy));
                    result[y][x][c] = magnitude;
                }
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
