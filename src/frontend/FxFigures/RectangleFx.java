package frontend.FxFigures;

import backend.model.Figures.Rectangle;
import backend.model.Point;
import frontend.GraphicsController;

public class RectangleFx extends Rectangle {

    private final GraphicsController graphicsController;

    public RectangleFx(Point topLeft, Point bottomRight, ColorData colorData, GraphicsController graphicsController) {
        super(topLeft, bottomRight, colorData);
        this.graphicsController = graphicsController;
    }

    @Override
    public void draw(boolean isSelected) {
        graphicsController.drawRectangle(topLeft, bottomRight, getFillColor(), getStrokeColor(isSelected), getStrokeWeight());
    }
}
