package com.example.CS125FinalProject;

//import androidx.constraintlayout.solver.widgets.Rectangle;

import java.util.ArrayList;

class Room {
    /** list of Portals in this room. */
    private ArrayList<Portal> portals;
    /** list of platforms in this room. */
    private ArrayList<Environment> environments;
    /** list of Characters in this room. */
    private ArrayList<Character> characters;
    //TODO: private ArrayList<Sprite> sprites = new ArrayList<Sprite()> used later to draw sprites

    /** Default constructor. Every list is null. */
    Room() {}
    /** Room constructor. Must specify each element. */
    Room(ArrayList<Portal> setPortals, ArrayList<Environment> setEnvironments, ArrayList<Character> setCharacters) {
        portals = setPortals;
        environments = setEnvironments;
        characters = setCharacters;
    }
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
    /** Renders a translucent rectangle on the screen at the position of each portal. Mostly for debugging. */
    void showPortals() {
        for (Portal temp: portals) {
            temp.showPortal();
        }
    }
    /** Runs the portal logic for each portal (Detects collision, teleports character). */
    void runPortals() {
        for (Portal temp: portals) {
            temp.runPortal(((Sketch) Main.sketch).getPlayer());
        }
    }

}
