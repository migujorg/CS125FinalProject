package com.example.CS125FinalProject;

import processing.core.PFont;

/** This class manages rendering text on the canvas.*/
public class TextBox {
    private Rectangle box = new Rectangle();
    private String message;
    private PFont font = Main.sketch.createFont("Arial", 100, true);
    //TODO: Right now game pretty much only supports 1 font because it loads that font in the beginning
    public TextBox() {};

    public TextBox(double setX, double setY, double setWidth, double setHeight, String setMessage) {
        box.x = setX;
        box.y = setY;
        box.width = setWidth;
        box.height = setHeight;
        message = setMessage;
        Main.sketch.textFont(font);
    }

    public TextBox(double setX, double setY, String setMessage) {
        box.x = setX;
        box.y = setY;
        box.width = -1;
        box.height = -1;
        message = setMessage;
        Main.sketch.textFont(font);
    }

    public TextBox(double setX, double setY, String setMessage, String setFont, double scale, boolean isSmooth) {
        box.x = setX;
        box.y = setY;
        box.width = -1;
        box.height = -1;
        message = setMessage;
        font = Main.sketch.createFont(setFont, (float) scale, isSmooth);
        Main.sketch.textFont(font);
    }

    public TextBox(double setX, double setY, double setWidth, double setHeight, String setMessage, String setFont, int scale, boolean isSmooth) {
        box.x = setX;
        box.y = setY;
        box.width = setWidth;
        box.height = setHeight;
        message = setMessage;
        font = Main.sketch.createFont(setFont, scale, isSmooth);
        Main.sketch.textFont(font);
    }

    void run() {
        Main.sketch.text(message, (float) box.x,  (float) box.y);
    }

}
