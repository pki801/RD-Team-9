import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UI extends JFrame implements ActionListener {
    private final JLabel imageLabel;
    private final JButton browseButton;
    private final Dimension screenSize;
    public BufferedImage img;

    public UI() {
        // Set up the frame
        setTitle("Image Display");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Label to display the image
        imageLabel = new JLabel("", SwingConstants.CENTER);
        add(imageLabel, BorderLayout.CENTER);

        // Label to display binary code
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        add(new JScrollPane(textArea), BorderLayout.EAST);

        // Browse button to select an image file
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        browseButton = new JButton("Browse Image");
        browseButton.addActionListener(this);
        browseButton.setPreferredSize(new Dimension(450, 50));
        buttonPanel.add(browseButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == browseButton) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    img = ImageIO.read(selectedFile);
                    if(img != null) {
                        Image scaledImage = getScaledImage(img, screenSize.width, screenSize.height);
                        ImageIcon imageIcon = new ImageIcon(scaledImage);
                        imageLabel.setIcon(imageIcon);
                        ImageToBinary.getBinaryData(img);
                    } else {
                        JOptionPane.showMessageDialog(this, "The selected file is not a valid image.");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error loading image.");
                }
            }
        }
    }

    private Image getScaledImage(BufferedImage srcImg, int width, int height) {
        int originalWidth = srcImg.getWidth();
        int originalHeight = srcImg.getHeight();

        double widthScale = (double) width / originalWidth;
        double heightScale = (double) height / originalWidth;
        double scale = Math.min(widthScale, heightScale);

        int newWidth = (int) (originalWidth * scale);
        int newHeight = (int) (originalHeight * scale);

        return srcImg.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    }

}
