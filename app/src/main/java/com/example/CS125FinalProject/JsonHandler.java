package com.example.CS125FinalProject;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;


/**
 * Extracts summary information about a game from JSON
 */
public class JsonHandler {
    /**JsonHandler creates a RoomManager object from the json input file. */
    private static RoomManager roomManager;

    public static RoomManager getRoomManager(Context context) throws IOException {
        Reader reader = new InputStreamReader(context.getResources().openRawResource(R.raw.game_setup));
        Object object = JsonParser.parseReader(reader);
        JsonObject jsonObject = (JsonObject) object;
        ArrayList<Room> roomArray = new ArrayList<>();
        JsonArray rooms = jsonObject.get("rooms").getAsJsonArray();
        for(int i = 0; i < rooms.size(); i++) {
            ArrayList<Environment> environmentArray = new ArrayList<>();
            JsonArray environments = ((JsonObject) rooms.get(i)).get("environments").getAsJsonArray();
            for (int j = 0; j < environments.size(); j++) {
                int type = ((JsonObject) environments.get(i)).get("type").getAsInt();
                JsonObject jAsObject = ((JsonObject) environments.get(i));
                if (type == Environment.PLATFORM) {
                    environmentArray.add(new Platform(jAsObject.get("x").getAsDouble(),
                            jAsObject.get("y").getAsDouble(),
                            jAsObject.get("width").getAsDouble(),
                            jAsObject.get("height").getAsDouble()));
                } else if (type == Environment.STAIRS) {
                    environmentArray.add(new Stairs(jAsObject.get("x").getAsDouble(),
                            jAsObject.get("y").getAsDouble(),
                            jAsObject.get("width").getAsDouble(),
                            jAsObject.get("height").getAsDouble()));
                } else {
                    environmentArray.add(new Portal(jAsObject.get("x").getAsDouble(),
                            jAsObject.get("y").getAsDouble(),
                            jAsObject.get("width").getAsDouble(),
                            jAsObject.get("height").getAsDouble(),
                            jAsObject.get("destination").getAsInt(),
                            jAsObject.get("requiresInteract").getAsBoolean()));
                }
            }
            ArrayList<Character> characterArray = new ArrayList<>();
            JsonArray characters = ((JsonObject) rooms.get(i)).get("characters").getAsJsonArray();
            for (int j = 0; j < characters.size(); j++) {
                JsonObject jAsObject = ((JsonObject) characters.get(i));
                characterArray.add(new Character(jAsObject.get("x").getAsDouble(),
                        jAsObject.get("y").getAsDouble(),
                        jAsObject.get("width").getAsDouble(),
                        jAsObject.get("height").getAsDouble(),
                        jAsObject.get("maxHealth").getAsDouble(),
                        jAsObject.get("armor").getAsDouble(),
                        jAsObject.get("isPlayer").getAsBoolean()));
            }
            roomArray.add(new Room(environmentArray, characterArray));
        }
        roomManager = new RoomManager(roomArray);
        return roomManager;
    }

    //public RoomManager getRoomManager() {
    //    return roomManager;
    //}
}
