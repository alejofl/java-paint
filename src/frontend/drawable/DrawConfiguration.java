package frontend.drawable;

import javafx.scene.paint.Color;

public class DrawConfiguration {
    private Color fillColor;
    private Color lineColor;
    private double lineWidth;

    /**
     * Indicates the visual properties of a figure to be drawn
     * @param fillColor color to fill the figure with
     * @param lineColor color of the border of the figure
     * @param lineWidth width of the border of the figure
     */
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

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }
}
