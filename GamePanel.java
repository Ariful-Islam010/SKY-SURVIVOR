import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener {
    Timer t;
    Player p;
    SkySurvivor game;
    ArrayList<Fire> fireballs=new ArrayList<>();
    Star currentStar=null;
    long lastFireTime=0;
    int maxFireballs=10;
    int fireCooldown=3000; // milliseconds
    int fireCount=0;
    int score=0;

    private Image bgImage;
    private int bgY=0;
    private boolean gameOver=false;

    public GamePanel(SkySurvivor game) {
        this.game=game;
        this.setFocusable(true);
        p = new Player();

        try {
            p.playerPic=new ImageIcon("C:\\Users\\hp\\OneDrive\\Desktop\\Game Project\\src\\image\\player.png").getImage();
            bgImage=new ImageIcon("C:\\Users\\hp\\OneDrive\\Desktop\\Game Project\\src\\image\\bg.jpg").getImage();
        } catch(Exception e) {
            System.out.println("Error loading image: " + e);
        }

        // Create initial star
        createNewStar();

        // Key listener with both key code and character support
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                char keyChar = Character.toUpperCase(e.getKeyChar());

                if (gameOver && (key == KeyEvent.VK_ENTER || keyChar == 'R')) {
                    resetGame();
                    return;
                }

                if (key==KeyEvent.VK_UP || keyChar=='U')
                    p.speedY=-5;
                if (key==KeyEvent.VK_DOWN || keyChar=='D')
                    p.speedY = 5;
                if (key == KeyEvent.VK_LEFT || keyChar =='L')
                    p.speedX=-5;
                if (key==KeyEvent.VK_RIGHT || keyChar=='R')
                    p.speedX = 5;
                if ((key==KeyEvent.VK_SPACE || keyChar=='J') && !p.jumping) {
                    p.jumping=true;
                    p.jumpHeight=0;
                }
            }

            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                char keyChar = Character.toUpperCase(e.getKeyChar());

                if ((key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN ||
                        keyChar == 'U' || keyChar == 'D') && p.speedY != 0) {
                    p.speedY = 0;
                }
                if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT ||
                        keyChar == 'L' || keyChar == 'R') && p.speedX != 0) {
                    p.speedX = 0;
                }
            }
        });

        t = new Timer(16, this);
        t.start();
    }

    private void resetGame() {
        gameOver = false;
        score = 0;
        fireCount = 0;
        fireballs.clear();
        currentStar = null;
        createNewStar();
        p.x = 300;
        p.y = 300;
        p.speedX = 0;
        p.speedY = 0;
        p.jumping = false;
    }

    private void createFireballs() {
        int num = (int)(Math.random() * 5);
        for (int i = 0; i < num; i++) {
            int x = (int)(Math.random() * (getWidth() - 30));
            fireballs.add(new Fire(x, -30));
        }
    }

    private void createNewStar() {
        // Create a star at a random position within the game area
        int x=(int)(Math.random()*(getWidth()-30));
        int y=(int)(Math.random() * (getHeight() - 100)) ;
        currentStar = new Star(x, y);
    }

    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            bgY += 2;
            if (bgY >= getHeight()) {
                bgY = 0;
            }

            p.update(getWidth(), getHeight());

            // Create new fireballs
            long currentTime=System.currentTimeMillis();
            if (currentTime-lastFireTime>fireCooldown) {
                createFireballs();
                lastFireTime=currentTime;
            }

            // Check star expired
            if (currentStar!=null && currentStar.isExpired()) {
                createNewStar();
            }

            // Fire update and collision detection
            for (int i=fireballs.size()-1;i>=0;i--) {
                Fire fire = fireballs.get(i);
                fire.update(getHeight());

                // Simple collision check
                if (fire.x<p.x+p.width &&
                        fire.x+fire.width>p.x &&
                        fire.y<p.y+p.height &&
                        fire.y+fire.height>p.y) {
                    gameOver=true;
                }

                if (!fire.active) {
                    fireballs.remove(i);
                    fireCount++;
                }
            }

            // Star collision detection
            if (currentStar != null &&
                    currentStar.x<p.x+p.width &&
                    currentStar.x+currentStar.width>p.x &&
                    currentStar.y<p.y+p.height &&
                    currentStar.y+currentStar.height>p.y) {
                score+=10;
                createNewStar();
            }
        }
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw two backgrounds for scrolling effect
        g.drawImage(bgImage,0,bgY-getHeight(),getWidth(),getHeight(),this);
        g.drawImage(bgImage, 0, bgY, getWidth(), getHeight(), this);

        // Draw player
        g.drawImage(p.playerPic, p.x, p.y, p.width, p.height, this);


        for (Fire fire : fireballs) {
            g.drawImage(fire.firePic, fire.x, fire.y, fire.width, fire.height, this);
        }
        if (currentStar != null) {
            g.drawImage(currentStar.starPic, currentStar.x, currentStar.y, currentStar.width, currentStar.height, this);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Fire Count: " + fireCount, 20, 30);
        g.drawString("Score: " + score, 20, 60);

    }
}
