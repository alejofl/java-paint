package backend;

import backend.model.Figure;
import backend.model.Point;

import java.util.*;


public class CanvasState {
    private static final int INITIAL_ZINDEX = 0;
    private int lowerZIndex = INITIAL_ZINDEX;
    private int higherZIndex = INITIAL_ZINDEX;

    private final TreeSet<Figure> list = new TreeSet<>();

    public void addFigure(Figure figure) {
        list.add(figure);
    }

    public int getHigherZIndex() {
        return higherZIndex++;
    }

    private int getLowerZIndex() {
        return --lowerZIndex;
    }

    public Iterable<Figure> figures() {
        return new TreeSet<>(list);
    }

    public Optional<Figure> getFigure(Point p) {
        for (Figure currentFigure : list.descendingSet()) {
            if (currentFigure.includesPoint(p)) {
                return Optional.of(currentFigure);
            }
        }
        return Optional.empty();
    }

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
