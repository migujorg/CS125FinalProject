package com.example.CS125FinalProject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

/**This class manages the location, scale and drawing of an image.*/
public class Sprite {
    /**A instance of the Rectangle class that stores the location and dimensions of the Sprite.*/
    private Rectangle dimensions = new Rectangle();
    /**The PImage class is used to actually render the image on the canvas.*/
    private PImage image = new PImage();
    /** ArraryList of PImages used for when the desired Sprite is actually an animation. */
    private ArrayList<PImage> animation = new ArrayList<>();
    /** Used to determine whether to run single image logic or animation logic. */
    boolean isAnimation = false;

    /**Full constructor. Useful for scaling the image to your desired size*/
    public Sprite(double setX, double setY, double setWidth, double setHeight, String fileName) {
        dimensions.x = setX;
        dimensions.y = setY;
        dimensions.width = setWidth;
        dimensions.height = setHeight;
        image = Main.sketch.loadImage(fileName);
    }

    /**This constructor only specifies location. The width and height will be set based on the image.*/
    public Sprite(double setX, double setY, String fileName) {
        dimensions.x = setX;
        dimensions.y = setY;
        if (fileName.equals("oAnimation")) {
            isAnimation = true;
            animation = ((Sketch) Main.sketch).getAnimations().get(0);
        } else if (fileName.equals("bAnimation")) {
            isAnimation = true;
            animation = ((Sketch) Main.sketch).getAnimations().get(1);
        } else if (fileName.equals("pAnimation")) {
            isAnimation = true;
            animation = ((Sketch) Main.sketch).getAnimations().get(2);
        } else if (fileName.equals("rAnimation")) {
            isAnimation = true;
            animation = ((Sketch) Main.sketch).getAnimations().get(5);
        } else {
            image = Main.sketch.loadImage(fileName);
            dimensions.width = image.width;
            dimensions.height = image.height;
        }
    }

    /**This constructor specifies location and a "scaleFactor" to scale the image by this amount. */
    public Sprite(double setX, double setY, double scaleFactor, String fileName) {
        dimensions.x = setX;
        dimensions.y = setY;
        if (fileName.equals("oAnimation")) {
            isAnimation = true;
            animation = ((Sketch) Main.sketch).getAnimations().get(0);
        } else if (fileName.equals("bAnimation")) {
            isAnimation = true;
            animation = ((Sketch) Main.sketch).getAnimations().get(1);
        } else if (fileName.equals("pAnimation")) {
            isAnimation = true;
            animation = ((Sketch) Main.sketch).getAnimations().get(2);
        } else if (fileName.equals("rAnimation")) {
            isAnimation = true;
            animation = ((Sketch) Main.sketch).getAnimations().get(5);
        } else {
            image = Main.sketch.loadImage(fileName);
            dimensions.width = image.width * scaleFactor;
            dimensions.height = image.height * scaleFactor;
        }
    }
    /**This method renders the sprite on the canvas. */
    public void run() {
        if (!isAnimation) {
            Main.sketch.image(image, (float) dimensions.x, (float) dimensions.y, (float) dimensions.width, (float) dimensions.height);
        } else {
            showAnimation(5);
        }
    }

    /** Renders the sequence of PImages to play the animation.
     * @param inverseSpeed used to determine how fast to play the animation. Range 1 to 60. Lower numbers
     *                     are faster. (frameRate == Main.sketch.frameRate / inverseSpeed times per second). */
    private void showAnimation(double inverseSpeed) {
        PImage frame = animation.get((int) ((Main.sketch.frameCount / inverseSpeed) % animation.size()));
        Main.sketch.image(frame, (float) dimensions.x, (float) dimensions.y, (float) frame.width, (float) frame.height);
    }
}
