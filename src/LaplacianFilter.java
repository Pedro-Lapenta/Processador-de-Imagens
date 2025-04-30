import java.awt.image.BufferedImage;

public class LaplacianFilter {

    private String name;

    public LaplacianFilter(String name) {
        this.name = name;
    }

    public int[][][] applyLaplacianFilter(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        int[][][] output = new int[height][width][3];

        // Percorre ignorando as bordas
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {

                int[][] neighborhood = new int[3][3];

                // Converte para tons de cinza usando a média RGB
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dx = -1; dx <= 1; dx++) {
                        int rgb = image.getRGB(x + dx, y + dy);
                        int r = (rgb >> 16) & 0xFF;
                        int g = (rgb >> 8) & 0xFF;
                        int b = rgb & 0xFF;
                        neighborhood[dy + 1][dx + 1] = (r + g + b) / 3;
                    }
                }

                // Aplica máscara Laplaciana (vizinhança 4)
                int laplacian =
                        (4 * neighborhood[1][1]) -
                        (neighborhood[0][1] + neighborhood[1][0] + neighborhood[1][2] + neighborhood[2][1]);

                // Eleva ao quadrado (magnitude do gradiente)
                int magnitude = laplacian * laplacian;

                // Normaliza para 255
                if (magnitude > 255) magnitude = 255;

                // Aplica valor em todos os canais (imagem cinza)
                output[y][x][0] = magnitude;
                output[y][x][1] = magnitude;
                output[y][x][2] = magnitude;
            }
        }

        return output;
    }

    public BufferedImage buildImageFromRGBMatrix(int[][][] matrix) {
        int height = matrix.length;
        int width = matrix[0].length;
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int r = matrix[y][x][0];
                int g = matrix[y][x][1];
                int b = matrix[y][x][2];

                int rgb = (r << 16) | (g << 8) | b;
                result.setRGB(x, y, rgb);
            }
        }

        return result;
    }
}
