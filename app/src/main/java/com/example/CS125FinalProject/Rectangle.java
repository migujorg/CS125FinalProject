package com.example.CS125FinalProject;

public class Rectangle {
    double x;
    double y;
    double width;
    double height;

    Rectangle() {}

    Rectangle(double setX, double setY, double setWidth, double setHeight) {
        x = setX;
        y = setY;
        width = setWidth;
        height = setHeight;
    }

    void setBounds(double setX, double setY, double setWidth, double setHeight) {
        x = setX;
        y = setY;
        width = setWidth;
        height = setHeight;
    }

    boolean intersects(Rectangle rectangle) {
        return x >= rectangle.x && x < rectangle.x + rectangle.width && y >= rectangle.y && y < rectangle.y + rectangle.height;
    }

    boolean intersectsX(Rectangle rectangle) {
        return rectangle.x + rectangle.width > x //right edge to right left of left edge
                && rectangle.x < x + width;      //left edge to the left of right edge
    }

    boolean intersectsY(Rectangle rectangle) {
        return rectangle.y + rectangle.height > y //bottom edge below top edge
                && rectangle.y < y + height;      //top edge above bottom edge
    }

    boolean contains(double checkX, double checkY) {
        return checkX >= x && checkX < x + width && checkY >= y && checkY < y + height;
    }

    boolean containsX(double checkX) {
        return checkX >= x && checkX < x + width;
    }

    boolean containsY(double checkY) {
        return checkY >= y && checkY < y + height;
    }

    public double getCenterX() {
        return (this.x + this.width) / 2;
    }

    public double getCenterY() {
        return (this.y + this.height) / 2;
    }
}
