package backend.model;

public class Rectangle extends Figure {

    private final static String RECTANGLE = "Rectángulo";
    private String figureName;

    /**
     * Rectangle model
     * @param topLeft topLeft point of the rectangle
     * @param bottomRight bottomRight point of the rectangle
     * @param zIndex depth of the rectangle
     */
    public Rectangle(Point topLeft, Point bottomRight, int zIndex) {
        super(new Limits(topLeft, bottomRight), zIndex);
        setFigureName(RECTANGLE);
    }

    // In order to reuse this method in Square class, we must define a String which value will depend on the figure used
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
