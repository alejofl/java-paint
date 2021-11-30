package backend.model;

public class Circle extends Ellipse {

    public Circle(Square squareBorder){
        super(squareBorder);
    }

    @Override
    public String toString(){
        return String.format(String.format("Círculo [Centro: %s, Radio: %.2f]", getCenter(), getXRadius()));
    }
}

// CLASE ANTIGUA DE CIRCLE
//public class Circle2 extends Figure {
//
//    private final Point centerPoint;
//    private final double radius;
//
//    public Circle2(Point centerPoint, double radius) {
//        this.centerPoint = centerPoint;
//        this.radius = radius;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("Círculo [Centro: %s, Radio: %.2f]", centerPoint, radius);
//    }
//
//    public Point getCenterPoint() {
//        return centerPoint;
//    }
//
//    public double getRadius() {
//        return radius;
//    }
//
//    @Override
//    public boolean includesPoint(Point p){
//        return Math.sqrt(Math.pow(this.getCenterPoint().getX() - p.getX(), 2) +
//                Math.pow(this.getCenterPoint().getY() - p.getY(), 2)) < this.getRadius();
//    }
//
//    @Override
//    public Point[] limits() {
//        return new Point[]{
//                new Point(getCenterPoint().getX() - radius, getCenterPoint().getY() - radius),
//                new Point(getCenterPoint().getX() + radius, getCenterPoint().getY() + radius)
//        };
//    }
//}
