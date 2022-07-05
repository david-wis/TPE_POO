package backend.model.Changes.ColorDataChanges;

import backend.model.Changes.ColorDataChange;
import backend.model.ColoredFigure;

public class StrokeWeightChange extends ColorDataChange {
    private static final String ATTRIBUTE = "grosor de borde";
    public StrokeWeightChange(ColoredFigure figure, ColoredFigure.ColorData newColorData) {
        super(figure, newColorData, ATTRIBUTE);
    }
}