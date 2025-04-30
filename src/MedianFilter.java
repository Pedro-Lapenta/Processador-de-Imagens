package src;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Arrays;

public class MedianFilter extends Image {

    public MedianFilter(String filePath) {
        super(filePath);
    }

    // Aplica o filtro de mediana 3x3 na imagem
    public int[][][] applyMedianFilter(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][][] rgbValues = getPixelRGB(image);
        int[][][] filteredRGB = new int[width][height][3];

        // Percorre a imagem, ignorando as bordas
        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                for (int channel = 0; channel < 3; channel++) {
                    // Pega os valores da vizinhança 3x3
                    int[] neighborhood = new int[9];
                    int index = 0;
                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 1; dy++) {
                            neighborhood[index++] = rgbValues[x + dx][y + dy][channel];
                        }
                    }

                    // Ordena os valores e escolhe o valor mediano
                    Arrays.sort(neighborhood);
                    filteredRGB[x][y][channel] = neighborhood[4];  // Valor mediano
                }
            }
        }

        return filteredRGB;
    }

    // Constrói uma imagem a partir da matriz RGB filtrada
    public BufferedImage buildImageFromRGBMatrix(int[][][] rgbMatrix) {
        int width = rgbMatrix.length;
        int height = rgbMatrix[0].length;

        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = resultImage.getRaster();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int red = rgbMatrix[x][y][0];
                int green = rgbMatrix[x][y][2];  // Ordem mantida como na sua classe anterior
                int blue = rgbMatrix[x][y][1];
                int[] rgb = { red, green, blue };
                raster.setPixel(x, y, rgb);
            }
        }

        return resultImage;
    }
}
