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
    private boolean wasGoingLeft = false;

    private PImage sprite;
    private ArrayList<PImage> animationRight = new ArrayList<>();
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
        displayHitbox();
        showCharacter();
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

    private void showCharacter() {
        showAnimation(8);
    }

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

    boolean isGrounded() {
        return isGrounded;
    }

    /**@return isPlayer.*/
    boolean isPlayer() {
        return isPlayer;
    }

    private void loadAnimation() {
        PImage frameR1 = Main.sketch.loadImage("ghostR1.png");
        PImage frameR2 = Main.sketch.loadImage("ghostR2.png");
        PImage frameR3 = Main.sketch.loadImage("ghostR3.png");
        PImage frameR4 = Main.sketch.loadImage("ghostR4.png");
        PImage frameR5 = Main.sketch.loadImage("ghostR5.png");
        PImage frameR6 = Main.sketch.loadImage("ghostR6.png");
        PImage frameR7 = Main.sketch.loadImage("ghostR7.png");
        PImage frameR8 = Main.sketch.loadImage("ghostR8.png");
        PImage frameR9 = Main.sketch.loadImage("ghostR9.png");
        animationRight.add(frameR1);
        animationRight.add(frameR2);
        animationRight.add(frameR3);
        animationRight.add(frameR4);
        animationRight.add(frameR5);
        animationRight.add(frameR6);
        animationRight.add(frameR7);
        animationRight.add(frameR8);
        animationRight.add(frameR9);

        PImage frameL1 = Main.sketch.loadImage("ghostL1.png");
        PImage frameL2 = Main.sketch.loadImage("ghostL2.png");
        PImage frameL3 = Main.sketch.loadImage("ghostL3.png");
        PImage frameL4 = Main.sketch.loadImage("ghostL4.png");
        PImage frameL5 = Main.sketch.loadImage("ghostL5.png");
        PImage frameL6 = Main.sketch.loadImage("ghostL6.png");
        PImage frameL7 = Main.sketch.loadImage("ghostL7.png");
        PImage frameL8 = Main.sketch.loadImage("ghostL8.png");
        PImage frameL9 = Main.sketch.loadImage("ghostL9.png");
        animationLeft.add(frameL1);
        animationLeft.add(frameL2);
        animationLeft.add(frameL3);
        animationLeft.add(frameL4);
        animationLeft.add(frameL5);
        animationLeft.add(frameL6);
        animationLeft.add(frameL7);
        animationLeft.add(frameL8);
        animationLeft.add(frameL9);
    }


}
