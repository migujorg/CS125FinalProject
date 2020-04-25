package com.example.CS125FinalProject;

import java.util.ArrayList;
/** This class manages rooms. Holds list of every room. Displays and manages changing of current room. */
public class RoomManager {
    /** ArrayList of every Room in the game. */
    private ArrayList<Room> rooms;
    /** index in the rooms array that marks the current room. */
    private int currentRoom;

    RoomManager(ArrayList<Room> setRooms) {
        rooms = setRooms;
        currentRoom = 0; //not necessary b/c default int initialization is 0;
    }

    void run() {
        for (Room temp: rooms) {
            temp.runRoom();
        }
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public int getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(int setCurrentRoom) {
        currentRoom = setCurrentRoom;
    }

}
