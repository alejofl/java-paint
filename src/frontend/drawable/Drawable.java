package frontend.drawable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public interface Drawable {
    Color SELECTED_COLOR = Color.RED;

    // Defines how figures will be drawn on the canvas
    default void draw(GraphicsContext gc, boolean selected) {
        if (selected) {
            gc.setStroke(SELECTED_COLOR);
        } else {
            gc.setStroke(getDrawConfiguration().getLineColor());
        }
        gc.setFill(getDrawConfiguration().getFillColor());
        gc.setLineWidth(getDrawConfiguration().getLineWidth());
        drawFill(gc);
        if (getDrawConfiguration().getLineWidth() != 0) {
            drawStroke(gc);
        }
    }

    DrawConfiguration getDrawConfiguration();

    // Any class that implements Drawable, must define how will its area and border be drawn
    void drawFill(GraphicsContext gc);

    void drawStroke(GraphicsContext gc);
}
