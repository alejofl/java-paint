package frontend.drawable;

import backend.model.Limits;

public interface Movable {

    // Movable classes must define its limits, in order to be able to move them
    default void move(double diffX, double diffY) {
        getLimits().getStart().move(diffX, diffY);
        getLimits().getEnd().move(diffX, diffY);
    }

    Limits getLimits();
}
