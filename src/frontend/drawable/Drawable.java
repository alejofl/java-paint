package frontend.drawable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public interface Drawable {
    Color SELECTED_COLOR = Color.RED;

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

    void drawFill(GraphicsContext gc);

    void drawStroke(GraphicsContext gc);
}
