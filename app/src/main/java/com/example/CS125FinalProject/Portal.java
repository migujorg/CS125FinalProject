package com.example.CS125FinalProject;

//import androidx.constraintlayout.solver.widgets.Rectangle;

import processing.core.PApplet;

/**This class manages travelling between rooms. Portals will be invisible in the final implementation. */
public class Portal {
    /** Destination of the portal. Int is the index of a level in the "levels" array (this array doesn't exist yet) */
    private int destination;
     /** Do you have to press a key to use this portal? Or is it automatic upon contact */
    private boolean requiresInteract;
    /** Rectangle used for hitbox */
    private Rectangle hitbox = new Rectangle();

    /** Constructor that uses X,Y,Width,and Height, rather than a Rectangle instance.
     * @param x sets x
     * @param y sets y
     * @param width sets width
     * @param height sets height
     * @param setDestination sets destination
     * @param setRequiresInteract sets requiresInteract
     */
    public Portal(int x, int y, int width, int height, int setDestination, boolean setRequiresInteract) {
        hitbox.setBounds(x, y, width, height);
        destination = setDestination;
        requiresInteract = setRequiresInteract;
    }

    /** Constructor that uses a Rectangle instance to instantiate the portal
     * @param setHitbox sets hitbox
     * @param setDestination sets destination
     * @param setRequiresInteract sets requiresInteract
     */
    public Portal(Rectangle setHitbox, int setDestination, boolean setRequiresInteract) {
        hitbox = setHitbox;
        destination = setDestination;
        requiresInteract = setRequiresInteract;
    }

    /** Renders a translucent rectangle on the screen at the position of the portal. For debugging */
    void showPortal() {
        Main.sketch.fill(30,30,200,100);
        Main.sketch.stroke(0,0,0);
        Main.sketch.rect((float) hitbox.x, (float) hitbox.y, (float) hitbox.width, (float) hitbox.height);
    }
    /** Runs the portal logic. Requires a Character parameter to know which character to watch for collision. */
    void runPortal(Character player) {
        if (!player.isAdvancedHitbox()) {
            if (!requiresInteract) {
                if (hitbox.intersects(player.getSimpleHitbox())) {
                    //TODO: set current level to destination level
                }
            }
        }
    }

    /**@return x coordinate of portal.*/
    double getX() {
        return hitbox.x;
    }
    /**@return y coordinate of portal.*/
    double getY() {
        return hitbox.y;
    }
    /**@return width of portal.*/
    double getWidth() {
        return hitbox.width;
    }
    /**@return height of portal.*/
    double getHeight() {
        return hitbox.height;
    }
    /**@return destination of portal.*/
    int getDestination() {
        return destination;
    }
    /**@return requiresInteract property of portal.*/
    boolean getRequiresInteract() {
        return requiresInteract;
    }
    /**@return hitbox Rectangle directly. */
    Rectangle getHitBox() {
        return hitbox;
    }
}
