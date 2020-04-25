package com.example.mainactivity;

import androidx.constraintlayout.solver.widgets.Rectangle;

import java.util.ArrayList;
import processing.core.PApplet;

import static com.example.mainactivity.Sketch.leftPressed;
import static com.example.mainactivity.Sketch.rightPressed;

public class Character extends PApplet{
    /**Current X coordinate of the character. */
    private double x;
    /**Current Y coordinate of the character. */
    private double y;
    /**Width of the character's hitbox. */
    private double width;
    /**Height of the character's hitbox. */
    private double height;
    /**The character's current velocity in the X direction. */
    private double xVelocity;
    /**The character's current velocity in the Y direction. */
    private double yVelocity;
    /**Constant used to determine how fast the character comes to a stop when on a surface. */
    private double frictionCoefficient;
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
    /**Whether of not character is on a surface. Used in friction calculation. */
    private boolean isGrounded;

    /**
     * Default constructor for if nothing is specified. Unlikely to be used much outside of debugging, usually every field will have a value.
     */
    Character() {
        x = 500;
        y = 0;
        width = 100;
        height = 100;
        xVelocity = 0;
        yVelocity = 0;
        frictionCoefficient = 0.5;
        maxHealth = 100;
        currentHealth = maxHealth;
        armor = 0.0;
        advancedHitbox = false;
        isGrounded = false;
    }

    /** Constructor for "advanced" characters. Meaning that they have an ArrayList<Rectangle> hitbox.
     * @param setX sets X value (character's spawn point)
     * @param setY sets Y value (character's spawn point)
     * @param setFrictionCoefficient sets frictionCoefficient
     * @param setMaxHealth sets maxHealth
     * @param setArmor sets armor
     * @param setHitBox sets hitBox
     */
    Character(double setX, double setY, double setFrictionCoefficient, double setMaxHealth, double setArmor, ArrayList<Rectangle> setHitBox) {
        x = setX;
        y = setY;
        frictionCoefficient = setFrictionCoefficient;
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
     * @param setFrictionCoefficient sets frictionCoefficient
     * @param setMaxHealth sets maxHealth
     * @param setArmor sets armor
     */
    public Character(double setX, double setY, double setWidth, double setHeight, double setFrictionCoefficient, double setMaxHealth, double setArmor) {
        x = setX;
        y = setY;
        width = setWidth;
        height = setHeight;
        frictionCoefficient = setFrictionCoefficient;
        maxHealth = setMaxHealth;
        currentHealth = maxHealth;
        armor = setArmor;
        advancedHitbox = false;
    }

    /**Call this in "draw()" to draw this character and run it's logic*/
    void run() {
        physicsUpdate();
        displayHitbox();
    }

    /**Updates x, y, and their velocities*/
    private void physicsUpdate() {
        y += yVelocity;
        yVelocity += Sketch.GLOBAL_GRAVITY;
        x += xVelocity;

        if (xVelocity > 0 && isGrounded && !rightPressed) {
            xVelocity -= frictionCoefficient;
        } else if (xVelocity <0 && isGrounded && !leftPressed) {
            xVelocity += frictionCoefficient;
        }
    }

    /**Displays the hitbox. Used for debugging. Later will display a sprite instead. */
    private void displayHitbox() {
        CS125FinalProject.sketch.stroke(0,0,0);
        CS125FinalProject.sketch.fill(0,0,0);
        CS125FinalProject.sketch.rect((float) x,(float) y,(float) width,(float) height);
    }





}
