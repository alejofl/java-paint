package backend.model;

public class Circle extends Ellipse {

    public Circle(Limits limits, int zIndex){
        super(limits, zIndex);
    }

    @Override
    public String createStringInfo(){
        return String.format("Círculo [Centro: %s, Radio: %.2f]", getCenterPoint(), getXRadius());
    }
}
