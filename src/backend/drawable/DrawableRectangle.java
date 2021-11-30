package backend.drawable;

import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawableRectangle extends DrawableFigure {
    private final Rectangle figure;

    public DrawableRectangle(Point topLeft, Point bottomRight, int zIndex, Color strokeColor, Color fillColor, double lineWidth) {
        this(new Rectangle(topLeft, bottomRight), zIndex, strokeColor, fillColor, lineWidth);
    }

    public DrawableRectangle(Rectangle figure, int zIndex, Color strokeColor, Color fillColor, double lineWidth){
        super(zIndex, strokeColor, fillColor, lineWidth);
        this.figure = figure;
    }

    @Override
    public void drawFill(GraphicsContext gc) {
        gc.fillRect(figure.getTopLeft().getX(), figure.getTopLeft().getY(),
                Math.abs(figure.getTopLeft().getX() - figure.getBottomRight().getX()), Math.abs(figure.getTopLeft().getY() - figure.getBottomRight().getY()));
    }

    @Override
    public void drawStroke(GraphicsContext gc) {
        gc.strokeRect(figure.getTopLeft().getX(), figure.getTopLeft().getY(),
                Math.abs(figure.getTopLeft().getX() - figure.getBottomRight().getX()), Math.abs(figure.getTopLeft().getY() - figure.getBottomRight().getY()));
    }

    @Override
    public void move(double diffX, double diffY) {
        figure.getTopLeft().move(diffX, diffY);
        figure.getBottomRight().move(diffX, diffY);
    }

    @Override
    public boolean includesPoint(Point p) {
        return this.figure.includesPoint(p);
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
