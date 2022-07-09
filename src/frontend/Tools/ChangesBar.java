package frontend.Tools;

import backend.CanvasState;
import frontend.PaintPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

/**
 * Barra para hacer y deshacer cambios
 */
public class ChangesBar {
    private final Button redoButton;
    private final Button undoButton;
    private final Label actionRedoLabel;
    private final Label actionUndoLabel;
    private final Label qtyRedoChanges;
    private final Label qtyUndoChanges;
    private final HBox changesBox;
    private final PaintPane pp;

    // Constantes
    private static final String UNDO_TEXT = "Deshacer";
    private static final String REDO_TEXT = "Rehacer";
    private static final String ZERO = "0";
    private static final double LABEL_MIN_WIDTH = 300.0;
    private static final double ACTION_BUTTON_WIDTH = 80.0;
    private static final double QTY_LABEL_MIN_WIDTH = 20.0;
    private static final String BACKGROUND_COLOR = "-fx-background-color: #999";
    private static final double SPACE = 5.0;

    public ChangesBar(PaintPane pp){
        this.pp = pp;
        undoButton = new Button(UNDO_TEXT);
        redoButton = new Button(REDO_TEXT);
        actionRedoLabel = new Label();
        actionUndoLabel = new Label();
        qtyRedoChanges = new Label(ZERO);
        qtyUndoChanges = new Label(ZERO);

        Control[] changesList = {
                actionUndoLabel,
                qtyUndoChanges,
                undoButton,
                redoButton,
                qtyRedoChanges,
                actionRedoLabel
        };
        changesBox = new HBox();
        changesBox.getChildren().addAll(changesList);
        setControlStyles();
        undoButton.setDisable(true);
        redoButton.setDisable(true);
        addEvents();
    }

    /**
     * Carga los estilos de los controles
     */
    private void setControlStyles(){
        actionUndoLabel.setMinWidth(LABEL_MIN_WIDTH);
        actionUndoLabel.setAlignment(Pos.CENTER_RIGHT);

        qtyUndoChanges.setMinWidth(QTY_LABEL_MIN_WIDTH);
        qtyUndoChanges.setAlignment(Pos.CENTER);

        undoButton.setMinWidth(ACTION_BUTTON_WIDTH);

        redoButton.setMinWidth(ACTION_BUTTON_WIDTH);

        qtyRedoChanges.setMinWidth(QTY_LABEL_MIN_WIDTH);
        qtyRedoChanges.setAlignment(Pos.CENTER);

        actionRedoLabel.setMinWidth(LABEL_MIN_WIDTH);
        actionRedoLabel.setAlignment(Pos.CENTER_LEFT);

        changesBox.setStyle(BACKGROUND_COLOR);
        changesBox.setSpacing(SPACE);
        changesBox.setAlignment(Pos.CENTER);
        changesBox.setPadding(new Insets(SPACE));
    }

    /**
     * Carga el texto de los labels de cambios
     * @param changeData estructura con informacion sobre cantidad de cambios y sus descripciones
     */
    public void setChangeLabels(CanvasState.ChangeData changeData) {
        actionUndoLabel.setText(changeData.getUndoChange());
        actionRedoLabel.setText(changeData.getRedoChange());
        qtyUndoChanges.setText(Integer.toString(changeData.getUndoQty()));
        qtyRedoChanges.setText(Integer.toString(changeData.getRedoQty()));

        // Desactivar los botones si no hay cambios para atras o para adelante
        undoButton.setDisable(changeData.getUndoQty() == 0);
        redoButton.setDisable(changeData.getRedoQty() == 0);
    }

    /**
     * Agrega los handlers a los botones de rehacer y deshacer
     */
    private void addEvents() {
       redoButton.setOnAction(event -> pp.redoChange());
       undoButton.setOnAction(event -> pp.undoChange());
    }

    /**
     * @return HBox con controles
     */
    public HBox getChangesBox(){
        return changesBox;
    }
}
