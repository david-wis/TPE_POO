package frontend.FxFigures;

import backend.model.Figures.Ellipse;
import backend.model.Point;
import frontend.GraphicsController;

public class EllipseFx extends Ellipse {

    private final GraphicsController graphicsController;
    public EllipseFx(Point centerPoint, double sMayorAxis, double sMinorAxis, ColorData colorData, GraphicsController graphicsController) {
        super(centerPoint, sMayorAxis, sMinorAxis, colorData);
        this.graphicsController = graphicsController;
    }
    public void draw(boolean isSelected) {
        graphicsController.drawEllipse(centerPoint, sMayorAxis, sMinorAxis, getFillColor(), getStrokeColor(isSelected), getStrokeWeight());
    }

}
