package frontend.Tools;

import backend.model.Figure;
import backend.model.Point;
import frontend.FxFigures.CircleFx;
import frontend.FxFigures.EllipseFx;
import frontend.FxFigures.RectangleFx;
import frontend.FxFigures.SquareFx;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

public class ToolBar {
    private final ToggleButton selectionButton;
    private final ToggleButton deleteButton;
    private final ToggleButton enlargeButton;
    private final ToggleButton shrinkButton;
    private final VBox buttonsBox;
    private final ToggleGroup tools;

    private final Map<Toggle, BiFunction<Point, Point, Figure>> creatorMap;

    public ToolBar(GraphicsContext gc) {
        selectionButton = new ToggleButton("Seleccionar");
        deleteButton = new ToggleButton("Borrar");
        enlargeButton = new ToggleButton("Agrandar");
        shrinkButton = new ToggleButton("Achicar");

        ToggleButton rectangleButton = new ToggleButton("Rectángulo"),
                    squareButton = new ToggleButton("Cuadrado"),
                    ellipseButton = new ToggleButton("Elipse"),
                    circleButton = new ToggleButton("Circulo");

        creatorMap = Map.of(
                rectangleButton, (startPoint, endPoint) -> new RectangleFx(startPoint, endPoint, gc),
                squareButton , (startPoint, endPoint) -> new SquareFx(startPoint, Math.abs(endPoint.getX() - startPoint.getX()), gc),
                ellipseButton, (startPoint, endPoint) -> {
                    Point centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
                    double sMayorAxis = Math.abs(endPoint.getX() - startPoint.getX());
                    double sMinorAxis = Math.abs(endPoint.getY() - startPoint.getY());
                    return new EllipseFx(centerPoint, sMayorAxis, sMinorAxis, gc);
                },
                circleButton, (startPoint, endPoint) -> {
                    double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
                    return new CircleFx(startPoint, circleRadius, gc);
                }
        );

        ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton, enlargeButton, shrinkButton};

        tools = new ToggleGroup();
        for (ToggleButton tool : toolsArr) {
            tool.setMinWidth(90);
            tool.setToggleGroup(tools);
            tool.setCursor(Cursor.HAND);
        }
        buttonsBox = new VBox(10);
        buttonsBox.getChildren().addAll(toolsArr);
        buttonsBox.setPadding(new Insets(5));
        buttonsBox.setStyle("-fx-background-color: #999");
        buttonsBox.setPrefWidth(100);
    }

    public Optional<Figure> getSelectedFigure(Point startPoint, Point endPoint) {
        if (tools.getSelectedToggle() != null)
            return Optional.ofNullable(creatorMap.getOrDefault(tools.getSelectedToggle(), (sp, ep) -> null).apply(startPoint, endPoint));
        return Optional.empty();
    }

    public VBox getButtonsBox() {
        return buttonsBox;
    }

    public void addShrinkButtonHandler(EventHandler<ActionEvent> handler) {
        shrinkButton.setOnAction(handler);
    }

    public void addEnlargeButtonHandler(EventHandler<ActionEvent> handler) {
        enlargeButton.setOnAction(handler);
    }

    public void addDeleteButtonHandler(EventHandler<ActionEvent> handler) {
        deleteButton.setOnAction(handler);
    }

    public boolean isSelecting() {
        return selectionButton.isSelected();
    }


}
