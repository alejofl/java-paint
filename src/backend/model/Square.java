package backend.model;

public class Square extends Rectangle {

    private final static String SQUARE = "Cuadrado";

    public Square(Point topLeft, double edge){
        // Dentro de la clase Square creo el punto bottomRight el cual consiste en sumar la longitud
        // del lado, en ambas componentes del punto topLeft
        super(topLeft, new Point(topLeft.getX() + edge, topLeft.getY() + edge));
        this.figureName = SQUARE;
    }


}
