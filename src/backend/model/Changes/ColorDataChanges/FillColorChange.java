package backend.model.Changes.ColorDataChanges;

import backend.model.Changes.ColorDataChange;
import backend.model.ColoredFigure;

public class FillColorChange extends ColorDataChange {
    private static final String ATTRIBUTE = "color de relleno";
    public FillColorChange(ColoredFigure figure, ColoredFigure.ColorData newColorData) {
        super(figure, newColorData, ATTRIBUTE);
    }
}
