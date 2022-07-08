package backend.model;

public abstract class Figure implements Movable, Resizable {
    protected final GraphicsController graphicsController;
    public Figure(GraphicsController graphicsController) {
        this.graphicsController = graphicsController;
    }
    public abstract boolean pointBelongs(Point eventPoint);
    public abstract void draw();
    public abstract String getName();
}
