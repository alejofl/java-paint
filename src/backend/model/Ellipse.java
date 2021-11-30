package backend.model;

public class Ellipse extends Figure {

    private final Rectangle borderRectangle;
    private Point center;
    private double xRadius, yRadius;

    public Ellipse(Rectangle borderRectangle){
        this.borderRectangle = borderRectangle;
        this.getEllipseProperties();
    }

    private void getEllipseProperties(){
        this.xRadius = (this.borderRectangle.getBottomRight().getX() - this.borderRectangle.getTopLeft().getX()) / 2;
        this.yRadius = (this.borderRectangle.getTopLeft().getY() - this.borderRectangle.getBottomRight().getY()) / 2;
        this.center = new Point(this.borderRectangle.getTopLeft().getX() + this.xRadius,
                this.borderRectangle.getTopLeft().getY() + this.yRadius);
    }

    public Rectangle getBorderRectangle(){
        return this.borderRectangle;
    }

    protected Point getCenter(){
        return this.center;
    }

    public double getXRadius(){
        return this.xRadius;
    }

    public double getYRadius() {
        return this.yRadius;
    }

    @Override
    public String toString(){
        return String.format("Elipse [Centro: %s, X_Radio: %.2f, Y_Radio: %.2f]", this.center,
                this.xRadius, this.yRadius);
    }

    @Override
    public Limits limits(){
        return this.borderRectangle.limits();
    }

    @Override
    public boolean includesPoint(Point p){
        // Deduccion matemática: https://math.stackexchange.com/questions/76457/check-if-a-point-is-within-an-ellipse#:~:text=The%20region%20(disk)%20bounded%20by,it%20is%20outside%20the%20ellipse.
        return (Math.pow((p.getX() - this.center.getX()) / this.xRadius, 2) +
                Math.pow((p.getY() - this.center.getY()) / this.yRadius, 2)) <= 1;
    }
}
