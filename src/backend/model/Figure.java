package backend.model;

public abstract class Figure {
    public abstract boolean includesPoint(Point p);

    // RETURNS THE SMALLEST RECTANGLE THAT INCLUDES THE LIMITS OF THE FIGURE. { startPoint, endPoint }
    public abstract Point[] limits();
}
