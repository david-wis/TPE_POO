package backend.model.Changes;

import backend.model.ColoredFigure;

/**
 * Cambio que se realiza sobre una figura
 */
public abstract class Change {
    protected ColoredFigure figure;

    /**
     * @param figure Figura afectada por la modificacion
     */
    public Change(ColoredFigure figure) {
        this.figure = figure;
    }

    /**
     * Se encarga de hacer una accion sobre una figura
     */
    public abstract void doChange();

    /**
     * Se encarga de deshacer una accion sobre una figura
     */
    public abstract void undoChange();
}
