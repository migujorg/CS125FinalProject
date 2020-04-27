package com.example.CS125FinalProject.JsonCreator;

import android.graphics.Rect;

import com.example.CS125FinalProject.Character;
import com.example.CS125FinalProject.Environment;
import com.example.CS125FinalProject.Platform;
import com.example.CS125FinalProject.Rectangle;
import com.example.CS125FinalProject.Stairs;
import com.example.CS125FinalProject.Portal;

import java.util.ArrayList;

import processing.core.PApplet;

public class Workshop extends PApplet {
    private ArrayList<Character> characters = new ArrayList<>();
    private ArrayList<Platform> platforms = new ArrayList<>();
    private ArrayList<Stairs> stairs = new ArrayList<>();
    private ArrayList<Portal> portals = new ArrayList<>();
    private Rectangle currentRect = new Rectangle();
    private int currentType = Environment.PLATFORM;

    public Workshop() {}

    public void settings() {
        size(displayWidth, displayHeight);
        smooth(0);
    }

    public void setup() {
        //orientation(LANDSCAPE);
        //frameRate(FRAME_RATE);
        background(255,0,0); //red for debugging purposes (if you can see it, it's no good)
    }

    public void draw() {
        refreshBackground();
        System.out.println(portals.size());
        if (portals.size() >= 1) {
            Rectangle temp = portals.get(portals.size() - 1).getHitBox();
            System.out.println(temp.x + ", " + temp.y + ", " + temp.width + ", " + temp.height);
        }
        drawAllRects();
    }

    private void drawAllRects() {
        for (int i = 0; i < characters.size(); i++) {
            fill(0,0,0,0);
            drawRectangle(characters.get(i).getSimpleHitbox());
        }
        for (int i = 0; i < platforms.size(); i++) {
            fill(255,0,0,200);
            drawRectangle(platforms.get(i).getHitbox());
        }
        for (int i = 0; i < portals.size(); i++) {
            fill(0,0,255,200);
            drawRectangle(portals.get(i).getHitbox());
        }
        drawCurrentRect();
    }

    private void drawRectangle(Rectangle r) {
        rect((float) r.x, (float) r.y, (float) r.width, (float) r.height);
    }

    private void drawCurrentRect() {
        if (currentType == Environment.PLATFORM) {
            fill(255,0,0,200);
        } else if (currentType == Environment.MOVEMENT) {
            fill(255,255,255,200);
        } else if (currentType == Environment.PORTAL) {
            fill(0,0,255,200);
        } else { //STAIRS
            fill(0,255,0,200);
        }
        stroke(0); //black outline;
        rect((float) currentRect.x, (float) currentRect.y, (float) currentRect.width, (float) currentRect.height);
    }

    private void setRectPoint() {
        currentRect.x = mouseX;
        currentRect.y = mouseY;
    }

    private void setRectWH() {
        currentRect.width = mouseX - currentRect.x;
        currentRect.height = mouseY - currentRect.y;
    }

    public void touchStarted() {
        setRectPoint();
    }

    public void touchMoved() {
        setRectWH();
    }

    public void touchEnded() {
        if (currentType == Environment.PLATFORM) {
            //Rectangle hitbox = new Rectangle();
            platforms.add(new Platform(currentRect.getCopy()));
        } else if (currentType == Environment.PORTAL) {
            portals.add(new Portal(currentRect.getCopy(), 1, false));
        } else { //STAIRS
            stairs.add(new Stairs(currentRect.getCopy()));
        }
        currentRect.setBounds(0,0,0,0);
    }

    private void refreshBackground() {
        fill(255, 255, 255);    //sets fill color to white
        stroke(255, 255, 255);  //sets edge color to white
        rect(0, 0, displayWidth, displayHeight);   //draws white rectangle the size of the canvas.
    }




}
