package backend;

import backend.drawable.DrawableFigure;
import backend.model.Point;

import java.util.*;


public class CanvasState {
    private static int INITIAL_ZINDEX = 0;
    private int lowerZIndex = INITIAL_ZINDEX;
    private int higherZIndex = INITIAL_ZINDEX;

    private final TreeSet<DrawableFigure> list = new TreeSet<>();

    public void addFigure(DrawableFigure figure) {
        list.add(figure);
    }

    public int getHigherZIndex() {
        return higherZIndex++;
    }

    private int getLowerZIndex() {
        return --lowerZIndex;
    }

    public Iterable<DrawableFigure> figures() {
        return new TreeSet<>(list);
    }

    public Optional<DrawableFigure> getFigure(Point p) {
        for (DrawableFigure currentFigure : list.descendingSet()) {
            if (currentFigure.includesPoint(p)) {
                return Optional.of(currentFigure);
            }
        }
        return Optional.empty();
    }

    public void bringToFront(DrawableFigure figure) {
        list.remove(figure);
        figure.setzIndex(getHigherZIndex());
        addFigure(figure);
    }

    public void sendToBack(DrawableFigure figure) {
        list.remove(figure);
        figure.setzIndex(getLowerZIndex());
        addFigure(figure);
    }
}
