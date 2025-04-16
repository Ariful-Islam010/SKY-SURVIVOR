import java.awt.*;

public class Player {
    int x=300;
    int y=300;
    int width=80;
    int height=80;
    int speedX=0;
    int speedY=0;
    boolean jumping=false;
    int jumpHeight=0;
    int jumpSpeed=10;
    int maxJump=100;
    Image playerPic;
    int groundY=500;

    void update(int screenWidth, int screenHeight) {
        x=x+speedX;
        y=y+speedY;

        if(x<0) {
            x=0;
        }
        if (x>screenWidth-width) {
            x=screenWidth-width;
        }
        if (y<0) {
            y=0;
        }
        if(y>screenHeight-height) {
            y=screenHeight-height;
        }
        if(jumping==true) {
            if(jumpHeight<maxJump) {
                y=y-jumpSpeed;
                jumpHeight=jumpHeight+jumpSpeed;
            }else{
                y=y+jumpSpeed;
                if(y>=groundY) {
                    y=groundY;
                    jumping=false;
                }
      }
        }
    }
}
