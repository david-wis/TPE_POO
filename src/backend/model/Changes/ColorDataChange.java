package backend.model.Changes;

import backend.model.ColoredFigure;

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

    @Override
    public void doChange() {
        figure.setColorData(newColorData);
    }

    @Override
    public void undoChange() {
        figure.setColorData(oldColorData);
    }

    @Override
    public String toString() {
        return String.format("Cambiar %s de %s", attrib, figure.getName());
    }
}
