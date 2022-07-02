package frontend.FxFigures;

import backend.model.Figures.Square;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;

public class SquareFx extends Square {

    private final GraphicsContext gc;

    public SquareFx(Point topLeft, double size, GraphicsContext gc) {
        super(topLeft, size);
        this.gc = gc;
    }

    @Override
    public void draw(){
        gc.fillRect(topLeft.getX(), topLeft.getY(),
                Math.abs(topLeft.getX() - bottomRight.getX()), Math.abs(topLeft.getY() - bottomRight.getY()));
        gc.strokeRect(topLeft.getX(), topLeft.getY(),
                Math.abs(topLeft.getX() - bottomRight.getX()), Math.abs(topLeft.getY() - bottomRight.getY()));
    }

}
