package backend.model.Changes.ColorDataChanges;

import backend.model.Changes.ColorDataChange;
import backend.model.ColoredFigure;

/**
 * Uno de los tipos de modificaciones que se puede realizar sobre las figuras: el relleno
 * Recibe una figura y el nuevo color de relleno
 */
public class FillColorChange extends ColorDataChange {
    private static final String ATTRIBUTE = "color de relleno";
    public FillColorChange(ColoredFigure figure, ColoredFigure.ColorData newColorData) {
        super(figure, newColorData, ATTRIBUTE);
    }
}
