package frontend.FxFigures;

import backend.model.Figures.Circle;
import backend.model.Point;
import frontend.GraphicsController;
import javafx.scene.canvas.GraphicsContext;

public class CircleFx extends Circle {
    private final GraphicsController graphicsController;

    public CircleFx(Point centerPoint, double radius, GraphicsController graphicsController) {
        super(centerPoint, radius);
        this.graphicsController = graphicsController;
    }

    @Override
    public void draw(boolean isSelected) {
        graphicsController.drawEllipse(centerPoint, getRadius(), getRadius(), fillColor, getStrokeColor(isSelected), strokeWeight);
    }
}
