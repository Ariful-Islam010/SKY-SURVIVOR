import java.awt.*;

public class Player {
    int x=300,y=300;
    int width=70;
    int height=70;
    int speedX=0, speedY=0;
    boolean jumping=false;
    int gravity=1; 
    int jumpVelocity=-15;  
    Image playerPic;
    int groundY=500; 

    void update(int screenWidth,int screenHeight) {
        x=x+speedX;
        
        if (jumping) {
            speedY+=gravity;
            y+=speedY;
            if (y>=groundY) {
                y=groundY;
                jumping=false;
                speedY=0;
            }
        } else {
            y+=speedY;
        }

        if (x<0) {
            x=0;
        }
        if (x>screenWidth-width) {
            x=screenWidth-width;
        }
        if (y<0) {
            y=0;
            if (jumping) {
                speedY=0; 
            }
        }
        if (y>screenHeight-height) {
            y=screenHeight-height;
            if (jumping) {
                jumping=false;
                speedY=0;
            }
        }
    }

    void jump() {
        if (!jumping && y>=groundY-5) {  
            jumping=true;
            speedY=jumpVelocity;  
        }
    }
}
