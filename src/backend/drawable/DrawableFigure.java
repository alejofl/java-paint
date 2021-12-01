package backend.drawable;

import backend.model.Figure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Objects;

public abstract class DrawableFigure extends Figure implements Comparable<DrawableFigure> {
    private static int COUNTER = 1000;
    private int id;
    protected Color strokeColor;
    protected Color fillColor;
    protected double lineWidth;
    private int zIndex;
    private final static Color SELECTED_COLOR = Color.RED;

    public DrawableFigure(int zIndex, Color strokeColor, Color fillColor, double lineWidth) {
        this.zIndex = zIndex;
        this.strokeColor = strokeColor;
        this.fillColor = fillColor;
        this.lineWidth = lineWidth;
        this.id = COUNTER++;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }

    public void setzIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    public void draw(GraphicsContext gc, boolean selected) {
        if (selected) {
            gc.setStroke(SELECTED_COLOR);
        } else {
            gc.setStroke(strokeColor);
        }
        gc.setFill(fillColor);
        gc.setLineWidth(lineWidth);
        drawFill(gc);
        if (lineWidth != 0) {
            drawStroke(gc);
        }
    }

    public abstract void drawFill(GraphicsContext gc);

    public abstract void drawStroke(GraphicsContext gc);

    public abstract void move(double diffX, double diffY);

    @Override
    public int compareTo(DrawableFigure o) {
        return Integer.compare(this.zIndex, o.zIndex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DrawableFigure that = (DrawableFigure) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
