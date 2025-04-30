package src;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ImageGUI extends JFrame {

    private JLabel originalLabel;
    private JLabel processedLabel;
    private JComboBox<String> filterSelector;
    private BufferedImage originalImage;
    private BufferedImage processedImage;

    public ImageGUI() {
        setTitle("Processador de Imagem");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel superior com botões
        JPanel topPanel = new JPanel();
        JButton loadButton = new JButton("Carregar Imagem");
        JButton processButton = new JButton("Aplicar Filtro");
        JButton saveButton = new JButton("Salvar Imagem Processada");
        filterSelector = new JComboBox<>(new String[] {
            "Inverter Cores",
            "Equalizar Histograma",
            "Ruído Sal e Pimenta",
            "Filtro de Média",
            "Filtro de Mediana",
            "Binarização ou limiarização",
            "Laplaciano",
            "Sobel",
            "Compressão Dinâmica"
        });

        topPanel.add(loadButton);
        topPanel.add(filterSelector);
        topPanel.add(processButton);
        topPanel.add(saveButton);

        add(topPanel, BorderLayout.NORTH);

        // Painel central com as imagens
        JPanel imagePanel = new JPanel(new GridLayout(1, 2));
        originalLabel = new JLabel("Imagem Original", JLabel.CENTER);
        processedLabel = new JLabel("Imagem Processada", JLabel.CENTER);
        imagePanel.add(originalLabel);
        imagePanel.add(processedLabel);

        add(imagePanel, BorderLayout.CENTER);

        // Ações dos botões
        loadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    originalImage = ImageIO.read(selectedFile);
                    if (originalImage != null) {
                        originalLabel.setIcon(new ImageIcon(originalImage.getScaledInstance(350, -1, java.awt.Image.SCALE_SMOOTH)));
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao carregar imagem: " + ex.getMessage());
                }
            }
        });

        processButton.addActionListener(e -> {
            if (originalImage == null) {
                JOptionPane.showMessageDialog(this, "Carregue uma imagem primeiro.");
                return;
            }

            String selectedFilter = (String) filterSelector.getSelectedItem();

            try {
                if ("Inverter Cores".equals(selectedFilter)) {
                    InvertedImage handler = new InvertedImage("");
                    int[][][] matrix = handler.getInvertedRGBMatrix(originalImage);
                    processedImage = handler.buildImageFromRGBMatrix(matrix);
                } else if ("Equalizar Histograma".equals(selectedFilter)) {
                    EqualizedImage handler = new EqualizedImage("");
                    int[][][] matrix = handler.getEqualizedRGBMatrix(originalImage);
                    processedImage = handler.buildImageFromRGBMatrix(matrix);
                } else if ("Ruído Sal e Pimenta".equals(selectedFilter)) {
                    SalEPimenta handler = new SalEPimenta("");
                    int[][][] matrix = handler.getSaltAndPepperRGBMatrix(originalImage);
                    processedImage = handler.buildImageFromRGBMatrix(matrix);
                } else if ("Filtro de Média".equals(selectedFilter)) {
                    MediaFilter handler = new MediaFilter("");
                    int[][][] matrix = handler.applyMediaFilter(originalImage);
                    processedImage = handler.buildImageFromRGBMatrix(matrix);
                } else if ("Filtro de Mediana".equals(selectedFilter)) {
                    MedianFilter handler = new MedianFilter("");
                    int[][][] matrix = handler.applyMedianFilter(originalImage);
                    processedImage = handler.buildImageFromRGBMatrix(matrix);
                } else if ("Binarização ou limiarização".equals(selectedFilter)) {
                    String input = JOptionPane.showInputDialog(this, "Informe o limiar (0 a 255):", "128");
                    if (input != null) {
                        try {
                            int threshold = Integer.parseInt(input);
                            if (threshold < 0 || threshold > 255) {
                                JOptionPane.showMessageDialog(this, "O limiar deve estar entre 0 e 255.");
                                return;
                            }
                            BinarizedImage handler = new BinarizedImage("");
                            int[][][] matrix = handler.applyBinarizationFilter(originalImage, threshold);
                            processedImage = handler.buildImageFromRGBMatrix(matrix);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this, "Valor inválido para limiar.");
                            return;
                        }
                    } else {
                        return;  // Usuário cancelou o input
                    }
                } else if ("Laplaciano".equals(selectedFilter)) {
                    LaplacianFilter handler = new LaplacianFilter("");
                    int[][][] matrix = handler.applyLaplacianFilter(originalImage);
                    processedImage = handler.buildImageFromRGBMatrix(matrix);
                }
                else if ("Sobel".equals(selectedFilter)) {
                    SobelFilter handler = new SobelFilter("");
                    int[][][] matrix = handler.applySobelFilter(originalImage);
                    processedImage = handler.buildImageFromRGBMatrix(matrix);
                }          
                else if ("Compressão Dinâmica".equals(selectedFilter)) {
                    String inputC = JOptionPane.showInputDialog(this, "Informe o valor de c (ex: 1.0):", "1.0");
                    String inputGamma = JOptionPane.showInputDialog(this, "Informe o valor de γ (gamma) (ex: 0.5):", "0.5");
                    if (inputC != null && inputGamma != null) {
                        try {
                            double c = Double.parseDouble(inputC);
                            double gamma = Double.parseDouble(inputGamma);
                
                            DynamicRangeCompression handler = new DynamicRangeCompression("");
                            int[][][] matrix = handler.applyDynamicRangeCompressionFilter(originalImage, c, gamma);
                            processedImage = handler.buildImageFromRGBMatrix(matrix);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this, "Valores inválidos para c ou gamma.");
                            return;
                        }
                    } else {
                        return;  // Cancelado
                    }
                }
                      

                processedLabel.setIcon(new ImageIcon(processedImage.getScaledInstance(350, -1, java.awt.Image.SCALE_SMOOTH)));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao processar imagem: " + ex.getMessage());
            }
        });

        saveButton.addActionListener(e -> {
            if (processedImage == null) {
                JOptionPane.showMessageDialog(this, "Não há imagem processada para salvar.");
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Salvar Imagem");
            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                try {
                    ImageIO.write(processedImage, "png", fileToSave);
                    JOptionPane.showMessageDialog(this, "Imagem salva com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar imagem: " + ex.getMessage());
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImageGUI gui = new ImageGUI();
            gui.setVisible(true);
        });
    }
}
