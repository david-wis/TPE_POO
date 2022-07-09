package frontend;

import backend.model.ColoredFigure;
import backend.model.GraphicsController;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


/**
 * Implementacion de GraphicsController para JavaFX
 */
public class GraphicsControllerFx implements GraphicsController {
    private final GraphicsContext gc;
    private final static int DEFAULT_LINE_WIDTH = 1;

    public GraphicsControllerFx(GraphicsContext gc) {
        this.gc = gc;
        gc.setLineWidth(DEFAULT_LINE_WIDTH);
    }

    /**
     * Limpia el canvas
     * @param width Ancho del canvas
     * @param height Alto del canvas
     */
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
    public void drawEllipse(Point centerPoint, double sHorizontalAxis, double sVerticalAxis, ColoredFigure.ColorData colorData) {
        setColors(colorData);
        gc.strokeOval(centerPoint.getX() - (sHorizontalAxis / 2), centerPoint.getY() - (sVerticalAxis / 2), sHorizontalAxis, sVerticalAxis);
        gc.fillOval(centerPoint.getX() - (sHorizontalAxis / 2), centerPoint.getY() - (sVerticalAxis / 2), sHorizontalAxis, sVerticalAxis);
    }

    @Override
    public void drawCircle(Point centerPoint, double radius, ColoredFigure.ColorData colorData) {
        drawEllipse(centerPoint, 2 * radius, 2 * radius, colorData);
    }
}
