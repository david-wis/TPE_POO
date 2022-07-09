package backend.model.Changes;

import backend.model.ColoredFigure;

/**
 * Encargada de realizar modificaciones sobre las caracteristicas principales de la figura: el color de relleno,
 * el del borde y el grosor del borde.
 */
public abstract class ColorDataChange extends Change {
    private final ColoredFigure.ColorData newColorData;
    private final ColoredFigure.ColorData oldColorData;
    private final String attrib;

    public ColorDataChange(ColoredFigure figure, ColoredFigure.ColorData newColorData, String attrib) {
        super(figure);
        this.attrib = attrib;
        this.newColorData = newColorData;
        oldColorData = figure.getColorData();
    }

    /**
     * Asigna un nuevo ColorData a la figura que recibio
     */
    @Override
    public void doChange() {
        figure.setColorData(newColorData);
    }

    /**
     * Asigna a la figura el ColorData que tenia antes de que se le hubiera aplicado un cambio
     */
    @Override
    public void undoChange() {
        figure.setColorData(oldColorData);
    }

    /**
     * @return Texto con la accion que se realizo sobre una figura en particular
     */
    @Override
    public String toString() {
        return String.format("Cambiar %s de %s", attrib, figure.getName());
    }
}
