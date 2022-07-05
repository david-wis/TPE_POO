package backend.model.Changes;

import backend.model.ColoredFigure;

public abstract class Change {
    protected ColoredFigure figure;

    public Change(ColoredFigure figure) {
        this.figure = figure;
    }
    
    public abstract void doChange();
    public abstract void undoChange();
}
