package com.example.CS125FinalProject;
/** Child of Environment class. Manages collisions between characters and platforms. */
public class Platform extends Environment {
    /** Full constructor using x, y, width, and height
     * @param x x coordinate of platform
     * @param y y coordinate of platform
     * @param width width of platform
     * @param height height of platform*/
    Platform(double x, double y, double width, double height) {
        super(new Rectangle(x, y, width, height), Environment.PLATFORM);
    }

    /** Full constructor using Rectangle
     * @param hitbox the hitbox of the Platform*/
    public Platform(Rectangle hitbox) {
        super(hitbox, Environment.PLATFORM);
    }

    /**Runs the logic for Platform. This includes display if Sketch.debugMode is enabled
     * @param c character that the Platform is checking for collision and logic. */
    void run(Character c) {
        characterCollisionCheck(c);
        if (Sketch.debugMode) {
            showPlatform();
        }
    }

    /**Runs collision logic for Platform.
     * @param c character that the Platform is checking for collision and logic. */
    private void characterCollisionCheck(Character c) {
        if (isCharOnTop(c)) {
            c.setYVelocity(0);
            c.setY(super.getHitbox().y - c.getHeight());
            c.setIsGrounded(true);
        } else if (isCharRight(c)) {
            c.setXVelocity(0);
            c.setX(super.getHitbox().x + super.getHitbox().width);
        } else if (isCharLeft(c)) {
            c.setXVelocity(0);
            c.setX(super.getHitbox().x - c.getWidth());
        }
    }

    /**Displays the platform's hitbox. Used for debugging*/
    private void showPlatform() {
        Main.sketch.stroke(255,255,255);
        Main.sketch.fill(255,30,30,100);
        Rectangle temp = super.getHitbox();
        Main.sketch.rect((float) temp.x, (float) temp.y, (float) temp.width, (float) temp.height);
    }

    /**This method is used to determine if a character is "on top" of this platform.
     *  Meaning on the top surface. Uses the character's current Y Velocity to determine if they will be
     *  "on top" of this platform in the next frame. This prevents clipping through platforms.
     * @param c character that the Platform is checking for collision and logic.
     * @return whether or not the character, c, is on top of this Platform*/
    private boolean isCharOnTop(Character c) {
        if (!c.isAdvancedHitbox()) {
            return c.getSimpleHitbox().intersectsX(super.getHitbox())
                    && (new Rectangle(c.getSimpleHitbox().x, c.getSimpleHitbox().y, //Will it intersect or pass through in the next frame based on yVelocity?
                    c.getSimpleHitbox().width,
                    c.getSimpleHitbox().height + c.getYVelocity())).intersectsY(super.getHitbox())
                    && c.getYVelocity() > 0;
        } else {
            return false; //TODO: Advanced hitbox collision
        }
    }

    /**This method is used to determine if a character is on the left side of this platform
     * Meaning touching the left edge. Uses the characters current X velocity to determine if they will be
     * touching the left edge in the next frame. This prevents clipping through platforms.
     * @param c character that the Platform is checking for collision and logic.
     * @return whether or not the character, c, is on the left edge of this platform. */
    private boolean isCharLeft(Character c) {
        if (!c.isAdvancedHitbox()) {
            return c.getSimpleHitbox().intersectsY(super.getHitbox())
                    && (new Rectangle(c.getSimpleHitbox().x,
                    c.getSimpleHitbox().y,
                    c.getSimpleHitbox().width + c.getXVelocity(),
                    c.getSimpleHitbox().height)).intersectsX(super.getHitbox());
        } else {
            return false; //TODO: Advanced hitbox collision
        }
    }

    /** This method is used to determine if a character is on the right side of this platform
     * Meaning touching the right edge. Uses the characters current X velocity to determine if they will be
     * touching the right edge in the next frame. This prevents clipping through platforms.
     * @param c character that the Platform is checking for collision and logic.
     * @return whether or not the character, c, is on the left edge of this platform. */
    private boolean isCharRight(Character c) {
        if (!c.isAdvancedHitbox()) {
            return c.getSimpleHitbox().intersectsY(super.getHitbox())
                    && (new Rectangle (c.getSimpleHitbox().x + c.getXVelocity(),
                    c.getSimpleHitbox().y,
                    c.getSimpleHitbox().width - c.getXVelocity(),
                    c.getSimpleHitbox().height).intersectsX(super.getHitbox()));
        } else {
            return false; //TODO: Advanced hitbox collision
        }
    }

    /**@return the hitbox Rectangle of this platform*/
    public Rectangle getHitbox() {
        return super.getHitbox();
    }
}
