package frontend.drawable;

import backend.model.Limits;
import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawableRectangle extends Rectangle implements Drawable, Movable {
    private final DrawConfiguration config;

    public DrawableRectangle(Point topLeft, Point bottomRight, int zIndex, Color strokeColor, Color fillColor, double lineWidth) {
        this(new Rectangle(topLeft, bottomRight, zIndex), strokeColor, fillColor, lineWidth);
    }

    // If Rectangle button is selected, 'figure' will store a Rectangle object coming from the first constructor
    // Instead, if Square button is selected, 'figure' will store a Square object coming from DrawableSquare's constructor
    // Based on this, what toString() method returns will depend on the figure it creates
    public DrawableRectangle(Rectangle figure, Color strokeColor, Color fillColor, double lineWidth){
        super(figure.getLimits().getStart(), figure.getLimits().getEnd(), figure.getZIndex());
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
