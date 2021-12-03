package frontend.drawable;

import backend.model.Circle;
import backend.model.Point;
import javafx.scene.paint.Color;

public class DrawableCircle extends DrawableEllipse {

    // The way circles are drawn will be the following way: Click on the canvas to indicate the top left point and
    // drag to the right in order to get the edge length of the square that contains the circle to be drawn
    public DrawableCircle(Point topLeft, double edge, int zIndex, Color strokeColor, Color fillColor, double lineWidth){
        super(new Circle(topLeft, edge, zIndex), strokeColor, fillColor, lineWidth);
    }
}