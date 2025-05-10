import java.awt.*;
import javax.swing.ImageIcon;

public class Star {
    int x,y;
    int width=30;
    int height=30;
    boolean active=true;
    Image starPic;
    long creationTime;
    int lifespan=5000;

    public Star(int startX, int startY) {
        this.x=startX;
        this.y=startY;
        this.creationTime=System.currentTimeMillis();
        try {
            starPic=new ImageIcon("C:\\Users\\hp\\OneDrive\\Desktop\\Game Project\\src\\image\\star.png").getImage();
        } catch(Exception e) {
            System.out.println("Error loading star image: " + e);
        }
    }

    public boolean isExpired() {
        return System.currentTimeMillis()-creationTime>lifespan;
    }
}
