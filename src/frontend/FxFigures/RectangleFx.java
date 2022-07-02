package frontend.FxFigures;

import backend.model.Figures.Rectangle;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;

public class RectangleFx extends Rectangle {

    private final GraphicsContext gc;
    public RectangleFx(Point topLeft, Point bottomRight, GraphicsContext gc) {
        super(topLeft, bottomRight);
        this.gc = gc;
    }


    @Override
    public void draw() {
        gc.fillRect(topLeft.getX(), topLeft.getY(),
                Math.abs(topLeft.getX() - bottomRight.getX()), Math.abs(topLeft.getY() - bottomRight.getY()));
        gc.strokeRect(topLeft.getX(), topLeft.getY(),
                Math.abs(topLeft.getX() - bottomRight.getX()), Math.abs(topLeft.getY() - bottomRight.getY()));
    }
}
