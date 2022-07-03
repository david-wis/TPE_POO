package frontend.FxFigures;

import backend.model.Figures.Ellipse;
import backend.model.Point;
import frontend.GraphicsController;
import javafx.scene.canvas.GraphicsContext;

public class EllipseFx extends Ellipse {

    private final GraphicsController graphicsController;
    public EllipseFx(Point centerPoint, double sMayorAxis, double sMinorAxis, GraphicsController graphicsController) {
        super(centerPoint, sMayorAxis, sMinorAxis);
        this.graphicsController = graphicsController;
    }
    public void draw(boolean isSelected) {
        graphicsController.drawEllipse(centerPoint, sMayorAxis, sMinorAxis, fillColor, getStrokeColor(isSelected), strokeWeight);
    }

}
