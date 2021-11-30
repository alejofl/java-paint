package backend.model;

public class Ellipse extends Figure {

    private final Rectangle borderRectangle;
    private final double xRadius, yRadius;

    public Ellipse(Rectangle borderRectangle){
        this.borderRectangle = borderRectangle;
        this.xRadius = (this.borderRectangle.getBottomRight().getX() - this.borderRectangle.getTopLeft().getX()) / 2;
        this.yRadius = (this.borderRectangle.getBottomRight().getY() - this.borderRectangle.getTopLeft().getY()) / 2;
    }

    public Point getCenterPoint(){
        return new Point(this.borderRectangle.getTopLeft().getX() + this.xRadius,
                this.borderRectangle.getTopLeft().getY() + this.yRadius);
    }

    public Rectangle getBorderRectangle(){
        return this.borderRectangle;
    }

    public double getXRadius(){
        return this.xRadius;
    }

    public double getYRadius() {
        return this.yRadius;
    }

    @Override
    public String toString(){
        return String.format("Elipse [Centro: %s, X_Radio: %.2f, Y_Radio: %.2f]", this.getCenterPoint(),
                this.xRadius, this.yRadius);
    }

    @Override
    public Limits limits(){
        return this.borderRectangle.limits();
    }

    @Override
    public boolean includesPoint(Point p){
        // Deduccion matemática: https://math.stackexchange.com/questions/76457/check-if-a-point-is-within-an-ellipse#:~:text=The%20region%20(disk)%20bounded%20by,it%20is%20outside%20the%20ellipse.
        return (Math.pow((p.getX() - this.getCenterPoint().getX()) / this.xRadius, 2) +
                Math.pow((p.getY() - this.getCenterPoint().getY()) / this.yRadius, 2)) <= 1;
    }
}
