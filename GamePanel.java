import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener {
    Timer t;
    Player p;
    SkySurvivor game;
    ArrayList<Fire> fireballs=new ArrayList<>();
    long lastFireTime=0;
    int maxFireballs=3;
    int fireCooldown=3000; //mili second
    int fireCount=0;

    public GamePanel(SkySurvivor game) {
        this.game=game;
        this.setFocusable(true);
        p=new Player();

        try {
            p.playerPic=new ImageIcon("C:\\Users\\hp\\OneDrive\\Desktop\\git clone\\Game Project\\src\\image\\player.png").getImage();
        }catch(Exception e) {
            System.out.println("Error loading image: "+e);
        }

        // Key listener with both key code and character support
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key=e.getKeyCode();
                char keyChar=Character.toUpperCase(e.getKeyChar());

                if (key==KeyEvent.VK_UP || keyChar=='U')
                    p.speedY = -5;
                if (key==KeyEvent.VK_DOWN || keyChar=='D')
                    p.speedY = 5;
                if (key==KeyEvent.VK_LEFT || keyChar=='L')
                    p.speedX = -5;
                if (key==KeyEvent.VK_RIGHT || keyChar=='R')
                    p.speedX = 5;
                if ((key==KeyEvent.VK_SPACE || keyChar=='J') && !p.jumping) {
                    p.jumping=true;
                    p.jumpHeight=0;
                }
            }

            public void keyReleased(KeyEvent e) {
                int key=e.getKeyCode();
                char keyChar=Character.toUpperCase(e.getKeyChar());

                if ((key==KeyEvent.VK_UP || key==KeyEvent.VK_DOWN ||
                        keyChar=='U' || keyChar=='D') && p.speedY!= 0) {
                    p.speedY=0;
                }
                if ((key==KeyEvent.VK_LEFT || key==KeyEvent.VK_RIGHT ||
                        keyChar=='L' || keyChar=='R') && p.speedX!=0) {
                    p.speedX=0;
                }
            }
        });

        t=new Timer(16, this);
        t.start();
    }

    private void createFireballs() {
        for (int i=0;i<maxFireballs;i++) {
            int x=(int)(Math.random()*(getWidth()-30));
            fireballs.add(new Fire(x, -30));
        }
    }

    public void actionPerformed(ActionEvent e) {
        p.update(getWidth(),getHeight());

        //Create new fireballs
        long currentTime=System.currentTimeMillis();
        if (currentTime-lastFireTime>fireCooldown) {
            createFireballs();
            lastFireTime=currentTime;
        }

        //Update and remove inactive fireballs
        for (int i=fireballs.size()-1;i>=0;i--) {
            Fire fire=fireballs.get(i);
            fire.update(getHeight());
            if (!fire.active) {
                fireballs.remove(i);
                fireCount++;
            }
        }
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(p.playerPic,p.x,p.y,p.width,p.height, this);
        for (Fire fire:fireballs) {
            g.drawImage(fire.firePic,fire.x,fire.y,fire.width,fire.height, this);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial",Font.BOLD, 20));
        g.drawString("Fire Count: " + fireCount, 20, 30);
    }
}
