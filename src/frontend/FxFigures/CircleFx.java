package frontend.FxFigures;

import backend.model.Figures.Circle;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;

public class CircleFx extends Circle {
    private final GraphicsContext gc;
    public CircleFx(Point centerPoint, double radius, GraphicsContext gc) {
        super(centerPoint, radius);
        this.gc = gc;
    }

    @Override
    public void draw() {
        double diameter = getRadius() * 2;
        gc.fillOval(centerPoint.getX() - getRadius(), centerPoint.getY() - getRadius(), diameter, diameter);
        gc.strokeOval(centerPoint.getX() - getRadius(), centerPoint.getY() - getRadius(), diameter, diameter);
    }
}
