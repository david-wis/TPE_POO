package backend.model.Changes;

import backend.CanvasState;
import backend.model.ColoredFigure;


public class CreateChange extends Change{
    private final CanvasState canvasState;
    public CreateChange(ColoredFigure figure, CanvasState canvasState) {
        super(figure);
        this.canvasState = canvasState;
    }

    /**
     * Dibuja una figura
     */
    @Override
    public void doChange() {
        canvasState.addFigure(figure);
    }

    /**
     * Borra la figura previamente dibujada
     */
    @Override
    public void undoChange() {
        canvasState.deleteFigure(figure);
    }

    @Override
    public String toString() {
        return String.format("Dibujar %s", figure.getName());
    }
}
