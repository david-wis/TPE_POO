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
        return centerPoint.distanceTo(eventPoint) < getRadius();
    }

    @Override
    public String getName() {
        return "Circulo";
    }

    @Override
    public void draw(String strokeColor) {
        graphicsController.drawCircle(centerPoint, getRadius(), new ColorData(getFillColor(), strokeColor, getStrokeWeight()));
    }
}
