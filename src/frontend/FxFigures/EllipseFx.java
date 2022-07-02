package frontend.FxFigures;

import backend.model.Figures.Ellipse;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;

public class EllipseFx extends Ellipse {
    private final GraphicsContext gc;
    public EllipseFx(Point centerPoint, double sMayorAxis, double sMinorAxis, GraphicsContext gc) {
        super(centerPoint, sMayorAxis, sMinorAxis);
        this.gc = gc;
    }
    public void draw() {
        gc.strokeOval(centerPoint.getX() - (sMayorAxis / 2), centerPoint.getY() - (sMinorAxis / 2), sMayorAxis, sMinorAxis);
        gc.fillOval(centerPoint.getX() - (sMayorAxis / 2), centerPoint.getY() - (sMinorAxis / 2), sMayorAxis, sMinorAxis);
    }

}
