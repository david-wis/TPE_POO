package frontend;

import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GraphicsController {
    private final GraphicsContext gc;
    private final static int DEFAULT_LINE_WIDTH = 1;

    public GraphicsController(GraphicsContext gc) {
        this.gc = gc;
        gc.setLineWidth(DEFAULT_LINE_WIDTH);
    }

    public GraphicsContext getContext() {
        return gc;
    }

    public void clear(double width, double height) {
        gc.clearRect(0, 0, width, height);
    }

    private void setColors(String fillColor, String strokeColor, double strokeWeight){
        gc.setStroke(Color.web(strokeColor));
        gc.setLineWidth(strokeWeight);
        gc.setFill(Color.web(fillColor));
    }

    public void drawRectangle(Point topLeft, Point bottomRight, String fillColor, String strokeColor, double strokeWeight) {
        setColors(fillColor, strokeColor, strokeWeight);
        gc.fillRect(topLeft.getX(), topLeft.getY(),
                Math.abs(topLeft.getX() - bottomRight.getX()), Math.abs(topLeft.getY() - bottomRight.getY()));
        gc.strokeRect(topLeft.getX(), topLeft.getY(),
                Math.abs(topLeft.getX() - bottomRight.getX()), Math.abs(topLeft.getY() - bottomRight.getY()));
    }

    public void drawEllipse(Point centerPoint, double sMayorAxis, double sMinorAxis, String fillColor, String strokeColor, double strokeWeight) {
        setColors(fillColor, strokeColor, strokeWeight);
        gc.strokeOval(centerPoint.getX() - (sMayorAxis / 2), centerPoint.getY() - (sMinorAxis / 2), sMayorAxis, sMinorAxis);
        gc.fillOval(centerPoint.getX() - (sMayorAxis / 2), centerPoint.getY() - (sMinorAxis / 2), sMayorAxis, sMinorAxis);
    }
}
