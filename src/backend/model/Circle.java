package backend.model;

public class Circle extends Ellipse {

    public Circle(Square squareBorder){
        super(squareBorder);
    }

    @Override
    public String toString(){
        return String.format(String.format("Círculo [Centro: %s, Radio: %.2f]", getCenterPoint(), getXRadius()));
    }
}
