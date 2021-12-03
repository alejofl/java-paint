package backend.model;

public class Square extends Rectangle {

    private final static String SQUARE = "Cuadrado";

    /**
     * As square is a particular type of rectangle, we just reuse Rectangle class behaviour and change figureName string
     * @param topLeft topLeft point of the square
     * @param edge edge of square
     * @param zIndex depth
     */
    public Square(Point topLeft, double edge, int zIndex){
        // Inside the square constructor we build the topLeft point and a point equidistant to that point
        super(topLeft, topLeft.getEquidistantPoint(edge), zIndex);
        setFigureName(SQUARE);
    }
}
