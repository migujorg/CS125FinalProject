package com.example.CS125FinalProject;

/** Stairs are another type of environment. When it comes to vertical collision, they are identical
 *  to platforms. However, on horizontal collision they will teleport the player on top of the stair */
public class Stairs extends Environment {
    /** Full constructor using x, y, width, and height
     * @param x x coordinate of Stairs
     * @param y y coordinate of Stairs
     * @param width width of Stairs
     * @param height height of Stairs*/
    Stairs(double x, double y, double width, double height) {
        super(new Rectangle(x, y, width, height), Environment.STAIRS);
    }

    /** Full constructor using Rectangle
     * @param hitbox the hitbox of the Stairs*/
    public Stairs(Rectangle hitbox) {
        super(hitbox, Environment.STAIRS);
    }

    /**Runs the logic for Stairs. This includes display if Sketch.debugMode is enabled
     * @param c character that the Stairs is checking for collision and logic. */
    void run(Character c) {
        characterCollisionCheck(c);
        if (Sketch.debugMode) {
            showStairs();
        }
    }

    /**Runs collision logic for Stairs.
     * @param c character that the Stairs is checking for collision and logic. */
    private void characterCollisionCheck(Character c) {
        if (isCharOnTop(c)) {
            c.setYVelocity(0);
            c.setY(super.getHitbox().y - c.getHeight());
            c.setIsGrounded(true);
        }
    }

    /**Displays the stair's hitbox. Used for debugging*/
    private void showStairs() {
        Main.sketch.stroke(0,0,0);
        Main.sketch.fill(30,30,255,100); //Blue blocks are stairs
        Rectangle temp = super.getHitbox();
        Main.sketch.rect((float) temp.x, (float) temp.y, (float) temp.width, (float) temp.height);
    }

    /**This method is used to determine if a character is "on top" of this stair.
     *  Meaning on the top surface. Uses player's current Y Velocity to determine if they will be
     *  "on top" of this stair in the next frame. This prevents clipping through stairs.
     * @param c character that the Stairs is checking for collision and logic.
     * @return whether or not the character, c, is on top of this Stairs*/
    @Deprecated
    private boolean isCharOnTopWIP(Character c) {
        if (!c.isAdvancedHitbox()) {
            return c.getSimpleHitbox().intersectsX(super.getHitbox())
                    && (new Rectangle(c.getSimpleHitbox().x, c.getSimpleHitbox().y,
                    c.getSimpleHitbox().width,
                    c.getSimpleHitbox().height + c.getYVelocity())).intersectsY(super.getHitbox())
                    && c.getYVelocity() > 0;
        } else {
            return false;
        }
    }

    /**This method is used to determine if a character is "on top" of this stair.
     *  Meaning on the top surface. Uses player's current Y Velocity to determine if they will be
     *  "on top" of this stair in the next frame. This prevents clipping through stairs.
     * @param c character that the Stairs is checking for collision and logic.
     * @return whether or not the character, c, is on top of this Stairs*/
    private boolean isCharOnTop(Character c) {
        if (!c.isAdvancedHitbox()) {
            return c.getSimpleHitbox().intersects(super.getHitbox());
        } else {
            return false;
        }
    }
}
