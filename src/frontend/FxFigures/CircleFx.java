package frontend.FxFigures;

import backend.model.Figures.Circle;
import backend.model.Point;
import frontend.GraphicsController;

public class CircleFx extends Circle {
    private final GraphicsController graphicsController;

    public CircleFx(Point centerPoint, double radius, ColorData colorData, GraphicsController graphicsController) {
        super(centerPoint, radius, colorData);
        this.graphicsController = graphicsController;
    }

    @Override
    public void draw(boolean isSelected) {
        graphicsController.drawCircle(centerPoint, getRadius(), getFillColor(), getStrokeColor(isSelected), getStrokeWeight());
    }
}
