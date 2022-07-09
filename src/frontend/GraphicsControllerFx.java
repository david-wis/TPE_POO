package frontend;

import backend.model.ColoredFigure;
import backend.model.GraphicsController;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GraphicsControllerFx extends GraphicsController {
    private final GraphicsContext gc;
    private final static int DEFAULT_LINE_WIDTH = 1;

    public GraphicsControllerFx(GraphicsContext gc) {
        this.gc = gc;
        gc.setLineWidth(DEFAULT_LINE_WIDTH);
    }

    public void clear(double width, double height) {
        gc.clearRect(0, 0, width, height);
    }

    private void setColors(ColoredFigure.ColorData colorData){
        gc.setStroke(Color.web(colorData.getStrokeColor()));
        gc.setLineWidth(colorData.getStrokeWeight());
        gc.setFill(Color.web(colorData.getFillColor()));
    }

    @Override
    public void drawRectangle(Point topLeft, Point bottomRight, ColoredFigure.ColorData colorData) {
        setColors(colorData);
        gc.fillRect(topLeft.getX(), topLeft.getY(),
                Math.abs(topLeft.getX() - bottomRight.getX()), Math.abs(topLeft.getY() - bottomRight.getY()));
        gc.strokeRect(topLeft.getX(), topLeft.getY(),
                Math.abs(topLeft.getX() - bottomRight.getX()), Math.abs(topLeft.getY() - bottomRight.getY()));
    }

    @Override
    public void drawEllipse(Point centerPoint, double sMayorAxis, double sMinorAxis, ColoredFigure.ColorData colorData) {
        setColors(colorData);
        gc.strokeOval(centerPoint.getX() - (sMayorAxis / 2), centerPoint.getY() - (sMinorAxis / 2), sMayorAxis, sMinorAxis);
        gc.fillOval(centerPoint.getX() - (sMayorAxis / 2), centerPoint.getY() - (sMinorAxis / 2), sMayorAxis, sMinorAxis);
    }

    @Override
    public void drawCircle(Point centerPoint, double radius, ColoredFigure.ColorData colorData) {
        drawEllipse(centerPoint, 2 * radius, 2 * radius, colorData);
    }
}
