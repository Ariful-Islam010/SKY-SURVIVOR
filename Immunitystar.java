import java.awt.*;
import javax.swing.ImageIcon;

public class ImmunityStar {
    int x,y;
    int width=30;
    int height=30;
    boolean active=true;
    Image immunityStarPic;
    long creationTime;
    int lifespan=8000;
    private float glowAlpha=0.5f;
    private boolean glowIncreasing=true;

    public ImmunityStar(int startX,int startY) {
        this.x=startX;
        this.y=startY;
        this.creationTime=System.currentTimeMillis();
        try {
            immunityStarPic=new ImageIcon("C:\\Users\\hp\\OneDrive\\Desktop\\Sky Survivor\\src\\image\\star.png").getImage();
        } catch(Exception e) {
            System.out.println("Error loading immunity star image: " + e);
        }
    }

    public boolean isExpired() {
        return System.currentTimeMillis()-creationTime>lifespan;
    }

    public void updateGlow() {
        if (glowIncreasing) {
            glowAlpha+=0.02f;
            if (glowAlpha>=1.0f) {
                glowAlpha=1.0f;
                glowIncreasing=false;
            }
        } else {
            glowAlpha-=0.02f;
            if (glowAlpha<=0.3f) {
                glowAlpha=0.3f;
                glowIncreasing=true;
            }
        }
    }

    public float getGlowAlpha() {
        return glowAlpha;
    }
}
