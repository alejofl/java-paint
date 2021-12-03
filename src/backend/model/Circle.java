package backend.model;

public class Circle extends Ellipse {

    /**
     * A Circle is a type of Ellipse, so we reuse Ellipse's behaviour
     *  We just change the string to print
     * @param topLeft topLeft of the imaginary square that encompasses the circle
     * @param diameter diameter of the circle
     * @param zIndex depth of circle
     */
    public Circle(Point topLeft, double diameter, int zIndex){
        super(topLeft, topLeft.getEquidistantPoint(diameter), zIndex);
    }

    @Override
    public String createStringInfo(){
        return String.format("Círculo [Centro: %s, Radio: %.2f]", getCenterPoint(), getXRadius());
    }
}
