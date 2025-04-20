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
        bgImage=new ImageIcon("C:\\Users\\hp\\OneDrive\\Desktop\\git clone\\Game Project\\src\\image\\framebg.jpg").getImage();
        JPanel menuPanel=new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage, 0, 0,getWidth(),getHeight(), this);
            }
        };

        JButton s=new JButton("Start Game");
        s.setFont(new Font("Arial", Font.BOLD, 24));

        s.setBorder(BorderFactory.createEmptyBorder());

        s.addActionListener(e -> {
            gamePanel=new GamePanel(this);
            setContentPane(gamePanel);
            gamePanel.requestFocusInWindow();
            revalidate();
        });
        menuPanel.add(s);
        add(menuPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SkySurvivor();
    }
}
