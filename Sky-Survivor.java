import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SkySurvivor extends JFrame {
    private JPanel startPanel;

    public SkySurvivor() {
        setTitle("Sky Survivor");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        startPanel = new JPanel();
        startPanel.setLayout(new GridBagLayout());

        JButton startButton = new JButton("Start Game");
        JButton exitButton = new JButton("Exit");

        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        exitButton.setFont(new Font("Arial", Font.BOLD, 24));

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // No functionality yet
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        buttonPanel.add(startButton, gbc);
        buttonPanel.add(exitButton, gbc);

        startPanel.add(buttonPanel);
        add(startPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SkySurvivor game = new SkySurvivor();
                game.setVisible(true);
            }
        });
    }
}
