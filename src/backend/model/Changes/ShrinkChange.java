package backend.model.Changes;

import backend.model.ColoredFigure;

public class ShrinkChange extends EnlargeChange {

    public ShrinkChange(ColoredFigure figure) {
        super(figure);
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
    public String toString() {
        return String.format("Achicar %s", figure.getName());
    }

}
