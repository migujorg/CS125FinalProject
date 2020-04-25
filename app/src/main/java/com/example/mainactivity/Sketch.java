package com.example.mainactivity;
import processing.core.PApplet;

public class Sketch extends PApplet{
    public void settings() {
        size(displayWidth, displayHeight);
    }
    public void setup() {}

    public void draw() {
        if (mousePressed) {
            ellipse(mouseX, mouseY, 50, 50);
        }
    }
}

