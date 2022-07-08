package backend.model.Figures;

import backend.model.ColoredFigure;
import backend.model.GraphicsController;
import backend.model.Point;


public class Ellipse extends ColoredFigure {
    protected final Point centerPoint;
    protected double sMayorAxis, sMinorAxis;

    public Ellipse(Point centerPoint, double sMayorAxis, double sMinorAxis, ColorData colorData, GraphicsController graphicsController) {
        super(colorData, graphicsController);
        this.centerPoint = centerPoint;
        this.sMayorAxis = sMayorAxis;
        this.sMinorAxis = sMinorAxis;
    }

    @Override
    public String toString() {
        return String.format("%s [Centro: %s, DMayor: %.2f, DMenor: %.2f]", getName(), centerPoint, sMayorAxis, sMinorAxis);
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getsMayorAxis() {
        return sMayorAxis;
    }

    public double getsMinorAxis() {
        return sMinorAxis;
    }

    @Override
    public boolean pointBelongs(Point eventPoint) {
        return ((Math.pow(eventPoint.getX() - centerPoint.getX(), 2) / Math.pow(sMayorAxis, 2)) +
                (Math.pow(eventPoint.getY() - centerPoint.getY(), 2) / Math.pow(sMinorAxis, 2))) <= 0.30;
    }

    @Override
    public void move(double dx, double dy) {
        centerPoint.move(dx, dy);
    }

    @Override
    public void resize(double percentage) {
        sMayorAxis *= (1 + percentage/100);
        sMinorAxis *= (1 + percentage/100);
    }

    @Override
    public String getName() {
        return "Elipse";
    }

    public void draw(boolean isSelected) {
        graphicsController.drawEllipse(centerPoint, sMayorAxis, sMinorAxis, new ColorData(getFillColor(), getStrokeColor(isSelected), getStrokeWeight()));
    }
}
