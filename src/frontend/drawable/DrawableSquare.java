package frontend.drawable;

import backend.model.Limits;
import backend.model.Point;
import backend.model.Square;
import javafx.scene.paint.Color;

public class DrawableSquare extends DrawableRectangle {
    public DrawableSquare(Point topLeft, double edge, int zIndex, Color strokeColor, Color fillColor, double lineWidth){
        super(new Square(topLeft, edge, zIndex), strokeColor, fillColor, lineWidth);
    }
}
