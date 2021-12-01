package backend.model;

public abstract class Figure implements Comparable<Figure> {
    private final Limits limits;
    private int zIndex;

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

    public abstract boolean includesPoint(Point p);

    public Limits getLimits() {
        return limits;
    }

    @Override
    public int compareTo(Figure other) {
        return Integer.compare(this.zIndex, other.zIndex);
    }
}
