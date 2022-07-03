package frontend.FxFigures;

import backend.model.Figures.Square;
import backend.model.Point;
import frontend.GraphicsController;
import javafx.scene.canvas.GraphicsContext;

public class SquareFx extends Square {

    private final GraphicsController graphicsController;

    public SquareFx(Point topLeft, double size, GraphicsController graphicsController) {
        super(topLeft, size);
        this.graphicsController = graphicsController;
    }

    @Override
    public void draw(boolean isSelected){
        graphicsController.drawRectangle(topLeft, bottomRight, fillColor, getStrokeColor(isSelected), strokeWeight);
    }

}
