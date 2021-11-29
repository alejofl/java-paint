package backend.model;

public class Circle extends Figure {

    private final Point centerPoint;
    private final double radius;

    public Circle(Point centerPoint, double radius) {
        this.centerPoint = centerPoint;
        this.radius = radius;
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", centerPoint, radius);
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public boolean includesPoint(Point p){
        return Math.sqrt(Math.pow(this.getCenterPoint().getX() - p.getX(), 2) +
                Math.pow(this.getCenterPoint().getY() - p.getY(), 2)) < this.getRadius();
    }

}
