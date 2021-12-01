package backend.model;

public class Ellipse extends Figure {

    private String stringInfo;
    private final double xRadius, yRadius;

    public Ellipse(Limits limits, int zIndex){
        super(limits, zIndex);
        this.xRadius = (this.getLimits().getEnd().getX() - this.getLimits().getStart().getX()) / 2;
        this.yRadius = (this.getLimits().getEnd().getY() - this.getLimits().getStart().getY()) / 2;
        stringInfo = createStringInfo();
    }

    public Point getCenterPoint(){
        return new Point(this.getLimits().getStart().getX() + this.xRadius,
                         this.getLimits().getStart().getY() + this.yRadius);
    }

    public double getXRadius(){
        return this.xRadius;
    }

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

    @Override
    public String toString() {
        return stringInfo;
    }

    @Override
    public boolean includesPoint(Point p){
        // Deduccion matemática: https://math.stackexchange.com/questions/76457/check-if-a-point-is-within-an-ellipse#:~:text=The%20region%20(disk)%20bounded%20by,it%20is%20outside%20the%20ellipse.
        return (Math.pow((p.getX() - this.getCenterPoint().getX()) / this.xRadius, 2) +
                Math.pow((p.getY() - this.getCenterPoint().getY()) / this.yRadius, 2)) <= 1;
    }
}
