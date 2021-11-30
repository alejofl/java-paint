package backend.drawable;

import backend.model.Line;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawableLine extends DrawableFigure {

    private Line figure;

    public DrawableLine(Point p1, Point p2, int zIndex, Color strokeColor, Color fillColor, double lineWidth){
        super(zIndex, strokeColor, fillColor, lineWidth);
        this.figure = new Line(p1, p2);
    }

    @Override
    public void drawFill(GraphicsContext gc){
        return;
    }

    @Override
    public void drawStroke(GraphicsContext gc){
        gc.strokeLine(this.figure.getP1().getX(), this.figure.getP1().getY(), this.figure.getP2().getX(), this.figure.getP2().getY());
    }

    @Override
    public void move(double diffX, double diffY){
        this.figure.getP1().move(diffX, diffY);
        this.figure.getP2().move(diffX, diffY);
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
