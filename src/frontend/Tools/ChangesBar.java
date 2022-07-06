package frontend.Tools;

import backend.CanvasState;
import frontend.PaintPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

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
        undoButton = new Button("Deshacer");
        redoButton = new Button("Rehacer");
        actionRedoLabel = new Label();
        actionUndoLabel = new Label();
        qtyRedoChanges = new Label("0");
        qtyUndoChanges = new Label("0");

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

    private void setControlStyles(){

        actionUndoLabel.setMinWidth(300);
        actionUndoLabel.setAlignment(Pos.CENTER_RIGHT);

        qtyUndoChanges.setMinWidth(20);
        qtyUndoChanges.setAlignment(Pos.CENTER);

        undoButton.setMinWidth(80);

        redoButton.setMinWidth(80);

        qtyRedoChanges.setMinWidth(20);
        qtyRedoChanges.setAlignment(Pos.CENTER);

        actionRedoLabel.setMinWidth(300);
        actionRedoLabel.setAlignment(Pos.CENTER_LEFT);

        changesBox.setStyle("-fx-background-color: #999");
        changesBox.setSpacing(5);
        changesBox.setAlignment(Pos.CENTER);
        changesBox.setPadding(new Insets(5));
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
