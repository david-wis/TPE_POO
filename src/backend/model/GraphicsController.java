package backend.model;

public abstract class GraphicsController {
    public abstract void drawRectangle(Point topLeft, Point bottomRight, ColoredFigure.ColorData colorData);
    public abstract void drawEllipse(Point centerPoint, double sMayorAxis, double sMinorAxis, ColoredFigure.ColorData colorData);
    public abstract void drawCircle(Point centerPoint, double radius, ColoredFigure.ColorData colorData);

}