package backend.model.Changes;

import backend.CanvasState;
import backend.model.ColoredFigure;

public class CreateChange extends Change{
    private final CanvasState canvasState;
    public CreateChange(ColoredFigure figure, CanvasState canvasState) {
        super(figure);
        this.canvasState = canvasState;
    }

    @Override
    public void doChange() {
        canvasState.addFigure(figure);
    }

    @Override
    public void undoChange() {
        canvasState.deleteFigure(figure);
    }

    @Override
    public String toString() {
        return String.format("Dibujar %s", figure.getName());
    }
}
