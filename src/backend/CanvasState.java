package backend;

import backend.model.Changes.Change;
import backend.model.ColoredFigure;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class CanvasState {

    //Lista con todas las figuras que fueron alguna vez creadas sobre el lienzo
    private final List<ColoredFigure> list = new ArrayList<>();

    // Stack con cambios a deshacer
    private final Deque<Change> backwardChangesStack = new LinkedList<>();

    // Stack con cambios a rehacer
    private final Deque<Change> forwardChangesStack = new LinkedList<>();

    /**
     * Agrega una figura a la lista de figuras
     * @param figure Figura a agregar
     */
    public void addFigure(ColoredFigure figure) {
        list.add(figure);
    }


    /**
     * Elimina una figura de la lista de figuras
     * @param figure Figura a eliminar
     */
    public void deleteFigure(ColoredFigure figure) {
        list.remove(figure);
    }

    /**
     * @return Iterable de las figuras de la lista
     */
    public Iterable<ColoredFigure> figures() {
        return new ArrayList<>(list);
    }

    /**
     * Agrega un cambio a la pila de acciones para deshacer y se limpia la pila de acciones para rehacer
     * @param change Cambio a agregar
     */
    public void addChange(Change change) {
        backwardChangesStack.push(change);
        forwardChangesStack.clear();
        change.doChange();
    }

    /**
     * Si la pila de acciones para deshacer no está vacia, obtiene el ultimo cambio y lo
     * agrega a la pila de acciones para rehacer. Luego, ejecuta esta ultima accion.
     */
    public void undoChange() {
        if (!backwardChangesStack.isEmpty()) {
            Change change = backwardChangesStack.pop();
            forwardChangesStack.push(change);
            change.undoChange();
        }
    }

    /**
     * Si la pila de acciones para rehacer no está vacia, obtiene el ultimo cambio y lo
     * agrega a la pila de acciones para deshacer. Luego, ejecuta esta ultima accion.
     */
    public void redoChange() {
        if (!forwardChangesStack.isEmpty()) {
            Change change = forwardChangesStack.pop();
            backwardChangesStack.push(change);
            change.doChange();
        }
    }


    /**
     * @return ChangeData con la cantidad de acciones que se pueden deshacer y rehacer,
     * y los textos con las ultimas acciones que se pueden rehacer y deshacer
     *
     */
    public ChangeData getChangeData() {
        // Si la pila de acciones para deshacer esta no esta vacia, crea el texto de la ultima accion a deshacer
        // Si la pila de acciones para rehacer esta no esta vacia, crea el texto de la ultima accion a rehacer

        String  undoChange = backwardChangesStack.isEmpty() ? "" : backwardChangesStack.peek().toString(),
                redoChange = forwardChangesStack.isEmpty() ? "" : forwardChangesStack.peek().toString();
        return new ChangeData(backwardChangesStack.size(), forwardChangesStack.size(), undoChange, redoChange);
    }

    /**
     * Guarda la informacion sobre los cambios disponibles y sus descripciones
     */
    public static class ChangeData {
        private final int redoQty, undoQty;
        private final String undoChange, redoChange;

        public ChangeData(int undoQty, int redoQty, String undoChange, String redoChange) {
            this.undoQty = undoQty;
            this.redoQty = redoQty;
            this.undoChange = undoChange;
            this.redoChange = redoChange;
        }

        /**
         * @return Cantidad de acciones que se pueden rehacer
         */
        public int getRedoQty() {
            return redoQty;
        }

        /**
         * @return Cantidad de acciones que se pueden deshacer
         */
        public int getUndoQty() {
            return undoQty;
        }

        /**
         * @return La ultima accion para deshacer
         */
        public String getUndoChange() {
            return undoChange;
        }

        /**
         * @return La ultima accion para rehacer
         */
        public String getRedoChange() {
            return redoChange;
        }
    }

}
