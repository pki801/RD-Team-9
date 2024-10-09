import javax.swing.*;
import java.awt.*;

public class NestedPanelExample extends JFrame {

    public NestedPanelExample() {
        // Set up the main frame
        setTitle("Nested Panel Example");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Sub-panel with FlowLayout
        JPanel subPanel = new JPanel();
        subPanel.setLayout(new FlowLayout());
        subPanel.setBackground(Color.LIGHT_GRAY);

        // Add components to the sub-panel
        subPanel.add(new JLabel("Label inside Sub-Panel"));
        subPanel.add(new JButton("Button inside Sub-Panel"));

        // Add the sub-panel to the main panel (CENTER position)
        mainPanel.add(subPanel, BorderLayout.CENTER);

        // Add another panel to the main panel (SOUTH position)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setBackground(Color.PINK);
        bottomPanel.add(new JLabel("Another Panel at the Bottom"));
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Add the main panel to the frame
        add(mainPanel);
    }
}
