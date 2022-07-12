package backend.model.Figures;

import backend.model.ColoredFigure;
import backend.model.GraphicsController;
import backend.model.Point;


/**
 * Representa un elipse dados su punto central, y ejes mayor y menor
 */
public class Ellipse extends ColoredFigure {
    protected final Point centerPoint;
    protected double sHorizontalAxis, sVerticalAxis;

    public Ellipse(Point centerPoint, double sHorizontalAxis, double sVerticalAxis, ColorData colorData, GraphicsController graphicsController) {
        super(colorData, graphicsController);
        this.centerPoint = centerPoint;
        this.sHorizontalAxis = sHorizontalAxis;
        this.sVerticalAxis = sVerticalAxis;
    }

    @Override
    public String toString() {
        return String.format("%s [Centro: %s, DHorizontal: %.2f, DVertical: %.2f]", getName(), centerPoint, sHorizontalAxis, sVerticalAxis);
    }

    @Override
    public boolean pointBelongs(Point eventPoint) {
        return ((Math.pow(eventPoint.getX() - centerPoint.getX(), 2) / Math.pow(sHorizontalAxis, 2)) +
                (Math.pow(eventPoint.getY() - centerPoint.getY(), 2) / Math.pow(sVerticalAxis, 2))) <= 0.30;
    }

    @Override
    public void move(double dx, double dy) {
        centerPoint.move(dx, dy);
    }

    @Override
    public void resize(double percentage) {
        sHorizontalAxis *= (1 + percentage / 100);
        sVerticalAxis *= (1 + percentage / 100);
    }

    @Override
    public String getName() {
        return "Elipse";
    }

    public void draw(String strokeColor) {
        graphicsController.drawEllipse(centerPoint, sHorizontalAxis, sVerticalAxis, new ColorData(getFillColor(), strokeColor, getStrokeWeight()));
    }
}
