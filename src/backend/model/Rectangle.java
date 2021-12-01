package backend.model;

public class Rectangle extends Figure {

    private final static String RECTANGLE = "Rectángulo";
    private String figureName;

    public Rectangle(Limits limits, int zIndex) {
        super(limits, zIndex);
        setFigureName(RECTANGLE);
    }

    @Override
    public String toString() {
        return String.format("%s [ %s , %s ]", figureName, getLimits().getStart(), getLimits().getEnd());
    }

    public void setFigureName(String name) {
        figureName = name;
    }

    public String getFigureName() {
        return figureName;
    }

    @Override
    public boolean includesPoint(Point p){
        return p.getX() > this.getLimits().getStart().getX() && p.getX() < this.getLimits().getEnd().getX() &&
               p.getY() > this.getLimits().getStart().getY() && p.getY() < this.getLimits().getEnd().getY();
    }
}
