package com.example.CS125FinalProject;

/**This class manages travelling between rooms. Portals will be invisible in the final implementation. */
public class Portal extends Environment {
    /** Destination of the portal. Int is the index of a level in the "levels" array (this array doesn't exist yet) */
    private String destination;
     /** Do you have to press a key to use this portal? Or is it automatic upon contact */
    private boolean requiresInteract;

    /** Constructor that uses X,Y,Width,and Height, rather than a Rectangle instance.
     * @param x sets x
     * @param y sets y
     * @param width sets width
     * @param height sets height
     * @param setDestination sets destination
     * @param setRequiresInteract sets requiresInteract*/
    public Portal(double x, double y, double width, double height, String setDestination, boolean setRequiresInteract) {
        super(new Rectangle (x,y,width,height), Environment.PORTAL);
        destination = setDestination;
        requiresInteract = setRequiresInteract;
    }

    /** Constructor that uses a Rectangle instance to instantiate the portal
     * @param setHitbox sets hitbox
     * @param setDestination sets destination
     * @param setRequiresInteract sets requiresInteract. */
    public Portal(Rectangle setHitbox, String setDestination, boolean setRequiresInteract) {
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

    /** Runs the portal logic. Requires a Character parameter to know which character to watch for collision.
     *  If the portal has the destination -1 then it triggers the rest methods for the characters and TextBoxes.
     *  Currently, resetting the TextBoxes will make it so that on levels you have visited once already the options
     *  will show up instantly. Some levels have super longer stories, so this is useful to make replaying parts that you've
     *  done already faster. */
    void run(Character c) {
        if (!c.isAdvancedHitbox() && c.isPlayer()) {
            if (!requiresInteract) {
                if (super.getHitbox().intersects(c.getSimpleHitbox())) {
                    if (destination.equals("restart")) {
                        resetAllTextBoxes();
                        resetAllCharacters();
                        destination = "welcome";
                        goToDestination();
                        destination = "restart";
                    } else {
                        goToDestination();
                    }
                }
            } else {
                if (!c.isGrounded() && super.getHitbox().intersects(c.getSimpleHitbox())) {
                    if (destination.equals("restart")) {
                        resetAllTextBoxes();
                        resetAllCharacters();
                        destination = "welcome";
                        goToDestination();
                        destination = "restart";
                    } else {
                        goToDestination();
                        c.setIsGrounded(true);
                    }
                }
            }
        }
        if (((Sketch) Main.sketch).isDebugMode()) {
            showPortal();
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
    String getDestination() {
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

    /** Private helper method used to call the reset() function for every TextBox. */
    private void resetAllTextBoxes() {
        for (int i = 0; i < ((Sketch) Main.sketch).getRoomManager().getRooms().size(); i++) {
            for (int j = 0; j < ((Sketch) Main.sketch).getRoomManager().getRooms().get(i).getTextBoxes().size(); j++) {
                ((Sketch) Main.sketch).getRoomManager().getRooms().get(i).getTextBoxes().get(j).reset();
            }
        }
    }

    /** Private helper method used to call the reset() function for every Character. */
    private void resetAllCharacters() {
        for (int i = 0; i < ((Sketch) Main.sketch).getRoomManager().getRooms().size(); i++) {
            for (int j = 0; j < ((Sketch) Main.sketch).getRoomManager().getRooms().get(i).getCharacters().size(); j++) {
                ((Sketch) Main.sketch).getRoomManager().getRooms().get(i).getCharacters().get(j).reset();
            }
        }
    }

    /** Private helper method used to load the correct level when the player goes through a portal. */
    private void goToDestination() {
        for (int i = 0; i < ((Sketch) Main.sketch).getRoomManager().getRooms().size(); i++) {
            if (((Sketch) Main.sketch).getRoomManager().getRooms().get(i).getName().equals(destination)) {
                ((Sketch) Main.sketch).getRoomManager().setCurrentRoom(i);
                ((Sketch) Main.sketch).getRoomManager().getCurrentRoomInstance().resetCounter();
                return;
            }
        }
    }



}
