package backend.model;

public class Line extends Figure {

    private Point p1, p2;

    public Line(Point p1, Point p2){
        this.p1 = p1;
        this.p2 = p2;
    }

    private double distanceBetweenPoints(Point p1, Point p2){
        return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
    }

    @Override
    public String toString(){
        return String.format("Linea Extremos:[ %s, %s ] Longitud: %.2f", this.p1, this.p2,
                this.distanceBetweenPoints(this.p1, this.p2));
    }

    @Override
    public boolean includesPoint(Point p){
        // Deduccion Matematica: https://stackoverflow.com/questions/17692922/check-is-a-point-x-y-is-between-two-points-drawn-on-a-straight-line
        return Double.compare(distanceBetweenPoints(this.p1, p) + distanceBetweenPoints(this.p2, p),
                distanceBetweenPoints(this.p1, this.p2)) == 0;
    }

    public Limits limits(){
        // Si los extremos de una linea recta estan contenidos dentro de un rectangulo, entonces
        // se puede garantizar que la recta esta contenida en dicho rectangulo
        return new Limits(this.p1, this.p2);
    }
}
