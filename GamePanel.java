import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class GamePanel extends JPanel implements ActionListener{
    Timer timer;
    Player player;
    boolean gameOver=false;
    SkySurvivor game;
    public GamePanel(SkySurvivor game) {
        this.game=game;
        this.setFocusable(true);
        player=new Player();
        try{
            player.playerPic=new ImageIcon("C:\\Users\\hp\\OneDrive\\Desktop\\git clone\\Game Project\\src\\image\\player.png").getImage();
        }catch(Exception e){
            System.out.println("Error loading image: "+e);
        }
        this.addKeyListener(new KeyListener(){
            public void keyPressed(KeyEvent e){
                int key=e.getKeyCode();
                char keyChar=Character.toUpperCase(e.getKeyChar());

                // Support both arrow keys
                if (key==KeyEvent.VK_UP || keyChar=='U') {
                    player.speedY = -5;
                }
                if (key == KeyEvent.VK_DOWN || keyChar == 'D') {
                    player.speedY = 5;
                }
                if (key==KeyEvent.VK_LEFT || keyChar=='L') {
                    player.speedX = -5;
                }
                if (key==KeyEvent.VK_RIGHT || keyChar=='R') {
                    player.speedX = 5;
                }
                if (key==KeyEvent.VK_SPACE || keyChar=='J') {
                    if (player.jumping==false) {
                        player.jumping=true;
                        player.jumpHeight=0;
                    }
                }
            }

            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                char keyChar=Character.toUpperCase(e.getKeyChar());

                // Support both arrow
                if ((key==KeyEvent.VK_UP || key==KeyEvent.VK_DOWN ||
                        keyChar=='U' || keyChar=='D') && player.speedY!= 0) {
                    player.speedY = 0;
                }
                if ((key==KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT ||
                        keyChar=='L' || keyChar=='R') && player.speedX != 0) {
                    player.speedX=0;
                }
            }

            public void keyTyped(KeyEvent e) {
            }
        });
        timer=new Timer(16, this);
        timer.start();
    }

    // Game loop
    public void actionPerformed(ActionEvent e){
        if (gameOver==false) {
            player.update(getWidth(), getHeight());
            repaint();
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(player.playerPic, player.x, player.y, player.width, player.height, this);
    }
}
