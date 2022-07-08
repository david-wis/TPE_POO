package backend.model;

public abstract class DrawableFigure implements Figure {
    protected final GraphicsController graphicsController;

    public DrawableFigure(GraphicsController graphicsController) {
        this.graphicsController = graphicsController;
    }
}
