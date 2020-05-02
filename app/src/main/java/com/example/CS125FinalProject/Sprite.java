package com.example.CS125FinalProject;

import processing.core.PApplet;
import processing.core.PImage;

/**This class manages the location, scale and drawing of an image.*/
public class Sprite {
    /**A instance of the Rectangle class that stores the location and dimensions of the Sprite.*/
    private Rectangle dimensions = new Rectangle();
    /**The PImage class is used to actualy render the image on the canvas.*/
    private PImage image = new PImage();

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
        image = Main.sketch.loadImage(fileName);
        dimensions.width = image.width * scaleFactor;
        dimensions.height = image.height * scaleFactor;
    }

    public void run() {
        Main.sketch.image(image, (float) dimensions.x, (float) dimensions.y, (float) dimensions.width, (float) dimensions.height);
    }



}
