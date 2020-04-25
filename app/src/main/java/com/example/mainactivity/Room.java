package com.example.mainactivity;

import androidx.constraintlayout.solver.widgets.Rectangle;

import java.util.ArrayList;

public class Room {
    /** list of Portals in this room. */
    private ArrayList<Portal> portals;
    /** list of platforms in this room. */
    private ArrayList<Rectangle> platforms;
    /** list of Characters in this room. */
    private ArrayList<Character> characters;
    //TODO: private ArrayList<Sprite> sprites = new ArrayList<Sprite()> used later to draw sprites

    /** Room constructor. Must specify each element. */
    Room(ArrayList<Portal> setPortals, ArrayList<Rectangle> setPlatforms, ArrayList<Character> setCharacters) {
        portals = setPortals;
        platforms = setPlatforms;
        characters = setCharacters;
    }
    //TODO: Add constructors for every case. (Room with no portals, no platforms, no characters, or any combination.)

    /** displays the room on the phone screen. Also runs the appropriate logic. */
    void runRoom() {
        //TODO: portalUpdate();
        //TODO: runCharacters();
        //TODO: displayPlatforms();
        //TODO: Basically, run the characters and make sure that they collide with platofrms in the level. Make sure portals work too.
    }


}
