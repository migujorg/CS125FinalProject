package com.example.CS125FinalProject;

//import androidx.constraintlayout.solver.widgets.Rectangle;

import java.util.ArrayList;

class Room {
    /** list of platforms in this room. */
    private ArrayList<Environment> environments;
    /** list of Characters in this room. */
    private ArrayList<Character> characters;
    //TODO: private ArrayList<Sprite> sprites = new ArrayList<Sprite()> used later to draw sprites

    /** Default constructor. Every list is null. */
    Room() {}

    /** Room constructor. No portals. */
    Room(ArrayList<Environment> setEnvironments, ArrayList<Character> setCharacters) {
        environments = setEnvironments;
        characters = setCharacters;
    }
    //TODO: Add constructors for every case. (Room with no portals, no platforms, no characters, or any combination.)

    /** displays the room on the phone screen. Also runs the appropriate logic. */
    void runRoom() {
        for (Character tempC: characters) {
            tempC.run();
        }
        for (Environment tempE: environments) { //For each environment
            for (Character tempC: characters) { //Each environment runs for each character
                tempE.run(tempC);
            }
        }
        //TODO: portalUpdate();
        //TODO: runCharacters();
        //TODO: displayPlatforms();
        //TODO: Basically, run the characters and make sure that they collide with platofrms in the level. Make sure portals work too.
    }

}
