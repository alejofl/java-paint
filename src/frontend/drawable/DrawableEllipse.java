package frontend.drawable;

import backend.model.Ellipse;
import backend.model.Limits;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawableEllipse extends Ellipse implements Drawable, Movable {
    private final DrawConfiguration config;

    public DrawableEllipse(Point topLeft, Point bottomRight, int zIndex, Color strokeColor, Color fillColor, double lineWidth){
        this(new Ellipse(new Limits(topLeft, bottomRight), zIndex), strokeColor, fillColor, lineWidth);
    }

    // If Ellipse button is selected, 'figure' will store an Ellipse object coming from the first constructor
    // Instead, if Circle button is selected, 'figure' will store a Circle object coming from DrawableCircle's constructor
    // Based on this, what toString() method returns will depend on it
    public DrawableEllipse(Ellipse figure, Color strokeColor, Color fillColor, double lineWidth){
        super(figure.getLimits(), figure.getZIndex());
        setStringInfo(figure.createStringInfo());
        this.config = new DrawConfiguration(fillColor, strokeColor, lineWidth);
    }

    public void drawFill(GraphicsContext gc){
        double width = getXRadius() * 2;
        double height = getYRadius() * 2;
        gc.fillOval(getLimits().getStart().getX(), getLimits().getStart().getY(), width, height);

    }

    public void drawStroke(GraphicsContext gc){
        double width = getXRadius() * 2;
        double height = getYRadius() * 2;
        gc.strokeOval(getLimits().getStart().getX(), getLimits().getStart().getY(), width, height);
    }

    @Override
    public DrawConfiguration getDrawConfiguration() {
        return config;
    }
}
