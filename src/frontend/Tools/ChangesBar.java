package frontend.Tools;

import backend.CanvasState;
import frontend.PaintPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ChangesBar {
    private final Button redoButton;
    private final Button undoButton;
    private final Label actionRedoLabel;
    private final Label actionUndoLabel;
    private final Label qtyRedoChanges;
    private final Label qtyUndoChanges;
    private final HBox changesBox;
    private final PaintPane pp;

    public ChangesBar(PaintPane pp){
        this.pp = pp;
        ToolBar toolBar = new ToolBar();
        undoButton = new Button("Deshacer");
        redoButton = new Button("Rehacer");
        actionRedoLabel = new Label();
        actionUndoLabel = new Label();
        qtyRedoChanges = new Label("0");
        qtyUndoChanges = new Label("0");

        Control[] changesArr = {
                actionUndoLabel,
                qtyUndoChanges,
                undoButton,
                redoButton,
                qtyRedoChanges,
                actionRedoLabel
        };

        toolBar.getItems().addAll(changesArr);
        changesBox = new HBox(toolBar);
        HBox.setHgrow(toolBar, Priority.ALWAYS);
        undoButton.setDisable(true);
        redoButton.setDisable(true);
        addEvents();
    }

    public void setChangeLabels(CanvasState.ChangeData changeData) {
        actionUndoLabel.setText(changeData.getUndoChange());
        actionRedoLabel.setText(changeData.getRedoChange());
        qtyUndoChanges.setText(Integer.toString(changeData.getUndoQty()));
        qtyRedoChanges.setText(Integer.toString(changeData.getRedoQty()));
        undoButton.setDisable(changeData.getUndoQty() == 0);
        redoButton.setDisable(changeData.getRedoQty() == 0);
    }

    private void addEvents() {
       redoButton.setOnAction(event -> pp.redoChange());
       undoButton.setOnAction(event -> pp.undoChange());
    }

    public HBox getChangesBox(){
        return changesBox;
    }
}
