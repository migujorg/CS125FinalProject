package com.example.CS125FinalProject;

public class Platform extends Environment {

    Platform(double x, double y, double width, double height) {
        super(new Rectangle(x, y, width, height), Environment.PLATFORM);
    }

    Platform(Rectangle hitbox) {
        super(hitbox, Environment.PLATFORM);
    }

    void run(Character c) {
        characterCollsionCheck(c);
    }

    private void characterCollsionCheck(Character c) {
        if (isCharOnTop(c)) {
            c.setYVelocity(0);
        }
    }
    private boolean isCharOnTop(Character c) {
        if (!c.isAdvancedHitbox()) {
            return c.getSimpleHitbox().intersectsX(super.getHitbox())
                    && (new Rectangle(c.getSimpleHitbox().x, c.getSimpleHitbox().y, //Will it intersect in the next frame based on yVelocity?
                              c.getSimpleHitbox().width,
                    c.getSimpleHitbox().height + c.getYVelocity())).intersectsY(super.getHitbox())
                    && c.getYVelocity() > 0;
        } else {
            return false; //TODO: Advanced hitbox collision
        }
    }
}
