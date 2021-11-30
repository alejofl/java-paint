package backend.drawable;

import backend.model.Circle;
import backend.model.Point;
import backend.model.Square;
import javafx.scene.paint.Color;

public class DrawableCircle extends DrawableEllipse {

    public DrawableCircle(Point topLeft, double edge, int zIndex, Color strokeColor, Color fillColor, double lineWidth){
        super(new Circle(new Square(topLeft, edge)), zIndex, strokeColor, fillColor, lineWidth);
    }
}