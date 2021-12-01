package frontend.drawable;

import backend.model.Limits;

public interface Movable {
    default void move(double diffX, double diffY) {
        getLimits().getStart().move(diffX, diffY);
        getLimits().getEnd().move(diffX, diffY);
    }

    Limits getLimits();
}
