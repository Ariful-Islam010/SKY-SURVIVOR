import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SkySurvivor extends JFrame {
    JPanel menuPanel;
    GamePanel gamePanel;
     boolean gameOver=false;
    public SkySurvivor() {
        this.setTitle("Sky Survivor");
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        makeMenu();
        this.add(menuPanel);
    }
    void makeMenu(){
        menuPanel = new JPanel();

        menuPanel.setLayout(new GridBagLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

         GridBagConstraints gbc=new GridBagConstraints();
        gbc.insets=new Insets(10, 10, 10, 10);
        JButton startButton=new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        buttonPanel.add(startButton, gbc);
        menuPanel.add(buttonPanel);
    }
    void startGame(){
          gamePanel=new GamePanel(this);
        this.setContentPane(gamePanel);
        gamePanel.requestFocusInWindow();
        this.revalidate();
    }
    public static void main(String[] args){
        SkySurvivor game=new SkySurvivor();
        game.setVisible(true);
    }
}
