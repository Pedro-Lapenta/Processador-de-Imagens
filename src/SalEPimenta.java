import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Random;

public class SalEPimenta extends Image {

    public SalEPimenta(String filePath) {
        super(filePath);
    }

    // Aplica ruído sal e pimenta em 10% dos pixels
    public int[][][] getSaltAndPepperRGBMatrix(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][][] rgbValues = getPixelRGB(image);
        Random random = new Random();

        int totalPixels = width * height;
        int noisyPixels = totalPixels / 10; // 10% dos pixels

        for (int i = 0; i < noisyPixels; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);

            // 50% chance de ser preto ou branco
            if (random.nextBoolean()) {
                // Branco
                rgbValues[x][y][0] = 255;
                rgbValues[x][y][1] = 255;
                rgbValues[x][y][2] = 255;
            } else {
                // Preto
                rgbValues[x][y][0] = 0;
                rgbValues[x][y][1] = 0;
                rgbValues[x][y][2] = 0;
            }
        }

        return rgbValues;
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
