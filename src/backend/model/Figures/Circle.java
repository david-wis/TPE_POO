package backend.model.Figures;

import backend.model.GraphicsController;
import backend.model.Point;

public class Circle extends Ellipse {
    public Circle(Point centerPoint, double radius, ColorData colorData, GraphicsController graphicsController) {
        super(centerPoint, 2 * radius, 2 * radius, colorData, graphicsController);
    }

    @Override
    public String toString() {
        return String.format("%s [Centro: %s, Radio: %.2f]", getName(), centerPoint, sMayorAxis);
    }

    public double getRadius() {
        return sMayorAxis/2;
    }

    @Override
    public boolean pointBelongs(Point eventPoint) {
        return Math.sqrt(Math.pow(centerPoint.getX() - eventPoint.getX(), 2) +
                Math.pow(centerPoint.getY() - eventPoint.getY(), 2)) < getRadius();
    }

    @Override
    public String getName() {
        return "Circulo";
    }

    @Override
    public void draw(boolean isSelected) {
        graphicsController.drawCircle(centerPoint, getRadius(), new ColorData(getFillColor(), getStrokeColor(isSelected), getStrokeWeight()));
    }
}
