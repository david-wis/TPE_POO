package backend.model.Changes.ColorDataChanges;

import backend.model.Changes.ColorDataChange;
import backend.model.ColoredFigure;

public class StrokeColorChange extends ColorDataChange {
    private static final String ATTRIBUTE = "color de borde";
    public StrokeColorChange(ColoredFigure figure, ColoredFigure.ColorData newColorData) {
        super(figure, newColorData, ATTRIBUTE);
    }
}