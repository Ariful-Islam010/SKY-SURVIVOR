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
    ImmunityStar currentImmunityStar=null;
    long lastFireTime=0;
    long lastImmunityStarTime=0;
    int maxFireballs=10;
    int fireCooldown=3000;
    int immunityStarCooldown=15000;
    int fireCount=0;
    int score=0;
    private SoundManager soundManager;

    private Image bgImage;
    private int bgY=0;
    private boolean gameOver=false;

    public GamePanel(SkySurvivor game) {
        this.game=game;
        this.setFocusable(true);
        setLayout(null);
        soundManager=SoundManager.getInstance();

        p=new Player();

        try {
            p.playerPic=new ImageIcon("C:\\Users\\hp\\OneDrive\\Desktop\\Game Project\\src\\image\\player.png").getImage();
            bgImage=new ImageIcon("C:\\Users\\hp\\OneDrive\\Desktop\\Game Project\\src\\image\\bg.jpg").getImage();
        } catch(Exception e) {
            System.out.println("Error loading image: " + e);
        }

        createNewStar();

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key=e.getKeyCode();
                char keyChar=Character.toUpperCase(e.getKeyChar());

                // Keep ESC key functionality
                if (key==KeyEvent.VK_ESCAPE) {
                    t.stop();
                    game.showMainMenu();
                    game.revalidate();
                    return;
                }

                if (gameOver) {
                    resetGame();
                    return;
                }

                // Regular movement controls (work when not jumping)
                if (key==KeyEvent.VK_UP || keyChar=='U')
                    p.speedY=-5;
                if (key==KeyEvent.VK_DOWN || keyChar=='D')
                    p.speedY = 5;
                if (key==KeyEvent.VK_LEFT || keyChar=='L')
                    p.speedX=-5;
                if (key==KeyEvent.VK_RIGHT || keyChar=='R')
                    p.speedX=5;

                // Jumping with spacebar - Angry Birds style
                if (key==KeyEvent.VK_SPACE || keyChar=='J') {
                    p.jump();
                }
            }

            public void keyReleased(KeyEvent e) {
                int key=e.getKeyCode();
                char keyChar=Character.toUpperCase(e.getKeyChar());

                if ((key==KeyEvent.VK_UP || key==KeyEvent.VK_DOWN ||
                        keyChar=='U' || keyChar=='D') && p.speedY != 0) {
                    p.speedY =0;
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
        currentImmunityStar = null;
        createNewStar();
        p.x = 300;
        p.y = p.groundY;  // Start on ground
        p.speedX = 0;
        p.speedY = 0;
        p.jumping = false;
        p.hasImmunity = false;
    }

    private void createFireballs() {
        int num = 1 + (int)(Math.random() * 5);
        for (int i = 0; i < num; i++) {
            int x = (int)(Math.random() * (getWidth() - 30));
            // Random speed (3-7)
            int speed = 3 + (int)(Math.random() * 5);
            Fire fire = new Fire(x, -30);
            fire.speed = speed;
            fireballs.add(fire);
        }
    }

    private void createNewStar() {
        int x = (int)(Math.random() * (getWidth() - 30));
        int y = (int)(Math.random() * (getHeight() - 100));
        currentStar = new Star(x, y);
    }

    private void createNewImmunityStar() {
        int x = (int)(Math.random() * (getWidth() - 40));
        int y = (int)(Math.random() * (getHeight() - 100));
        currentImmunityStar = new ImmunityStar(x, y);
    }

    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            bgY += 2;
            if (bgY >= getHeight()) {
                bgY = 0;
            }

            p.update(getWidth(), getHeight());

            long currentTime = System.currentTimeMillis();
            if (currentTime-lastFireTime>fireCooldown) {
                createFireballs();
                lastFireTime = currentTime;
            }

            // Immunity star creation - less frequent (15 seconds)
            if (currentImmunityStar == null && currentTime - lastImmunityStarTime > immunityStarCooldown) {
                createNewImmunityStar();
                lastImmunityStarTime = currentTime;
            }

            if (currentStar!=null && currentStar.isExpired()) {
                createNewStar();
            }

            // Immunity star update
            if (currentImmunityStar != null) {
                currentImmunityStar.updateGlow();
                if (currentImmunityStar.isExpired()) {
                    currentImmunityStar = null;
                }
            }

            for (int i = fireballs.size()-1;i>=0;i--) {
                Fire fire = fireballs.get(i);
                fire.update(getHeight());

                //collision - only when not immune
                if (!p.isImmune()) {
                    int collisionMargin = 10;
                    if (fire.x < p.x + p.width - collisionMargin &&
                            fire.x + fire.width - collisionMargin > p.x + collisionMargin &&
                            fire.y < p.y + p.height - collisionMargin &&
                            fire.y+fire.height-collisionMargin>p.y+collisionMargin) {
                        gameOver=true;
                    }
                }

                if (!fire.active) {
                    fireballs.remove(i);
                    fireCount++;
                }
            }

            // Star collision detection with sound effect
            if (currentStar != null) {
                int collisionMargin = 10;
                if (currentStar.x < p.x + p.width - collisionMargin &&
                        currentStar.x + currentStar.width - collisionMargin > p.x + collisionMargin &&
                        currentStar.y < p.y + p.height - collisionMargin &&
                        currentStar.y + currentStar.height - collisionMargin > p.y + collisionMargin) {
                    score += 10;
                    soundManager.playSound("starCollect"); // Play star collection sound
                    createNewStar();
                }
            }

            // Immunity star collision with sound effect
            if (currentImmunityStar != null) {
                int collisionMargin = 10;
                if (currentImmunityStar.x < p.x + p.width - collisionMargin &&
                        currentImmunityStar.x + currentImmunityStar.width - collisionMargin > p.x + collisionMargin &&
                        currentImmunityStar.y < p.y + p.height - collisionMargin &&
                        currentImmunityStar.y + currentImmunityStar.height - collisionMargin > p.y + collisionMargin) {
                    p.activateImmunity();
                    score += 50;
                    soundManager.playSound("immunityCollect"); // Play immunity collection sound
                    currentImmunityStar = null;
                }
            }
        }
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(bgImage, 0, bgY - getHeight(), getWidth(), getHeight(), this);
        g.drawImage(bgImage, 0, bgY, getWidth(), getHeight(), this);

        // Immunity glow effect
        if (p.isImmune()) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(new Color(0, 255, 255, 100));
            g2d.fillOval(p.x - 5, p.y - 5, p.width + 10, p.height + 10);
        }

        g.drawImage(p.playerPic, p.x, p.y, p.width, p.height, this);

        for (Fire fire : fireballs) {
            g.drawImage(fire.firePic, fire.x, fire.y, fire.width, fire.height, this);
        }
        if (currentStar != null) {
            g.drawImage(currentStar.starPic, currentStar.x, currentStar.y, currentStar.width, currentStar.height, this);
        }
        if (currentImmunityStar != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(new Color(255, 255, 0, (int)(currentImmunityStar.getGlowAlpha() * 255)));
            g2d.fillOval(currentImmunityStar.x - 8, currentImmunityStar.y - 8,
                    currentImmunityStar.width + 16, currentImmunityStar.height + 16);
            g.drawImage(currentImmunityStar.immunityStarPic, currentImmunityStar.x, currentImmunityStar.y,
                    currentImmunityStar.width, currentImmunityStar.height, this);
        }

        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Fire Count: "+fireCount, 20, 30);
        g.drawString("Score: "+score, 20, 60);
        if (gameOver) {
            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            String gameOverText = "Game Over";
            int textWidth = g.getFontMetrics().stringWidth(gameOverText);
            g.drawString(gameOverText, (getWidth()-textWidth)/2, getHeight()/2-40);

            g.setFont(new Font("Arial", Font.BOLD, 24));
            String scoreText="Score: " + score;
            textWidth=g.getFontMetrics().stringWidth(scoreText);
            g.drawString(scoreText, (getWidth()-textWidth)/2, getHeight() / 2);
        }
    }
}
