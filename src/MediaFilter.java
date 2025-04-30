package src;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class MediaFilter extends Image {

    public MediaFilter(String filePath) {
        super(filePath);
    }

    // Aplica o filtro de média (3x3)
    public int[][][] applyMediaFilter(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][][] rgbValues = getPixelRGB(image);
        int[][][] filteredRGB = new int[width][height][3];

        // Máscara de média 3x3
        int maskSize = 3;
        int offset = maskSize / 2;

        for (int x = offset; x < width - offset; x++) {
            for (int y = offset; y < height - offset; y++) {
                for (int channel = 0; channel < 3; channel++) {
                    int sum = 0;
                    // Aplicar a máscara 3x3
                    for (int dx = -offset; dx <= offset; dx++) {
                        for (int dy = -offset; dy <= offset; dy++) {
                            sum += rgbValues[x + dx][y + dy][channel];
                        }
                    }
                    // A média é a soma dividida pelo número de elementos (9)
                    filteredRGB[x][y][channel] = sum / (maskSize * maskSize);
                }
            }
        }

        return filteredRGB;
    }

    // Converte a matriz RGB filtrada de volta para BufferedImage
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
