package backend.model;

public class Circle extends Ellipse {

    // A Circle is a type of Ellipse, so we reuse Ellipse's behaviour
    // We just change the string to print
    public Circle(Limits limits, int zIndex){
        super(limits, zIndex);
    }

    @Override
    public String createStringInfo(){
        return String.format("Círculo [Centro: %s, Radio: %.2f]", getCenterPoint(), getXRadius());
    }
}
