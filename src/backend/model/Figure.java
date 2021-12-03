package backend.model;

public abstract class Figure implements Comparable<Figure> {

    // Any figure will be defined by its limits and its depth (zIndex)
    private final Limits limits;
    private int zIndex;

    /**
     * Abstract class that models all figures as the rectangle that encompasses the respective figure.
     * @param limits limits of the figure
     * @param zIndex depth of figure
     */
    public Figure(Limits limits, int zIndex) {
        this.limits = limits;
        setZIndex(zIndex);
    }

    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    public int getZIndex() {
        return zIndex;
    }

    /**
     * Indicates if p is inside the area of the figure
     * @param p the point consulted
     * @return true if the point is inside this, false if not
     */
    public abstract boolean includesPoint(Point p);

    public Limits getLimits() {
        return limits;
    }

    // Figures will have a natural order, defined by its depth in the canvas (ascending order)
    @Override
    public int compareTo(Figure other) {
        return Integer.compare(this.zIndex, other.zIndex);
    }
}
