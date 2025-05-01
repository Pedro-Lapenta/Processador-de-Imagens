import java.awt.image.BufferedImage;

public class LimiarizationImage extends Image {

    public LimiarizationImage(String filePath) {
        super(filePath);
    }

    public int[][][] applyLimiarizationFilter(BufferedImage image, int threshold) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][][] result = new int[height][width][3];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                // Converter para tons de cinza
                int gray = (int) (0.3 * red + 0.59 * green + 0.11 * blue);

                // Aplicar limiar
                int newGray = (gray >= threshold) ? gray : 0;

                result[y][x][0] = newGray;
                result[y][x][1] = newGray;
                result[y][x][2] = newGray;
            }
        }

        return result;
    }

    public BufferedImage buildImageFromRGBMatrix(int[][][] matrix) {
        int height = matrix.length;
        int width = matrix[0].length;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int r = matrix[y][x][0];
                int g = matrix[y][x][1];
                int b = matrix[y][x][2];
                int rgb = (r << 16) | (g << 8) | b;
                image.setRGB(x, y, rgb);
            }
        }

        return image;
    }
}
