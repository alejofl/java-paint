package backend.model;

public class Ellipse extends Figure {

    private String stringInfo;
    private final double xRadius, yRadius;

    /**
     * An ellipse will be defined by the limits of the rectangle that encloses it
     * @param topLeft topLeft of imaginary rectangle that encompasses the ellipse
     * @param bottomRight bottomRight of imaginary rectangle that encompasses the ellipse
     * @param zIndex depth of ellipse
     */
    public Ellipse(Point topLeft, Point bottomRight, int zIndex){
        super(new Limits(topLeft, bottomRight), zIndex);
        this.xRadius = (this.getLimits().getEnd().getX() - this.getLimits().getStart().getX()) / 2;
        this.yRadius = (this.getLimits().getEnd().getY() - this.getLimits().getStart().getY()) / 2;
        stringInfo = createStringInfo();
    }

    /**
     * @return the ellipse's center point
     */
    public Point getCenterPoint(){
        return new Point(this.getLimits().getStart().getX() + this.xRadius,
                         this.getLimits().getStart().getY() + this.yRadius);
    }

    /**
     * @return the ellipse's radius in x-axis
     */
    public double getXRadius(){
        return this.xRadius;
    }

    /**
     * @return the ellipse's radius in y-axis
     */
    public double getYRadius() {
        return this.yRadius;
    }

    public String createStringInfo(){
        return String.format("Elipse [Centro: %s, X_Radio: %.2f, Y_Radio: %.2f]", this.getCenterPoint(),
                this.xRadius, this.yRadius);
    }

    public void setStringInfo(String stringInfo) {
        this.stringInfo = stringInfo;
    }

    // For the same reason that happens with Rectangle and Square, we must define
    // a String that contains the text to print depending on if the figure is an Ellipse or a Circle
    @Override
    public String toString() {
        return stringInfo;
    }

    @Override
    public boolean includesPoint(Point p){
        // A mathematical explanation of why this works: https://math.stackexchange.com/questions/76457/check-if-a-point-is-within-an-ellipse#:~:text=The%20region%20(disk)%20bounded%20by,it%20is%20outside%20the%20ellipse.
        return (Math.pow((p.getX() - this.getCenterPoint().getX()) / this.xRadius, 2) +
                Math.pow((p.getY() - this.getCenterPoint().getY()) / this.yRadius, 2)) <= 1;
    }
}
