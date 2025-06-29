import java.awt.*;
import javax.swing.ImageIcon;

public class Player {
    int x=300;
    int y;
    int width = 65;
    int height = 65;
    int speedX = 0;
    int speedY = 0;
    int groundY = 500; // Ground level
    Image playerPic;

    // Immunity system
    boolean hasImmunity = false;
    long immunityStartTime = 0;
    int immunityDuration = 5000; // 5 seconds

    // Jumping system - Angry Birds style
    boolean jumping=false;
    double jumpStartX;
    double jumpCurrentX;
    double jumpVelocityX=3.0; // Horizontal movement speed during jump
    double jumpVelocityY=-12.0; // Initial upward velocity
    double jumpCurrentVelocityY;
    double gravity=0.6; // Gravity acceleration
    int jumpPhase=0; // 0: not jumping, 1: ascending, 2: descending

    public Player() {
        y = groundY;
    }

    public void update(int screenWidth, int screenHeight) {
        // Handle immunity timer
        if (hasImmunity && System.currentTimeMillis()-immunityStartTime>immunityDuration) {
            hasImmunity=false;
        }

        // Handle jumping physics
        if (jumping) {
            handleJumpPhysics();
        } else {
            // Regular movement when not jumping
            handleRegularMovement(screenWidth, screenHeight);
        }

        // Keep player within screen bounds
        if (x < 0) x = 0;
        if (x > screenWidth - width) x = screenWidth - width;
        if (y < 0) y = 0;
        if (y > screenHeight - height) y = screenHeight - height;

        // Ensure player stays on ground when not jumping
        if (!jumping && y > groundY) {
            y = groundY;
        }
    }

    private void handleJumpPhysics() {
        // Horizontal movement during jump
        jumpCurrentX += jumpVelocityX;
        x = (int) jumpCurrentX;

        // Vertical movement with gravity
        jumpCurrentVelocityY += gravity;
        y += (int) jumpCurrentVelocityY;

        // Check if landed back on ground
        if (y >= groundY) {
            y = groundY;
            jumping = false;
            jumpPhase = 0;
            speedX = 0;
            speedY = 0;
        }

        // Update jump phase based on velocity
        if (jumpCurrentVelocityY < 0) {
            jumpPhase = 1; // Ascending
        } else if (jumpCurrentVelocityY > 0) {
            jumpPhase = 2; // Descending
        }
    }

    private void handleRegularMovement(int screenWidth, int screenHeight) {
        // Only allow regular movement when not jumping
        x += speedX;
        y += speedY;
    }

    public void jump() {
        // Only allow jumping when on ground
        if (!jumping && y>=groundY) {
            jumping=true;
            jumpStartX=x;
            jumpCurrentX=x;
            jumpCurrentVelocityY=jumpVelocityY;
            jumpPhase=1;

            // Determine jump direction based on current movement
            if (speedX != 0) {
                jumpVelocityX = speedX > 0 ? Math.abs(jumpVelocityX) : -Math.abs(jumpVelocityX);
            } else {
                // Default forward jump if no horizontal movement
                jumpVelocityX = 3.0;
            }
        }
    }

    public void activateImmunity() {
        hasImmunity=true;
        immunityStartTime=System.currentTimeMillis();
    }

    public boolean isImmune() {
        return hasImmunity;
    }
}
