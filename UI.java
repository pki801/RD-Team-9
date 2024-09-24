import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UI extends JFrame implements ActionListener {
    private JLabel imageLabel;
    private JButton browseButton;
    private Dimension screenSize;

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

        // Browse button to select an image file
        browseButton = new JButton("Browse Image");
        browseButton.addActionListener(this);
        add(browseButton, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == browseButton) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    BufferedImage img = ImageIO.read(selectedFile);
                    if(img != null) {
                        Image scaledImage = getScaledImage(img, screenSize.width, screenSize.height);
                        ImageIcon imageIcon = new ImageIcon(scaledImage);
                        imageLabel.setIcon(imageIcon);
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
