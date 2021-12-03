package backend.model;

import java.util.Objects;

public class Point {

    private double x, y;

    public Point(double x, double y) {
        move(x, y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("{%.2f , %.2f}", x, y);
    }

    // We decided that points can be movable
    public void move(double x, double y) {
        this.x += x;
        this.y += y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
    }

    // Calculates the minimum distance between this and another point
    public double distanceTo(Point other){
        return Math.sqrt(Math.pow(other.getX() - getX(), 2) + Math.pow(other.getY() - getY(), 2));
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
