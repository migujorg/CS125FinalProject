package com.example.CS125FinalProject;
/** Child of Environment class. Manages collisions between characters and platforms. */
class Platform extends Environment {
    /** Full constructor
     * @param x x coordinate of platform
     * @param y y coordinate of platform
     * @param width width of platform
     * @param height height of platform
     */
    Platform(double x, double y, double width, double height) {
        super(new Rectangle(x, y, width, height), Environment.PLATFORM);
    }

    Platform(Rectangle hitbox) {
        super(hitbox, Environment.PLATFORM);
    }

    void run(Character c) {
        characterCollisionCheck(c);
    }

    private void characterCollisionCheck(Character c) {
        if (isCharOnTop(c)) {
            c.setYVelocity(0);
            c.setY(super.getHitbox().y - c.getHeight());
            c.setIsGrounded(true);
        } else {
            c.setIsGrounded(false);
        }

        if (Sketch.debugMode) {
            showPlatform();
        }
    }

    private void showPlatform() {
        Main.sketch.stroke(0,0,0);
        Main.sketch.fill(255,30,30,100);
        Rectangle temp = super.getHitbox();
        Main.sketch.rect((float) temp.x, (float) temp.y, (float) temp.width, (float) temp.height);
    }

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
}
