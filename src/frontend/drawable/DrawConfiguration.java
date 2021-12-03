package frontend.drawable;

import javafx.scene.paint.Color;

// Indicates the visual properties of a figure to be drawn
public class DrawConfiguration {
    private Color fillColor;
    private Color lineColor;
    private double lineWidth;

    public DrawConfiguration(Color fillColor, Color lineColor, double lineWidth) {
        setFillColor(fillColor);
        setLineColor(lineColor);
        setLineWidth(lineWidth);
    }

    public Color getFillColor() {
        return fillColor;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public double getLineWidth() {
        return lineWidth;
    }

    public void setFillColor(Color fillCOlor) {
        this.fillColor = fillCOlor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }
}
