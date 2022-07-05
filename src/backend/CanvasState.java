package backend;

import backend.model.Changes.Change;
import backend.model.ColoredFigure;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class CanvasState {

    private final List<ColoredFigure> list = new ArrayList<>();

    private final Deque<Change> backwardChangesStack = new LinkedList<>(); // Stack con cambios a deshacer

    private final Deque<Change> forwardChangesStack = new LinkedList<>(); // Stack con cambios a rehacer

    public void addFigure(ColoredFigure figure) {
        list.add(figure);
    }

    public void deleteFigure(ColoredFigure figure) {
        list.remove(figure);
    }

    public Iterable<ColoredFigure> figures() {
        return new ArrayList<>(list);
    }

    public void addChange(Change change) {
        backwardChangesStack.push(change);
        forwardChangesStack.clear();
        change.doChange();
    }

    public void undoChange() {
        if (!backwardChangesStack.isEmpty()) {
            Change change = backwardChangesStack.pop();
            forwardChangesStack.push(change);
            change.undoChange();
        }
    }

    public void redoChange() {
        if (!forwardChangesStack.isEmpty()) {
            Change change = forwardChangesStack.pop();
            backwardChangesStack.push(change);
            change.doChange();
        }
    }

    public ChangeData getChangeData() {
        String  undoChange = backwardChangesStack.isEmpty() ? "" : backwardChangesStack.peek().toString(),
                redoChange = forwardChangesStack.isEmpty() ? "" : forwardChangesStack.peek().toString();
        return new ChangeData(backwardChangesStack.size(), forwardChangesStack.size(), undoChange, redoChange);
    }

    public static class ChangeData {
        private final int redoQty, undoQty;
        private final String undoChange, redoChange;

        public ChangeData(int undoQty, int redoQty, String undoChange, String redoChange) {
            this.undoQty = undoQty;
            this.redoQty = redoQty;
            this.undoChange = undoChange;
            this.redoChange = redoChange;
        }

        public int getRedoQty() {
            return redoQty;
        }

        public int getUndoQty() {
            return undoQty;
        }

        public String getUndoChange() {
            return undoChange;
        }

        public String getRedoChange() {
            return redoChange;
        }
    }

}
