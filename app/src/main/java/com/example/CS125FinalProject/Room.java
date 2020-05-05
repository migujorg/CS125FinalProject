package com.example.CS125FinalProject;

import java.util.ArrayList;

class Room {
    private String name = "";
    /** list of platforms in this room. */
    private ArrayList<Environment> environments;
    /** list of Characters in this room. */
    private ArrayList<Character> characters;
    /** list of Sprites in this room. */
    private ArrayList<Sprite> sprites;
    /** list of TextBoxes in this room. */
    private ArrayList<TextBox> textBoxes;
    /** the TextBox that is currently being drawn. */
    private int currentTextBox;

    private int counter;

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

    /** Full rooms constructor. Has every element */
    Room(ArrayList<Environment> setEnvironments, ArrayList<Character> setCharacters,
         ArrayList<Sprite> setSprites, ArrayList<TextBox> setTextBoxes, String setName) {
        environments = setEnvironments;
        characters = setCharacters;
        sprites = setSprites;
        textBoxes = setTextBoxes;
        name = setName;
    }

    /** Consolidates logic for each room element into one method. */
    void runRoom() {
        doSprites();
        if (((Portal) environments.get(3)).getDestination().equals("restart")) {
            counter++;
            ((Sketch) Main.sketch).setBackgroundHue(counter);
        }
        doTextBoxes();
        doCharacters();
        doEnvironments();
    }

    /** Runs the logic for all of the Sprites int the room. */
    private void doSprites() {
        for (Sprite tempS: sprites) {
            tempS.run();
        }
    }

    /** Runs the logic for all of the TextBoxes int the room. */
    private void doTextBoxes() {
        if (textBoxes.size() > 0) {
            ((Sketch) Main.sketch).drawFF();
        }
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

    /** Runs the logic for all of the Characters int the room. */
    private void doCharacters() {
        for (Character tempC: characters) {
            tempC.run();
        }
    }

    /** Runs the logic for all of the Environments int the room. */
    private void doEnvironments() {
        for (Environment tempE: environments) { //For each environment
            for (Character tempC: characters) { //Each environment runs for each character
                tempE.run(tempC);
            }
        }
    }

    /**@return textBoxes ArrayList. */
    ArrayList<TextBox> getTextBoxes() {
        return textBoxes;
    }

    /**@return characters ArrayList. */
    ArrayList<Character> getCharacters() {
        return characters;
    }

    /**@return name. */
    String getName() {
        return name;
    }

}
