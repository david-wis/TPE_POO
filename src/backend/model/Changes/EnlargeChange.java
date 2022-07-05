package backend.model.Changes;

import backend.model.ColoredFigure;

public class EnlargeChange extends Change {
    public EnlargeChange(ColoredFigure figure) {
        super(figure);
    }

    @Override
    public void doChange() {
        figure.enlarge();
    }

    @Override
    public void undoChange() {
        figure.shrink();
    }

    @Override
    public String toString() {
        return String.format("Agrandar %s", figure.getName());
    }
}
