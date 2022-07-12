package backend.model.Changes.ColorDataChanges;

import backend.model.Changes.ColorDataChange;
import backend.model.ColoredFigure;

/**
 * Uno de los tipos de modificaciones que se puede realizar sobre las figuras: el color del borde
 * Recibe una figura y el nuevo color del borde
 */
public class StrokeColorChange extends ColorDataChange {
    private static final String ATTRIBUTE = "color de borde";
    public StrokeColorChange(ColoredFigure figure, ColoredFigure.ColorData newColorData) {
        super(figure, newColorData, ATTRIBUTE);
    }
}