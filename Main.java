import javax.swing.*;


public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            DecodeUI frame = new DecodeUI();
            frame.setVisible(true);
        });
    }
}

