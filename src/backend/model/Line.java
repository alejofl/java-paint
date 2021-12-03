package backend.model;

public class Line extends Figure {

    public Line(Limits limits, int zIndex){
        super(limits, zIndex);
    }

    @Override
    public String toString(){
        return String.format("Linea [ Extremos: [ %s, %s ] ; Longitud: %.2f ]", getLimits().getStart(), getLimits().getEnd(),
               (getLimits().getStart().distanceTo(getLimits().getEnd())));
    }

    @Override
    public boolean includesPoint(Point p){
        // A mathematical explanation of why this works: https://stackoverflow.com/questions/17692922/check-is-a-point-x-y-is-between-two-points-drawn-on-a-straight-line
        return Double.compare((getLimits().getStart().distanceTo(p)) + (getLimits().getEnd().distanceTo(p)),
                (getLimits().getStart().distanceTo(getLimits().getEnd()))) == 0;
    }
}
