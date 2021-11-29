package backend;

import backend.drawable.DrawableFigure;
import backend.model.Figure;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;


public class CanvasState {
    private static int INITIAL_ZINDEX = 0;
    private int lowerZIndex = INITIAL_ZINDEX;
    private int higherZIndex = INITIAL_ZINDEX;

    private final Set<DrawableFigure> list = new TreeSet<>();

    public void addFigure(DrawableFigure figure) {
        list.add(figure);
    }

    public int getHigherZIndex() {
        return higherZIndex++;
    }

    public Iterable<DrawableFigure> figures() {
        return new TreeSet<>(list);
    }
}
