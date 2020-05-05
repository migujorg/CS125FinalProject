package com.example.CS125FinalProject;

//import androidx.constraintlayout.solver.widgets.Rectangle;

import java.lang.reflect.Array;
import java.util.ArrayList;

import processing.core.PImage;

/**This class is used to manage characters in this game. Keeps track of and modifies position, health, and other values .*/
public class Character {
    /**Rectangle used for position, width, and height of simple hitbox. */
    private Rectangle simpleHitbox = new Rectangle();
    /** The character's movement speed. */
    private double moveSpeed = 10;
    /**The character's current velocity in the X direction. */
    private double xVelocity;
    /**The character's current velocity in the Y direction. */
    private double yVelocity;
    /**Max health of the character. */
    private double maxHealth;
    /**Current health of the character. */
    private double currentHealth;
    /**Percent of damage mitigated. */
    private double armor;
    /**For more advanced characters, we will use an array of rectangles for the hitbox. */
    private ArrayList<Rectangle> hitBox = new ArrayList<>();
    /**Boolean that indicates if character has a simple (one rectangle) or advanced (array of rectangles) hitbox. */
    private boolean advancedHitbox;
    /**Boolean that indicates if the character should be controllable. */
    private boolean isPlayer;
    /** The last direction that the character was moving in. Needed to determine which sprite to display after coming to a stop. */
    private boolean wasGoingLeft = false;
    /** The sequence of PImages to be used for the right facing animation. */
    private ArrayList<PImage> animationRight = new ArrayList<>();
    /** The sequence of PImages to be used for the left facing animation. */
    private ArrayList<PImage> animationLeft = new ArrayList<>();
    /**Whether of not character is on a surface. Used in friction calculation. */
    private boolean isGrounded;

    /** Default constructor for if nothing is specified. Unlikely to be used much outside of debugging, usually every field will have a value. */
    Character() {
        simpleHitbox.setBounds(500, 0, 100, 100);
        xVelocity = 0;
        yVelocity = 0;
        maxHealth = 100;
        currentHealth = maxHealth;
        armor = 0.0;
        advancedHitbox = false;
        isGrounded = false;
    }

    /** Default constructor if only isPlayer is specified */
    Character(boolean setIsPlayer) {
        simpleHitbox.setBounds(500, 0, 100, 100);
        xVelocity = 0;
        yVelocity = 0;
        maxHealth = 100;
        currentHealth = maxHealth;
        armor = 0.0;
        advancedHitbox = false;
        isGrounded = false;
        isPlayer = setIsPlayer;
    }

    /** Constructor for "advanced" characters. Meaning that they have an ArrayList<Rectangle> hitbox.
     * @param setX sets X value (character's spawn point)
     * @param setY sets Y value (character's spawn point)
     * @param setMaxHealth sets maxHealth
     * @param setArmor sets armor
     * @param setHitBox sets hitBox
     */
    Character(double setX, double setY, double setMaxHealth, double setArmor, ArrayList<Rectangle> setHitBox) {
        simpleHitbox.setBounds(setX,  setY, 0,0);
        maxHealth = setMaxHealth;
        currentHealth = maxHealth;
        armor = setArmor;
        hitBox = setHitBox;
        advancedHitbox = true;
    }

    /** Constructor for "simple" characters. Meaning that they have a single rectangle used for the hitbox.
     * @param setX sets initial x value (character's spawn point)
     * @param setY sets initial y value (character's spawn point);
     * @param setWidth sets width (used for hitbox)
     * @param setHeight sets height (used for hitbox)
     * @param setMaxHealth sets maxHealth
     * @param setArmor sets armor
     */
    public Character(double setX, double setY, double setWidth, double setHeight, double setMaxHealth, double setArmor) {
        simpleHitbox.setBounds(setX, setY, setWidth, setHeight);
        maxHealth = setMaxHealth;
        currentHealth = maxHealth;
        armor = setArmor;
        advancedHitbox = false;
    }

    /** Constructor for player character. Simple hitbox for now.
     * @param setX sets initial x value (character's spawn point)
     * @param setY sets initial y value (character's spawn point);
     * @param setWidth sets width (used for hitbox)
     * @param setHeight sets height (used for hitbox)
     * @param setMaxHealth sets maxHealth
     * @param setArmor sets armor
     * @param setIsPlayer sets if it is the player. (Basically always true b/c why else would you use this constructor)
     */
    public Character(double setX, double setY, double setWidth, double setHeight, double setMaxHealth, double setArmor, boolean setIsPlayer) {
        simpleHitbox.setBounds(setX, setY, setWidth, setHeight);
        maxHealth = setMaxHealth;
        currentHealth = maxHealth;
        armor = setArmor;
        advancedHitbox = false;
        isPlayer = setIsPlayer;
        loadAnimation();
    }

    /**Call this in "draw()" to draw this character and run it's logic*/
    void run() {
        physicsUpdate();
        if (((Sketch) Main.sketch).isDebugMode()) {
            displayHitbox();
        }
        showAnimation(8);
        if (isPlayer) {
            runPlayerControl();
        }
        isGrounded = false;
    }

    /** Consolidates player control logic into one method. */
    private void runPlayerControl() {
        if (((Sketch) Main.sketch).isRightPressed()) {
            xVelocity = moveSpeed;
        } else if (((Sketch) Main.sketch).isLeftPressed()) {
            xVelocity = -1 * moveSpeed;
        }
        if (((Sketch) Main.sketch).isUpPressed() && isGrounded) {
            yVelocity = -20;
        }
    }

    /**Updates x, y, and their velocities*/
    private void physicsUpdate() {
        simpleHitbox.y += yVelocity;
        yVelocity += Sketch.GLOBAL_GRAVITY;
        simpleHitbox.x += xVelocity;

        if (xVelocity > 0 && isGrounded && !((Sketch) Main.sketch).isRightPressed()) {
            xVelocity -= Sketch.FRICTION_COEFFICIENT;
            wasGoingLeft = false;
        } else if (xVelocity < 0 && isGrounded && !((Sketch) Main.sketch).isLeftPressed()) {
            xVelocity += Sketch.FRICTION_COEFFICIENT;
            wasGoingLeft = true;
        }
    }

    /**Displays the hitbox. Used for debugging. Later will display a sprite instead. */
    private void displayHitbox() {
        Main.sketch.stroke(255,255,255);
        Main.sketch.fill(0,0,0,0);
        Main.sketch.rect((float) simpleHitbox.x,(float) simpleHitbox.y , (float) simpleHitbox.width, (float) simpleHitbox.height);
    }

    /** Renders the sequence of PImages in the correct position to play the animation for the character.
     * @param inverseSpeed used to determine how fast to play the animation. Range 1 to 60. Lower numbers
     *                     are faster. (frameRate == Main.sketch.frameRate / inverseSpeed times per second). */
    private void showAnimation(double inverseSpeed) {
        PImage frame;
        if (xVelocity > 0 || (xVelocity == 0 && !wasGoingLeft)) {
            frame = animationRight.get((int) ((Main.sketch.frameCount / inverseSpeed) % animationRight.size()));
        } else {
            frame = animationLeft.get((int) ((Main.sketch.frameCount / inverseSpeed) % animationLeft.size()));
        }
        Main.sketch.image(frame, (float) simpleHitbox.x - 150, (float) simpleHitbox.y - 90, (float) frame.width, (float) frame.height);
    }


    /** @return returns advancedHitbox. */
    boolean isAdvancedHitbox() {
        return advancedHitbox;
    }
    /** @return returns simpleHitbox Rectangle object. */
    public Rectangle getSimpleHitbox() {
        return simpleHitbox;
    }
    /** @return returns height using simpleHitbox. */
    double getHeight() {
        return simpleHitbox.height;
    }

    /** @return returns width using simpleHitbox. */
    double getWidth() {
        return simpleHitbox.width;
    }

    /** @param setX Sets x coordinate of Character. */
    void setX(double setX) {
        simpleHitbox.x = setX;
    }

    /** @param setXVelocity sets xVelocity of Character. */
    void setXVelocity(double setXVelocity) {
        xVelocity = setXVelocity;
    }

    /** @param setY Sets y coordinate of Character. */
    void setY(double setY) {
        simpleHitbox.y = setY;
    }

    /** @return yVelocity of Character. */
    double getYVelocity() {
        return yVelocity;
    }

    /**@param setYVelocity sets Y velocity of Character. */
    void setYVelocity(double setYVelocity) {
        yVelocity = setYVelocity;
    }

    /** @return gets X velocity of Character. */
    public double getXVelocity() {
        return xVelocity;
    }

    /**@param setIsGrounded sets isGrounded variable of Character. */
    void setIsGrounded(boolean setIsGrounded) {
        isGrounded = setIsGrounded;
    }

    /**@return returns isGrounded variable. */
    boolean isGrounded() {
        return isGrounded;
    }

    /**@return returns isPlayer variable.*/
    boolean isPlayer() {
        return isPlayer;
    }

    /** Private helper method used to store previously loaded PImage Lists into animationRight and
     *  animationLeft instance variables. */
    private void loadAnimation() {
        animationRight = ((Sketch) Main.sketch).getAnimations().get(3);
        animationLeft = ((Sketch) Main.sketch).getAnimations().get(4);
    }
}
