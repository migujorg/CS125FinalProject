package com.example.CS125FinalProject;

//import androidx.constraintlayout.solver.widgets.Rectangle;

import java.lang.reflect.Array;
import java.util.ArrayList;

import processing.core.PApplet;

class Room {
    /** list of platforms in this room. */
    private ArrayList<Environment> environments;
    /** list of Characters in this room. */
    private ArrayList<Character> characters;
    /** list of Sprites in this room. */
    private ArrayList<Sprite> sprites;
    /** list of TextBoxes in this room. */
    private ArrayList<TextBox> textBoxes;
    /** Used to call special room-specific methods. (For example, for rooms that kill you)*/
    private String type = "normal";

    private int currentTextBox;
    //TODO: private ArrayList<Sprite> sprites = new ArrayList<Sprite()> used later to draw sprites

    /** Default constructor. Every list is empty. Pretty much never used */
    Room() {
        environments = new ArrayList<>();
        characters = new ArrayList<>();
        sprites = new ArrayList<>();
    }

    /** Room constructor with no Sprites. Useful during basic room layout design phase */
    Room(ArrayList<Environment> setEnvironments, ArrayList<Character> setCharacters) {
        environments = setEnvironments;
        characters = setCharacters;
        sprites = new ArrayList<Sprite>();
    }

    /** Almost full rooms constructor. Has every element except for type. Type is set to it's default of "normal". */
    Room(ArrayList<Environment> setEnvironments, ArrayList<Character> setCharacters,
         ArrayList<Sprite> setSprites, ArrayList<TextBox> setTextBoxes) {
        environments = setEnvironments;
        characters = setCharacters;
        sprites = setSprites;
        textBoxes = setTextBoxes;
    }

    /** Full rooms constructor. Has every element except for type. Type is set to it's default of "normal". */
    Room(ArrayList<Environment> setEnvironments, ArrayList<Character> setCharacters,
         ArrayList<Sprite> setSprites, ArrayList<TextBox> setTextBoxes, String setType) {
        environments = setEnvironments;
        characters = setCharacters;
        sprites = setSprites;
        textBoxes = setTextBoxes;
        type = setType;
    }

    /** displays the room on the phone screen. Also runs the appropriate logic. */
    void runRoom() {
        doSprites();
        doTextBoxes();
        doCharacters();
        doEnvironments();
    }

    private void doSprites() {
        for (Sprite tempS: sprites) {
            tempS.run();
        }
    }

    private void doTextBoxes() {
        for (int i = 0; i < textBoxes.size(); i++) {
            if (currentTextBox >= i) {
                textBoxes.get(i).drawBackground();
                textBoxes.get(i).drawText();
            }
        }
        if (currentTextBox < textBoxes.size() && textBoxes.get(currentTextBox).isComplete()) {
            currentTextBox++;
        }
    }

    private void doCharacters() {
        for (Character tempC: characters) {
            tempC.run();
        }
    }

    private void doEnvironments() {
        for (Environment tempE: environments) { //For each environment
            for (Character tempC: characters) { //Each environment runs for each character
                tempE.run(tempC);
            }
        }
    }

    private void doDeathScreen() {

    }

}
