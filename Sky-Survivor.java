import javax.swing.*;
import java.awt.*;

public class SkySurvivor extends JFrame {
    GamePanel gamePanel;

    public SkySurvivor() {
        setTitle("Sky Survivor");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        //Create menu with button
        JPanel menuPanel=new JPanel(new GridBagLayout());
        JButton startButton=new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.addActionListener(e -> {
            gamePanel=new GamePanel(this);
            setContentPane(gamePanel);
            gamePanel.requestFocusInWindow();
            revalidate();
        });

        menuPanel.add(startButton);
        add(menuPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SkySurvivor();
    }
}
