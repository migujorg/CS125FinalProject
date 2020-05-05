package com.example.CS125FinalProject;

//import androidx.constraintlayout.solver.widgets.Rectangle;

import processing.core.PApplet;

/**This class manages travelling between rooms. Portals will be invisible in the final implementation. */
public class Portal extends Environment {
    /** Destination of the portal. Int is the index of a level in the "levels" array (this array doesn't exist yet) */
    private int destination;
     /** Do you have to press a key to use this portal? Or is it automatic upon contact */
    private boolean requiresInteract;

    /** Constructor that uses X,Y,Width,and Height, rather than a Rectangle instance.
     * @param x sets x
     * @param y sets y
     * @param width sets width
     * @param height sets height
     * @param setDestination sets destination
     * @param setRequiresInteract sets requiresInteract
     */
    public Portal(double x, double y, double width, double height, int setDestination, boolean setRequiresInteract) {
        super(new Rectangle (x,y,width,height), Environment.PORTAL);
        destination = setDestination;
        requiresInteract = setRequiresInteract;
    }

    /** Constructor that uses a Rectangle instance to instantiate the portal
     * @param setHitbox sets hitbox
     * @param setDestination sets destination
     * @param setRequiresInteract sets requiresInteract
     */
    public Portal(Rectangle setHitbox, int setDestination, boolean setRequiresInteract) {
        super(setHitbox, Environment.PORTAL);
        destination = setDestination;
        requiresInteract = setRequiresInteract;
    }

    /** Renders a translucent rectangle on the screen at the position of the portal. For debugging */
    void showPortal() {
        Main.sketch.fill(30,30,200,100);
        Main.sketch.stroke(0,0,0);
        Main.sketch.rect((float) super.getHitbox().x, (float) super.getHitbox().y, (float) super.getHitbox().width, (float) super.getHitbox().height);
    }

    /** Runs the portal logic. Requires a Character parameter to know which character to watch for collision. */
    void run(Character c) {
        if (!c.isAdvancedHitbox() && c.isPlayer()) {
            if (!requiresInteract) {
                if (super.getHitbox().intersects(c.getSimpleHitbox())) {
                    ((Sketch) Main.sketch).getRoomManager().setCurrentRoom(destination);
                }
            } else {
                if (!c.isGrounded() && super.getHitbox().intersects(c.getSimpleHitbox())) {
                    ((Sketch) Main.sketch).getRoomManager().setCurrentRoom(destination);
                    c.setIsGrounded(true);
                }
            }
        }
        if (((Sketch) Main.sketch).isDebugMode()) {
            //showPortal();
        }
    }

    /**@return x coordinate of portal.*/
    double getX() {
        return super.getHitbox().x;
    }
    /**@return y coordinate of portal.*/
    double getY() {
        return super.getHitbox().y;
    }
    /**@return width of portal.*/
    double getWidth() {
        return super.getHitbox().width;
    }
    /**@return height of portal.*/
    double getHeight() {
        return super.getHitbox().height;
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
    public Rectangle getHitBox() {
        return super.getHitbox();
    }
}
