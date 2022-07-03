package frontend.FxFigures;

import backend.model.Figures.Rectangle;
import backend.model.Point;
import frontend.GraphicsController;
import javafx.scene.canvas.GraphicsContext;

public class RectangleFx extends Rectangle {

    private final GraphicsController graphicsController;

    public RectangleFx(Point topLeft, Point bottomRight, GraphicsController graphicsController) {
        super(topLeft, bottomRight);
        this.graphicsController = graphicsController;
    }

    @Override
    public void draw(boolean isSelected) {
        graphicsController.drawRectangle(topLeft,  bottomRight,fillColor, getStrokeColor(isSelected), strokeWeight);
    }
}
