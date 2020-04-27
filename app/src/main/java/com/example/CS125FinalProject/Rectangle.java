package com.example.CS125FinalProject;
/** This class is used to manage hitboxes and collisions of various game elements. */
public class Rectangle {
    /** top left corner x coordinate. */
    public double x;
    /** top left corner y coordinate. */
    public double y;
    /** width of Rectangle (in pixels). */
    public double width;
    /** height of Rectangle (in pixels). */
    public double height;
    /** Default constructor creates a Rectangle at (0,0) with width 0, height 0. */
    public Rectangle() {}

    /**Full Constructor
     * @param setX sets X coordinate
     * @param setY sets Y coordinate
     * @param setWidth sets width
     * @param setHeight sets height */
    public Rectangle(double setX, double setY, double setWidth, double setHeight) {
        x = setX;
        y = setY;
        width = setWidth;
        height = setHeight;
    }

    /**Sets the bounds of the Rectangle
     * @param setX sets X coordinate
     * @param setY sets Y coordinate
     * @param setWidth sets width
     * @param setHeight sets height */
    public void setBounds(double setX, double setY, double setWidth, double setHeight) {
        x = setX;
        y = setY;
        width = setWidth;
        height = setHeight;
    }

    /**Detects intersection between two Rectangle objects
     * @param rectangle the other Rectangle to check collision with
     * @return whether or not the two Rectangles intersect. */
    boolean intersects(Rectangle rectangle) {
        return x >= rectangle.x && x < rectangle.x + rectangle.width && y >= rectangle.y && y < rectangle.y + rectangle.height;
    }

    /**Detects "intersection" between X coordinates of two Rectangle Objects.
     * Ignores Y coordinate. In other words, if two rectangles are above/below each other
     * @param rectangle the other Rectangle to check collision with
     * @return whether or not the two Rectangles' X coordinates intersect. */
    boolean intersectsX(Rectangle rectangle) {
        return rectangle.x + rectangle.width > x //right edge to right left of left edge
                && rectangle.x < x + width;      //left edge to the left of right edge
    }

    /**Detects "intersection" between Y coordinates of two Rectangle Objects.
     * Ignores X coordinate. In other words, if two rectangles are side-by-side
     * @param rectangle the other Rectangle to check collision with
     * @return whether or not the two Rectangles' Y coordinates intersect. */
    boolean intersectsY(Rectangle rectangle) {
        return rectangle.y + rectangle.height > y //bottom edge below top edge
                && rectangle.y < y + height;      //top edge above bottom edge
    }

    public boolean contains(double checkX, double checkY) {
        return checkX >= x && checkX < x + width && checkY >= y && checkY < y + height;
    }

    boolean containsX(double checkX) {
        return checkX >= x && checkX < x + width;
    }

    boolean containsY(double checkY) {
        return checkY >= y && checkY < y + height;
    }

    /** @return X coordinate of center of Rectangle.*/
    public double getCenterX() {
        return (this.x + this.width) / 2;
    }

    /** @return Y coordinate of center of Rectangle.*/
    public double getCenterY() {
        return (this.y + this.height) / 2;
    }

    public Rectangle getCopy() {
        return new Rectangle(x, y, width, height);
    }
}
