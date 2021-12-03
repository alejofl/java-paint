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

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }
}
