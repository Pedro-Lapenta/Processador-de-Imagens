package src;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class InvertedImage extends Image {
    
    public InvertedImage(String filePath) {
        super(filePath);
    }

    // Retorna matriz com os valores RGB invertidos
    public int[][][] getInvertedRGBMatrix(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][][] rgbValues = getPixelRGB(image);
        int[][][] invertedRGB = new int[width][height][3];
    
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                invertedRGB[x][y][0] = 255 - rgbValues[x][y][0]; // Red
                invertedRGB[x][y][1] = 255 - rgbValues[x][y][1]; // Blue
                invertedRGB[x][y][2] = 255 - rgbValues[x][y][2]; // Green
            }
        }
    
        return invertedRGB;
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
