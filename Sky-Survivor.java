import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class SkySurvivor extends JFrame {
    GamePanel gamePanel;
    Image bgImage;

    public SkySurvivor() {
        setTitle("Sky Survivor");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        try {
            bgImage=new ImageIcon("C:\\Users\\hp\\OneDrive\\Desktop\\Game Project\\src\\image\\framebg.jpg").getImage();
        } catch(Exception e) {
            System.out.println("Error loading background image: " + e);
        }
        showMainMenu();
        setVisible(true);
    }

    public void showMainMenu() {
        JPanel menuPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        JPanel buttonPanel=new JPanel(new GridLayout(2, 1, 0, 20));
        buttonPanel.setOpaque(false);

        JButton startButton=new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        startButton.setBackground(new Color(70, 130, 180));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);

        startButton.addActionListener(e -> {
            gamePanel=new GamePanel(this);
            setContentPane(gamePanel);
            gamePanel.requestFocusInWindow();
            revalidate();
        });

        // Exit button
        JButton exitButton=new JButton("Exit Game");
        exitButton.setFont(new Font("Arial", Font.BOLD, 24));
        exitButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        exitButton.setBackground(new Color(178, 34, 34));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);

        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        buttonPanel.add(startButton);
        buttonPanel.add(exitButton);
        menuPanel.add(buttonPanel);
        setContentPane(menuPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SkySurvivor());
    }
}
