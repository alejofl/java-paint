package backend.drawable;

import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawableRectangle extends DrawableFigure {
    private final Rectangle figure;

    public DrawableRectangle(Point topLeft, Point bottomRight, int zIndex, Color strokeColor, Color fillColor, double lineWidth) {
        super(zIndex, strokeColor, fillColor, lineWidth);
        figure = new Rectangle(topLeft, bottomRight);
    }

    @Override
    public void draw(GraphicsContext gc, boolean selected) {
        super.draw(gc, selected);
        gc.fillRect(figure.getTopLeft().getX(), figure.getTopLeft().getY(),
                Math.abs(figure.getTopLeft().getX() - figure.getBottomRight().getX()), Math.abs(figure.getTopLeft().getY() - figure.getBottomRight().getY()));
        gc.strokeRect(figure.getTopLeft().getX(), figure.getTopLeft().getY(),
                Math.abs(figure.getTopLeft().getX() - figure.getBottomRight().getX()), Math.abs(figure.getTopLeft().getY() - figure.getBottomRight().getY()));
    }

    @Override
    public void move(double diffX, double diffY) {
        figure.getTopLeft().move(diffX, diffY);
        figure.getBottomRight().move(diffX, diffY);
    }
}
