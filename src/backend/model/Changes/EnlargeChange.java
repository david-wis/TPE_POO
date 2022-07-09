package backend.model.Changes;

import backend.model.ColoredFigure;

public class EnlargeChange extends Change {
    public EnlargeChange(ColoredFigure figure) {
        super(figure);
    }

    /**
     * Agranda una figura
     */
    @Override
    public void doChange() {
        figure.enlarge();
    }

    /**
     * Achica la figura previamente agrandada
     */
    @Override
    public void undoChange() {
        figure.inverseEnlarge();
    }

    @Override
    public String toString() {
        return String.format("Agrandar %s", figure.getName());
    }
}
