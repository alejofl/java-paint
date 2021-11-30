package backend.drawable;

import backend.model.Circle;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawableCircle extends DrawableFigure {
    private final Circle figure;

    public DrawableCircle(Point centerPoint, double radius, int zIndex, Color strokeColor, Color fillColor, double lineWidth) {
        super(zIndex, strokeColor, fillColor, lineWidth);
        figure = new Circle(centerPoint, radius);
    }

    @Override
    public void drawFill(GraphicsContext gc) {
        double diameter = figure.getRadius() * 2;
        gc.fillOval(figure.getCenterPoint().getX() - figure.getRadius(), figure.getCenterPoint().getY() - figure.getRadius(), diameter, diameter);
    }

    @Override
    public void drawStroke(GraphicsContext gc) {
        double diameter = figure.getRadius() * 2;
        gc.strokeOval(figure.getCenterPoint().getX() - figure.getRadius(), figure.getCenterPoint().getY() - figure.getRadius(), diameter, diameter);
    }

    @Override
    public void move(double diffX, double diffY) {
        figure.getCenterPoint().move(diffX, diffY);
    }

    @Override
    public boolean includesPoint(Point p) {
        return figure.includesPoint(p);
    }

    @Override
    public Limits limits() {
        return figure.limits();
    }

    @Override
    public String toString(){
        return this.figure.toString();
    }
}
