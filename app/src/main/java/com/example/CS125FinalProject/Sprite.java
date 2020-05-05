package com.example.CS125FinalProject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

/**This class manages the location, scale and drawing of an image.*/
public class Sprite {
    /**A instance of the Rectangle class that stores the location and dimensions of the Sprite.*/
    private Rectangle dimensions = new Rectangle();
    /**The PImage class is used to actualy render the image on the canvas.*/
    private PImage image = new PImage();
    private ArrayList<PImage> animation = new ArrayList<>();
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
        image = Main.sketch.loadImage(fileName);
        dimensions.width = image.width;
        dimensions.height = image.height;
    }

    /**This constructor specifies location and a "scaleFactor" to scale the image by this amount. */
    public Sprite(double setX, double setY, double scaleFactor, String fileName) {
        dimensions.x = setX;
        dimensions.y = setY;
        if (fileName.equals("oAnimation")) {
            setUpOrangePortal();
            isAnimation = true;
        } else if (fileName.equals("bAnimation")) {
            setUpBluePortal();
            isAnimation = true;
        } else if (fileName.equals("pAnimation")) {
            setUpPurplePortal();
            isAnimation = true;
        } else {
            image = Main.sketch.loadImage(fileName);
        }
        dimensions.width = image.width * scaleFactor;
        dimensions.height = image.height * scaleFactor;
    }
    /**This method renders the sprite on the canvas. */
    public void run() {
        if (!isAnimation) {
            Main.sketch.image(image, (float) dimensions.x, (float) dimensions.y, (float) dimensions.width, (float) dimensions.height);
        } else {
            showAnimation(5);
        }
    }

    private void setUpOrangePortal() {
        PImage frame1 = Main.sketch.loadImage("orange1.png");
        PImage frame2 = Main.sketch.loadImage("orange2.png");
        PImage frame3 = Main.sketch.loadImage("orange3.png");
        PImage frame4 = Main.sketch.loadImage("orange4.png");
        PImage frame5 = Main.sketch.loadImage("orange5.png");
        PImage frame6 = Main.sketch.loadImage("orange6.png");
        PImage frame7 = Main.sketch.loadImage("orange7.png");
        PImage frame8 = Main.sketch.loadImage("orange8.png");
        PImage frame9 = Main.sketch.loadImage("orange9.png");
        animation.add(frame1);
        animation.add(frame2);
        animation.add(frame3);
        animation.add(frame4);
        animation.add(frame5);
        animation.add(frame6);
        animation.add(frame7);
        animation.add(frame8);
        animation.add(frame9);
    }

    private void setUpBluePortal() {
        PImage frame1 = Main.sketch.loadImage("blue1.png");
        PImage frame2 = Main.sketch.loadImage("blue2.png");
        PImage frame3 = Main.sketch.loadImage("blue3.png");
        PImage frame4 = Main.sketch.loadImage("blue4.png");
        PImage frame5 = Main.sketch.loadImage("blue5.png");
        PImage frame6 = Main.sketch.loadImage("blue6.png");
        PImage frame7 = Main.sketch.loadImage("blue7.png");
        PImage frame8 = Main.sketch.loadImage("blue8.png");
        PImage frame9 = Main.sketch.loadImage("blue9.png");
        animation.add(frame1);
        animation.add(frame2);
        animation.add(frame3);
        animation.add(frame4);
        animation.add(frame5);
        animation.add(frame6);
        animation.add(frame7);
        animation.add(frame8);
        animation.add(frame9);
    }

    private void setUpPurplePortal() {
        PImage frame1 = Main.sketch.loadImage("purple1.png");
        PImage frame2 = Main.sketch.loadImage("purple2.png");
        PImage frame3 = Main.sketch.loadImage("purple3.png");
        PImage frame4 = Main.sketch.loadImage("purple4.png");
        PImage frame5 = Main.sketch.loadImage("purple5.png");
        PImage frame6 = Main.sketch.loadImage("purple6.png");
        PImage frame7 = Main.sketch.loadImage("purple7.png");
        PImage frame8 = Main.sketch.loadImage("purple8.png");
        PImage frame9 = Main.sketch.loadImage("purple9.png");
        animation.add(frame1);
        animation.add(frame2);
        animation.add(frame3);
        animation.add(frame4);
        animation.add(frame5);
        animation.add(frame6);
        animation.add(frame7);
        animation.add(frame8);
        animation.add(frame9);
    }

    private void showAnimation(double inverseSpeed) {
        PImage frame = animation.get((int) ((Main.sketch.frameCount / inverseSpeed) % animation.size()));
        Main.sketch.image(frame, (float) dimensions.x, (float) dimensions.y, (float) frame.width, (float) frame.height);
    }



}
