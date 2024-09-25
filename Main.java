import javax.swing.*;


public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            UI frame = new UI();
            frame.setVisible(true);
        });
    }
}

