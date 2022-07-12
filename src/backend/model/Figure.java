package backend.model;

/**
 * Figura
 */
public abstract class Figure implements Movable, Resizable {
    protected final GraphicsController graphicsController;
    public Figure(GraphicsController graphicsController) {
        this.graphicsController = graphicsController;
    }

    /**
     * @param eventPoint Punto objetivo
     * @return Si el punto se encuentra dentro de la figura
     */
    public abstract boolean pointBelongs(Point eventPoint);

    /**
     * Dibuja la figura
     */
    public abstract void draw();

    /**
     * @return Tipo de figura
     */
    public abstract String getName();
}
