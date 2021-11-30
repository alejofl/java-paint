package backend.model;

public class Rectangle extends Figure {

    private final Point topLeft, bottomRight;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", topLeft, bottomRight);
    }

    @Override
    public boolean includesPoint(Point p){
        return p.getX() > this.getTopLeft().getX() && p.getX() < this.getBottomRight().getX() &&
                p.getY() > this.getTopLeft().getY() && p.getY() < this.getBottomRight().getY();
    }

    @Override
    public Limits limits() {
        return new Limits(topLeft, bottomRight);
    }
}
