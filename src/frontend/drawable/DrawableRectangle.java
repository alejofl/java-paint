package frontend.drawable;

import backend.model.Limits;
import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawableRectangle extends Rectangle implements Drawable, Movable {
    DrawConfiguration config;
    public DrawableRectangle(Point topLeft, Point bottomRight, int zIndex, Color strokeColor, Color fillColor, double lineWidth) {
        this(new Rectangle(new Limits(topLeft, bottomRight), zIndex), strokeColor, fillColor, lineWidth);
    }

    // Si se tiene seleccionado el boton Rectangulo, figure apuntará a un objeto de tipo Rectangle
    // (proviente del constructor de arriba). En cambio, si se tiene seleccionado el boton Cuadrado, figure
    // apuntará a un objeto de tipo Square (que proviene del constructor de DrawableSquare)
    // En base a ello, dependerá lo que devolverá el toString()
    public DrawableRectangle(Rectangle figure, Color strokeColor, Color fillColor, double lineWidth){
        super(figure.getLimits(), figure.getZIndex());
        setFigureName(figure.getFigureName());
        this.config = new DrawConfiguration(fillColor, strokeColor, lineWidth);
    }

    @Override
    public void drawFill(GraphicsContext gc) {
        gc.fillRect(getLimits().getStart().getX(), getLimits().getStart().getY(),
                Math.abs(getLimits().getStart().getX() - getLimits().getEnd().getX()), Math.abs(getLimits().getStart().getY() - getLimits().getEnd().getY()));
    }

    @Override
    public void drawStroke(GraphicsContext gc) {
        gc.strokeRect(getLimits().getStart().getX(), getLimits().getStart().getY(),
                Math.abs(getLimits().getStart().getX() - getLimits().getEnd().getX()), Math.abs(getLimits().getStart().getY() - getLimits().getEnd().getY()));
    }

    @Override
    public DrawConfiguration getDrawConfiguration() {
        return config;
    }
}
