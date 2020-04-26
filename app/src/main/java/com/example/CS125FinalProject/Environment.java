package com.example.CS125FinalProject;

import com.example.CS125FinalProject.Rectangle;

public abstract class Environment {
    static int PLATFORM = 0;
    public static int DAMAGE = 1;
    public static int MOVEMENT = 2;
    public static int STAIRS = 3; //This works but not having normal side collision (teleports character to top when touches side)

    private Rectangle hitbox;
    private int type;

    Environment(Rectangle setHitbox, int setType) {
        hitbox = setHitbox;
        type = setType;
    }

    abstract void run(Character c);

    Rectangle getHitbox() {
        return hitbox;
    }
}
