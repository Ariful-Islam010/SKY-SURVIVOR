import java.awt.*;
import javax.swing.ImageIcon;

public class Player {
    int x = 300;
    int y = 500; // Fixed ground position
    int width = 65;
    int height = 65;
    int speedX = 0;
    int speedY = 0;
    Image playerPic;

    // Immunity system
    boolean hasImmunity = false;
    long immunityStartTime = 0;
    int immunityDuration = 5000; // 5 seconds

    // Simple movement constants
    final int MAX_SPEED = 5;
    final int GROUND_Y = 500;
    final int MIN_Y = 400; // Can move up slightly from ground

    public Player() {
        y = GROUND_Y;
    }

    public void update(int screenWidth, int screenHeight) {
        // Handle immunity timer
        if (hasImmunity && System.currentTimeMillis() - immunityStartTime > immunityDuration) {
            hasImmunity = false;
        }

        // Simple direct movement
        x += speedX;
        y += speedY;

        // Apply boundaries
        if (x < 0) {
            x = 0;
        }
        if (x > screenWidth - width) {
            x = screenWidth - width;
        }

        // Vertical constraints - limited movement range
        if (y < MIN_Y) {
            y = MIN_Y;
        }
        if (y > screenHeight - height) {
            y = screenHeight - height;
        }
    }

    public void activateImmunity() {
        hasImmunity = true;
        immunityStartTime = System.currentTimeMillis();
    }

    public boolean isImmune() {
        return hasImmunity;
    }
}
