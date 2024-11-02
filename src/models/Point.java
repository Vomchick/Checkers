package models;

import java.util.Objects;

public final class Point {
    private int X;
    private int Y;

    public Point(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    public int X() {
        return X;
    }

    public int Y() {
        return Y;
    }

    public void setX(int x) {
        this.X = x;
    }

    public void setY(int y) {
        this.Y = y;
    }
}
