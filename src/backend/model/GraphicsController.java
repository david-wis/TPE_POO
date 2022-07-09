package backend.model;

public interface GraphicsController {
    void drawRectangle(Point topLeft, Point bottomRight, ColoredFigure.ColorData colorData);
    void drawEllipse(Point centerPoint, double sMayorAxis, double sMinorAxis, ColoredFigure.ColorData colorData);
    void drawCircle(Point centerPoint, double radius, ColoredFigure.ColorData colorData);
}