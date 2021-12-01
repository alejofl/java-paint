package backend.model;

public class Line extends Figure {

    public Line(Limits limits, int zIndex){
        super(limits, zIndex);
    }

    private double distanceBetweenPoints(Point p1, Point p2){
        return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
    }

    @Override
    public String toString(){
        return String.format("Linea [ Extremos: [ %s, %s ] ; Longitud: %.2f ]", getLimits().getStart(), getLimits().getEnd(),
                this.distanceBetweenPoints(getLimits().getStart(), getLimits().getEnd()));
    }

    @Override
    public boolean includesPoint(Point p){
        // Deduccion Matematica: https://stackoverflow.com/questions/17692922/check-is-a-point-x-y-is-between-two-points-drawn-on-a-straight-line
        return Double.compare(distanceBetweenPoints(getLimits().getStart(), p) + distanceBetweenPoints(getLimits().getEnd(), p),
                distanceBetweenPoints(getLimits().getStart(), getLimits().getEnd())) == 0;
    }
}
