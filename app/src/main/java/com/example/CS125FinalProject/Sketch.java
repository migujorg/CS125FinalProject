package com.example.CS125FinalProject;

import android.app.Activity;
import android.content.Context;

import java.io.IOException;
import java.io.PipedInputStream;
import java.util.ArrayList;
import java.util.Arrays;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.opengl.PGraphicsOpenGL;
import android.media.MediaPlayer;
import android.content.res.AssetFileDescriptor;

/**This can be seen as the "Main Method" of processing.
 *  settings() is ran once before everything.
 *  setup() runs immediately after. Once.
 *  draw() runs a set number of times per second. */
public class Sketch extends PApplet {
    /** Acceleration constant. yVelocity is increased by this many pixels-per-second each frame. */
    static double GLOBAL_GRAVITY = 2;
    /** Friction constant. Magnitude of xVelocity is decreased by this many pixels-per-second each frame it is on a surface. */
    static double FRICTION_COEFFICIENT = 0.5;
    /** Frame rate of the sketch. Will attempt to reach this frame rate, but may be lower if it cannot be achieved. */
    private static int FRAME_RATE = 60;
    /** Boolean used by developer to help debug things. When set to true turns on various display that are normally hidden. */
    static boolean debugMode = true; //affects display of hitboxes
    /** Is the right side of the screen pressed? Used for player control. */
    private boolean rightPressed = false;
    /** Is the left side of the screen pressed? Used for player control. */
    private boolean leftPressed = false;
    /** is the top of the screen pressed? Used for player control. */
    private boolean upPressed = false;

    private ArrayList<ArrayList<PImage>> animations = new ArrayList<>();

    Context activityContext;
    private RoomManager roomManager0;
    private ArrayList<MediaPlayer> terminalSounds = new ArrayList<>();
    private ArrayList<MediaPlayer> music = new ArrayList<>();
    int currentSong = -1;

    Activity activity;
    Context context;
    AssetFileDescriptor assetFileDescriptor;
    public Sketch(Context context){
        activityContext = context;
    }

    /**Settings for the screen. Runs once before everything*/
    public void settings() {
        fullScreen(P2D);
        //smooth(0);
    }

    /**Runs once after settings(). */
    public void setup() {
        ((PGraphicsOpenGL)g).textureSampling(3);
        orientation(LANDSCAPE);
        setUpAnimations();
        roomManager0 = JsonHandler.getRoomManager(activityContext);
        frameRate(FRAME_RATE);
        background(255,0,0); //red for debugging purposes (if you can see it, it's no good)
        activity = this.getActivity();
        context = activity.getApplicationContext();
        setUpSounds();
    }

    /**Runs FRAME_RATE times per second */
    public void draw() {
        refreshBackground();
        //System.out.println("displayWidth: " + displayWidth + "\ndisplayHeight: " +  displayHeight);
        roomManager0.run();
        musicManager();
        if (debugMode) {
            showTouch();
        }
        fill(255,30,30);
        textAlign(CORNER);
        text("" + frameRate, 50, 80);
    }

    /**Shows which part of the screen is being detected as pressed. Used for debugging*/
    private void showTouch() {
        if (leftPressed) {
            fill(0,0,0, 100);
            stroke(0,0,0);
            rect(0, 0, (float) (displayWidth / 2.0), (float) displayHeight);
            if (upPressed) {
                rect(0, 0, (float) (displayWidth / 2.0) , (float) ((displayHeight - 300) / 2.0));
            }
        }
        if (rightPressed) {
            fill(0,0,0, 100);
            stroke(0,0,0);
            rect((float) (displayWidth / 2.0), 0, (float) (displayWidth / 2.0), displayHeight);
            if (upPressed) {
                rect( (float) (displayWidth / 2.0) , 0,  (float) (displayWidth / 2.0), (float) ((displayHeight - 300)/ 2.0));
            }
        }
    }

    /**Draws a black background. Required before each frame to prevent Tron-line drawing. */
    private void refreshBackground() {
        fill(0, 0, 0);    //sets fill color to black
        stroke(0, 0, 0);  //sets edge color to black
        rect(0, 0, displayWidth, displayHeight);   //draws white rectangle the size of the canvas.
    }

    //TODO: FIX TOUCH CONTROLS TO WORK WITH MULTITOUCH

    /**Runs when a touch is detected. Sets leftPressed and rightPressed accordingly. */
    public void touchStarted() {
        if (mouseX < displayWidth / 2) {
            leftPressed =  true;
        } else {
            rightPressed = true;
        }
        if (mouseY < (displayHeight - 300) / 2) {
            upPressed = true;
        }
        fill(0,0,0);
        stroke(0,0,0);
        rect(mouseX - 100, mouseY - 100, 200, 200);
    }

    /**Runs when a touch moves. Sets leftPressed and rightPressed accordingly. */
    public void touchMoved() {
        if (mouseX < displayWidth /2) {
            leftPressed = true;
            rightPressed = false;
        } else {
            rightPressed = true;
            leftPressed = false;
        }
        if (mouseY < (displayHeight - 300) / 2) {
            upPressed = true;
        } else {
            upPressed = false;
        }
    }

    /**Runs when a touch is released. Sets leftPressed and rightPressed accordingly. */
    public void touchEnded() {
        if (mouseX < displayWidth /2) {
            leftPressed = false;
        } else {
            rightPressed = false;
        }
        upPressed = false;
    }

    /**@return returns rightPressed. Used for player control. */
    boolean isRightPressed() {
        return rightPressed;
    }

    /**@return returns leftPressed. Used for player control. */
    boolean isLeftPressed() {
        return leftPressed;
    }

    boolean isUpPressed() { return upPressed; }

    /**@return returns roomManger. This getter is not currently used*/
    public RoomManager getRoomManager() {
        return roomManager0;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public ArrayList<MediaPlayer> getTerminalSounds() {
        return terminalSounds;
    }

    private void setUpSounds() {
        try {
            assetFileDescriptor = context.getAssets().openFd("ui_hacking_charsingle_06.wav");
            setUpSound(terminalSounds, 16, (float) 0.1);
            assetFileDescriptor = context.getAssets().openFd("On-Thngs-to-Come_Looping.mp3");
            setUpSound(music,1, 1);
            assetFileDescriptor = context.getAssets().openFd("02-Puzzle.mp3");
            setUpSound(music,1, (float) 0.6);
        } catch (Exception e) {
            printStackTrace(e);
        }
    }

    private void setUpSound(ArrayList<MediaPlayer> destination, int howMany, float volume) {
        for (int i = 0; i < howMany; i++) {
            try {
                MediaPlayer sound = new MediaPlayer();
                sound.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
                sound.prepare();
                sound.setVolume(volume, volume);
                destination.add(sound);
            } catch (IOException e) {
                printStackTrace(e);
            }
        }
    }
    private void musicManager() {
        if (frameCount > 50 && (currentSong == -1 || !music.get(currentSong).isPlaying())) {
            currentSong++;
            if (currentSong == music.size()) {
                currentSong = 0;
            }
            if (!music.get(currentSong).isPlaying()) {
                music.get(currentSong).start();
            }
        }
    }
    private void setUpAnimations() {
        setUpOrangePortal();
        setUpBluePortal();
        setUpPurplePortal();
        setUpCharacter();
    }

    private void setUpOrangePortal() {
        animations.add(new ArrayList<PImage>());
        PImage frame1 = loadImage("orange1.png");
        PImage frame2 = loadImage("orange2.png");
        PImage frame3 = loadImage("orange3.png");
        PImage frame4 = loadImage("orange4.png");
        PImage frame5 = loadImage("orange5.png");
        PImage frame6 = loadImage("orange6.png");
        PImage frame7 = loadImage("orange7.png");
        PImage frame8 = loadImage("orange8.png");
        PImage frame9 = loadImage("orange9.png");
        animations.get(0).add(frame1);
        animations.get(0).add(frame2);
        animations.get(0).add(frame3);
        animations.get(0).add(frame4);
        animations.get(0).add(frame5);
        animations.get(0).add(frame6);
        animations.get(0).add(frame7);
        animations.get(0).add(frame8);
        animations.get(0).add(frame9);
    }

    private void setUpBluePortal() {
        animations.add(new ArrayList<PImage>());
        PImage frame1 = loadImage("blue1.png");
        PImage frame2 = loadImage("blue2.png");
        PImage frame3 = loadImage("blue3.png");
        PImage frame4 = loadImage("blue4.png");
        PImage frame5 = loadImage("blue5.png");
        PImage frame6 = loadImage("blue6.png");
        PImage frame7 = loadImage("blue7.png");
        PImage frame8 = loadImage("blue8.png");
        PImage frame9 = loadImage("blue9.png");
        animations.get(1).add(frame1);
        animations.get(1).add(frame2);
        animations.get(1).add(frame3);
        animations.get(1).add(frame4);
        animations.get(1).add(frame5);
        animations.get(1).add(frame6);
        animations.get(1).add(frame7);
        animations.get(1).add(frame8);
        animations.get(1).add(frame9);
    }

    private void setUpPurplePortal() {
        animations.add(new ArrayList<PImage>());
        PImage frame1 = loadImage("purple1.png");
        PImage frame2 = loadImage("purple2.png");
        PImage frame3 = loadImage("purple3.png");
        PImage frame4 = loadImage("purple4.png");
        PImage frame5 = loadImage("purple5.png");
        PImage frame6 = loadImage("purple6.png");
        PImage frame7 = loadImage("purple7.png");
        PImage frame8 = loadImage("purple8.png");
        PImage frame9 = loadImage("purple9.png");
        animations.get(2).add(frame1);
        animations.get(2).add(frame2);
        animations.get(2).add(frame3);
        animations.get(2).add(frame4);
        animations.get(2).add(frame5);
        animations.get(2).add(frame6);
        animations.get(2).add(frame7);
        animations.get(2).add(frame8);
        animations.get(2).add(frame9);
    }

    private void setUpCharacter() {
        animations.add(new ArrayList<PImage>());
        PImage frameR1 = loadImage("ghostR1.png");
        PImage frameR2 = loadImage("ghostR2.png");
        PImage frameR3 = loadImage("ghostR3.png");
        PImage frameR4 = loadImage("ghostR4.png");
        PImage frameR5 = loadImage("ghostR5.png");
        PImage frameR6 = loadImage("ghostR6.png");
        PImage frameR7 = loadImage("ghostR7.png");
        PImage frameR8 = loadImage("ghostR8.png");
        PImage frameR9 = loadImage("ghostR9.png");
        animations.get(3).add(frameR1);
        animations.get(3).add(frameR2);
        animations.get(3).add(frameR3);
        animations.get(3).add(frameR4);
        animations.get(3).add(frameR5);
        animations.get(3).add(frameR6);
        animations.get(3).add(frameR7);
        animations.get(3).add(frameR8);
        animations.get(3).add(frameR9);

        animations.add(new ArrayList<PImage>());
        PImage frameL1 = loadImage("ghostL1.png");
        PImage frameL2 = loadImage("ghostL2.png");
        PImage frameL3 = loadImage("ghostL3.png");
        PImage frameL4 = loadImage("ghostL4.png");
        PImage frameL5 = loadImage("ghostL5.png");
        PImage frameL6 = loadImage("ghostL6.png");
        PImage frameL7 = loadImage("ghostL7.png");
        PImage frameL8 = loadImage("ghostL8.png");
        PImage frameL9 = loadImage("ghostL9.png");
        animations.get(4).add(frameL1);
        animations.get(4).add(frameL2);
        animations.get(4).add(frameL3);
        animations.get(4).add(frameL4);
        animations.get(4).add(frameL5);
        animations.get(4).add(frameL6);
        animations.get(4).add(frameL7);
        animations.get(4).add(frameL8);
        animations.get(4).add(frameL9);
    }

    public ArrayList<ArrayList<PImage>> getAnimations() {
        return animations;
    }
}

