package backend;

import backend.model.Figure;
import backend.model.Point;

import java.util.*;


public class CanvasState {
    // CanvasState keeps the record of the "nearest" and "farthest" figure in the canvas
    private static final int INITIAL_ZINDEX = 0;
    private int lowerZIndex = INITIAL_ZINDEX;
    private int higherZIndex = INITIAL_ZINDEX;

    // As the program pastes the figures on the canvas, we must respect how they must be shown
    // So we must iterate through the figures, from the "farthest" to the "nearest" one
    private final TreeSet<Figure> list = new TreeSet<>();

    public void addFigure(Figure figure) {
        list.add(figure);
    }

    // Everytime we create a figure, we must assign it as the new "nearest" one
    // This method is also used for "bring to front" functionality
    public int getHigherZIndex() {
        return higherZIndex++;
    }

    // This method is used for "send to back" functionality
    private int getLowerZIndex() {
        return --lowerZIndex;
    }

    public Iterable<Figure> figures() {
        return new TreeSet<>(list);
    }

    /**
     * @param p the point to consult
     * @return the figure with highest zIndex that contains point p, null if there is no valid result
     */
    public Optional<Figure> getFigure(Point p) {
        for (Figure currentFigure : list.descendingSet()) {
            if (currentFigure.includesPoint(p)) {
                return Optional.of(currentFigure);
            }
        }
        return Optional.empty();
    }

    // As we change figure's zIndex which defines its order, we must remove it from the collection, update it and add it again
    // If not, the collection stays in an inconsistent state
    public void bringToFront(Figure figure) {
        remove(figure);
        figure.setZIndex(getHigherZIndex());
        addFigure(figure);
    }

    public void sendToBack(Figure figure) {
        remove(figure);
        figure.setZIndex(getLowerZIndex());
        addFigure(figure);
    }

    public void remove(Figure figure) {
        list.remove(figure);
    }
}
