package backend.model.Changes.ColorDataChanges;

import backend.model.Changes.ColorDataChange;
import backend.model.ColoredFigure;

/**
 * Uno de los tipos de modificaciones que se puede realizar sobre las figuras: el grosor del borde
 * Recibe una figura y el nuevo grosor del borde
 */
public class StrokeWeightChange extends ColorDataChange {
    private static final String ATTRIBUTE = "grosor de borde";
    public StrokeWeightChange(ColoredFigure figure, ColoredFigure.ColorData newColorData) {
        super(figure, newColorData, ATTRIBUTE);
    }
}