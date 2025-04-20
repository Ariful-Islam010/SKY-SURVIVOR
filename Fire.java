import java.awt.*;
import javax.swing.ImageIcon;

public class Fire {
    int x,y;
    int width=30;
    int height=30;
    int speed=5;
    Image firePic;
    boolean active=true;

    public Fire(int startX,int startY) {
        this.x=startX;
        this.y=startY;

        try {
            firePic=new ImageIcon("C:\\Users\\hp\\OneDrive\\Desktop\\git clone\\Game Project\\src\\image\\fireball.png").getImage();
        } catch(Exception e) {
            System.out.println("Error loading fire image: " + e);
        }
    }

    public void update(int screenHeight) {
        y+=speed;
        if (y>screenHeight)
            active=false;
    }
}
