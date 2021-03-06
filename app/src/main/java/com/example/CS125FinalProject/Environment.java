package com.example.CS125FinalProject;

/** This is an abstract class used for game "Environments". This is non-character things
 *  that the player and other characters can interact with in some way. */
//TODO: Make Portal class a child of Environment and change Room class accordingly
public abstract class Environment {
    /** Constant to represent Platform as type. */
    public static final int PLATFORM = 0;
    /** Constant to represent Stairs as type. */
    public static final int STAIRS = 1;
    /** Constant to represent Movement as type. */
    public static final int MOVEMENT = 2;
    /** Constant to represent Portal as type. */
    public static final int PORTAL = 3;

    /** The hitbox of the Environment object */
    private Rectangle hitbox;
    /** The type of environment object */
    private int type;

    /**Constructor for Environment
     * @param setHitbox sets the hitbox
     * @param setType sets the "type" value*/
    Environment(Rectangle setHitbox, int setType) {
        hitbox = setHitbox;
        type = setType;
    }

    /**Every child class must have this method. Runs the logic and display for that Environment
     * @param c character that the Environment is checking for collision and logic */
    abstract void run(Character c);

    /** @return hitbox*/
    public Rectangle getHitbox() {
        return hitbox;
    }
}
