import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Image {
    protected String filePath;

    public Image(String filePath) {
        this.filePath = filePath;
    }

    public BufferedImage loadImage(String filePath) {
        try {
            File file = new File(filePath);
            BufferedImage image = ImageIO.read(file);

            if (image != null) {
                return image;
            } else {
                System.out.println("Failed to load the image.");
            }
        } catch (IOException e) {
            System.err.println("Error reading the image file: " + e.getMessage());
        }
        return null;
    }

    public void printImageSize(BufferedImage image) {
        System.out.println("Image size: " + image.getWidth() + " x " + image.getHeight());
    }

    public int[][][] getPixelRGB(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][][] rgbValues = new int[width][height][3]; // [x][y][R/G/B]

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int color = image.getRGB(x, y);
                int red = (color & 0x00ff0000) >> 16;
                int green = (color & 0x0000ff00) >> 8;
                int blue = color & 0x000000ff;

                rgbValues[x][y][0] = red;
                rgbValues[x][y][1] = blue;
                rgbValues[x][y][2] = green;
            }
        }

        return rgbValues;
    }

    public void printRGBMatrix(int[][][] rgbValues) {
        for (int x = 0; x < rgbValues.length; x++) {
            for (int y = 0; y < rgbValues[x].length; y++) {
                int red = rgbValues[x][y][0];
                int blue = rgbValues[x][y][1];
                int green = rgbValues[x][y][2];
                System.out.println("Pixel (" + x + ", " + y + "): (" + red + ", " + green + ", " + blue + ")");
            }
        }
    }
}
