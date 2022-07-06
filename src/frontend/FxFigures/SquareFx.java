package frontend.FxFigures;

import backend.model.Figures.Square;
import backend.model.Point;
import frontend.GraphicsController;

public class SquareFx extends Square {

    private final GraphicsController graphicsController;

    public SquareFx(Point topLeft, double size, ColorData colorData, GraphicsController graphicsController) {
        super(topLeft, size, colorData);
        this.graphicsController = graphicsController;
    }

    @Override
    public void draw(boolean isSelected){
        graphicsController.drawRectangle(topLeft, bottomRight, getFillColor(), getStrokeColor(isSelected), getStrokeWeight());
    }

}
