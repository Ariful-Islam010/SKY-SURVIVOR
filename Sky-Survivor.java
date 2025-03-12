import javax.swing.*;
import java.awt.*;

public class SkySurvivor extends JFrame {
    private JPanel startPanel;

    public SkySurvivor() {
        setTitle("Sky Survivor");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createStartPanel();
        add(startPanel);
    }

    private void createStartPanel() {
        startPanel = new JPanel();
        startPanel.setLayout(new GridBagLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        // No functionality yet
        buttonPanel.add(startButton, gbc);

        startPanel.add(buttonPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SkySurvivor game = new SkySurvivor();
            game.setVisible(true);
        });
    }
}

