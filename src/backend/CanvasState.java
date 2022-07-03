package backend;

import backend.model.ColoredFigure;
import backend.model.Figure;

import java.util.ArrayList;
import java.util.List;

public class CanvasState {

    private final List<ColoredFigure> list = new ArrayList<>();

    public void addFigure(ColoredFigure figure) {
        list.add(figure);
    }

    public void deleteFigure(ColoredFigure figure) {
        list.remove(figure);
    }

    public Iterable<ColoredFigure> figures() {
        return new ArrayList<>(list);
    }

}
