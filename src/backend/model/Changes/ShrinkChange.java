package backend.model.Changes;

import backend.model.ColoredFigure;

public class ShrinkChange extends Change {

    public ShrinkChange(ColoredFigure figure) {
        super(figure);
    }

    @Override
    public void doChange() {
        figure.shrink();
    }

    @Override
    public void undoChange() {
        figure.inverseShrink();
    }

    @Override
    public String toString() {
        return String.format("Achicar %s", figure.getName());
    }

}
