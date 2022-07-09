package backend.model.Figures;

import backend.model.GraphicsController;
import backend.model.Point;

public class Square extends Rectangle {

    public Square(Point topLeft, double size, ColorData colorData, GraphicsController graphicsController) {
        super(topLeft, new Point(topLeft.getX() + size, topLeft.getY() + size), colorData, graphicsController);
    }

    @Override
    public String toString() {
        return String.format("%s [ %s , %s ]", getName(), topLeft, bottomRight);
    }

    @Override
    public String getName(){
        return "Cuadrado";
    }

    @Override
    public void draw(String strokeColor){
        graphicsController.drawRectangle(topLeft, bottomRight, new ColorData(getFillColor(), strokeColor, getStrokeWeight()));
    }
}
