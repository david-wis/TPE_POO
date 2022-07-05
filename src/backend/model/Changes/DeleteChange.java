package backend.model.Changes;

import backend.CanvasState;
import backend.model.ColoredFigure;

public class DeleteChange extends CreateChange {
    public DeleteChange(ColoredFigure figure, CanvasState canvasState) {
        super(figure, canvasState);
    }

    @Override
    public void doChange() {
        super.undoChange();
    }

    @Override
    public void undoChange() {
        super.doChange();
    }

    @Override
    public String toString(){
        return String.format("Borrar %s", figure.getName());
    }
}
