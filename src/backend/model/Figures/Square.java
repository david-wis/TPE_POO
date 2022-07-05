package backend.model.Figures;

import backend.model.Point;

public abstract class Square extends Rectangle {

    public Square(Point topLeft, double size, ColorData colorData) {
        super(topLeft, new Point(topLeft.getX() + size, topLeft.getY() + size), colorData);
    }

    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", topLeft, bottomRight);
    }

}
