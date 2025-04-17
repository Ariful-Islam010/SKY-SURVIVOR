import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener {
    Timer timer;
    Player player;
    SkySurvivor game;
    ArrayList<Fire> fireballs=new ArrayList<>();
    long lastFireTime=0;
    int maxFireballs=3;
    int fireCooldown=3000;

    public GamePanel(SkySurvivor game) {
        this.game=game;
        this.setFocusable(true);
        player=new Player();

        try {
            player.playerPic=new ImageIcon("C:\\Users\\hp\\OneDrive\\Desktop\\git clone\\Game Project\\src\\image\\player.png").getImage();
        }catch(Exception e) {
            System.out.println("Error loading image: " + e);
        }

        // Key listener with both key code and character support
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key=e.getKeyCode();
                char keyChar = Character.toUpperCase(e.getKeyChar());

                if (key==KeyEvent.VK_UP || keyChar=='U')
                    player.speedY = -5;
                if (key==KeyEvent.VK_DOWN || keyChar=='D')
                    player.speedY = 5;
                if (key==KeyEvent.VK_LEFT || keyChar=='L')
                    player.speedX = -5;
                if (key==KeyEvent.VK_RIGHT || keyChar=='R')
                    player.speedX = 5;
                if ((key==KeyEvent.VK_SPACE || keyChar=='J') && !player.jumping) {
                    player.jumping=true;
                    player.jumpHeight=0;
                }
            }

            public void keyReleased(KeyEvent e) {
                int key=e.getKeyCode();
                char keyChar=Character.toUpperCase(e.getKeyChar());

                if ((key==KeyEvent.VK_UP || key==KeyEvent.VK_DOWN ||
                        keyChar=='U' || keyChar=='D') && player.speedY!= 0) {
                    player.speedY=0;
                }
                if ((key==KeyEvent.VK_LEFT || key==KeyEvent.VK_RIGHT ||
                        keyChar=='L' || keyChar=='R') && player.speedX!=0) {
                    player.speedX=0;
                }
            }
        });

        timer=new Timer(16, this);
        timer.start();
    }

    private void createFireballs() {
        for (int i=0;i<maxFireballs;i++) {
            fireballs.add(new Fire((int)(Math.random()*(getWidth()-30)), -30));
        }
    }

    public void actionPerformed(ActionEvent e) {
        player.update(getWidth(),getHeight());

        //Create new fireballs
        if (System.currentTimeMillis()-lastFireTime>fireCooldown) {
            createFireballs();
            lastFireTime=System.currentTimeMillis();
        }

        //Update and remove inactive fireballs
        for (int i=fireballs.size()-1;i>=0;i--) {
            Fire fire=fireballs.get(i);
            fire.update(getHeight());
            if (!fire.active) fireballs.remove(i);
        }
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw player and fireballs
        g.drawImage(player.playerPic, player.x, player.y, player.width, player.height, this);
        for (Fire fire:fireballs) {
            g.drawImage(fire.firePic,fire.x,fire.y,fire.width,fire.height, this);
        }
    }
}
