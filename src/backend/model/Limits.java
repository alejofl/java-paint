package backend.model;

// Defines the limits of a figure using two points
public class Limits {

    private final Point start;
    private final Point end;

    // Basic constructor.
    public Limits(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    // Constructor to generate a square limit area.
    public Limits(Point start, double edge) {
        this(start, new Point(start.getX() + edge, start.getY() + edge));
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }
}
