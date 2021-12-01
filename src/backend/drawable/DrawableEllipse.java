package backend.drawable;

import backend.model.Ellipse;
import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawableEllipse extends DrawableFigure {

    private Ellipse figure;

    public DrawableEllipse(Point topLeft, Point bottomRight, int zIndex, Color strokeColor, Color fillColor, double lineWidth){
        this(new Ellipse(new Rectangle(topLeft, bottomRight)), zIndex, strokeColor, fillColor, lineWidth);
    }

    // Si se tiene seleccionado el boton Elipse, figure apuntará a un objeto de tipo Ellipse
    // (proviente del constructor de arriba). En cambio, si se tiene seleccionado el boton Circulo, figure
    // apuntará a un objeto de tipo Circle (que proviene del constructor de DrawableCircle)
    // En base a ello, dependerá lo que devolverá el toString()
    public DrawableEllipse(Ellipse figure, int zIndex, Color strokeColor, Color fillColor, double lineWidth){
        super(zIndex, strokeColor, fillColor, lineWidth);
        this.figure = figure;
    }

    public void drawFill(GraphicsContext gc){
        double width = this.figure.getXRadius() * 2;
        double height = this.figure.getYRadius() * 2;
        gc.fillOval(this.figure.getBorderRectangle().getTopLeft().getX(), this.figure.getBorderRectangle().getTopLeft().getY(),
                width, height);

    }

    public void drawStroke(GraphicsContext gc){
        double width = this.figure.getXRadius() * 2;
        double height = this.figure.getYRadius() * 2;
        gc.strokeOval(this.figure.getBorderRectangle().getTopLeft().getX(), this.figure.getBorderRectangle().getTopLeft().getY(),
                width, height);
    }

    public void move(double diffX, double diffY){
        this.figure.getBorderRectangle().getTopLeft().move(diffX, diffY);
        this.figure.getBorderRectangle().getBottomRight().move(diffX, diffY);
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
