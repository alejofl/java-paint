package frontend.drawable;

import backend.model.Circle;
import backend.model.Limits;
import backend.model.Point;
import javafx.scene.paint.Color;

public class DrawableCircle extends DrawableEllipse {
    // La forma de dibujar un circulo, consistirá en dibujar el punto superior izquierdo y arrastrar a la derecha
    // para obtener la componente x del segundo punto. En base a ello, dibujará un circulo, contenido en el cuadrado
    // formado con dichos punto y componente x
    public DrawableCircle(Point topLeft, double edge, int zIndex, Color strokeColor, Color fillColor, double lineWidth){
        super(new Circle(new Limits(topLeft, edge), zIndex), strokeColor, fillColor, lineWidth);
    }
}