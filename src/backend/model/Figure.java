package backend.model;

public abstract class Figure {
    public abstract boolean includesPoint(Point p);

    // RETURNS THE SMALLEST RECTANGLE THAT INCLUDES THE LIMITS OF THE FIGURE. { startPoint, endPoint }
    public abstract Limits limits();

    public static class Limits {
        private final Point start;
        private final Point end;

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
}
