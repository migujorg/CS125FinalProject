package com.example.CS125FinalProject;

import java.util.ArrayList;
/** This class manages rooms. Holds list of every room. Displays and manages changing of current room. */
public class RoomManager {
    /** ArrayList of every Room in the game. */
    private ArrayList<Room> rooms;
    /** index in the rooms array that marks the current room. Room 0 is always starting room*/
    private int currentRoom;

    /** Constructor for RoomManager.
     * @param setRooms sets the rooms ArrayList<Room>*/
    RoomManager(ArrayList<Room> setRooms) {
        rooms = setRooms;
        currentRoom = 25; //not really necessary b/c default int initialization is 0;
    }

    /** Calls the run() method on the current room. */
    void run() {
        rooms.get(currentRoom).runRoom();
    }

    /**@return returns rooms ArrayList */
    public ArrayList<Room> getRooms() {
        return rooms;
    }

    /**@return returns currentRoom. */
    public int getCurrentRoom() {
        return currentRoom;
    }

    /**@param setCurrentRoom sets currentRoom. */
    public void setCurrentRoom(int setCurrentRoom) {
        currentRoom = setCurrentRoom;
    }

}
