package frontend.Tools;

import backend.model.ColoredFigure;
import backend.model.Point;
import frontend.FxFigures.CircleFx;
import frontend.FxFigures.EllipseFx;
import frontend.FxFigures.RectangleFx;
import frontend.FxFigures.SquareFx;
import frontend.GraphicsController;
import frontend.PaintPane;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class ToolBar {
    private final ToggleButton selectionButton;
    private final ToggleButton deleteButton;
    private final ToggleButton enlargeButton;
    private final ToggleButton shrinkButton;
    private final Slider strokeSlider;
    private final ColorPicker fillColorPicker;
    private final ColorPicker strokeColorPicker;
    private final VBox toolBox;
    private final ToggleGroup tools;
    private final PaintPane pp;

    private final Map<Toggle, BiFunction<Point, Point, ColoredFigure>> creatorMap;

    public ToolBar(GraphicsController gc, PaintPane pp) {
        this.pp = pp;
        selectionButton = new ToggleButton("Seleccionar");
        deleteButton = new ToggleButton("Borrar");
        enlargeButton = new ToggleButton("Agrandar");
        shrinkButton = new ToggleButton("Achicar");
        strokeSlider = new Slider(0, 50, 0);
        strokeSlider.setMajorTickUnit(26);

        fillColorPicker = new ColorPicker();
        strokeColorPicker = new ColorPicker();

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

        Control[] toolsArr = {
                selectionButton,
                rectangleButton,
                circleButton,
                squareButton,
                ellipseButton,
                deleteButton,
                enlargeButton,
                shrinkButton,
                strokeSlider,
                strokeColorPicker,
                fillColorPicker,
        };

        tools = new ToggleGroup();
        for (Control tool : toolsArr) {
            tool.setMinWidth(90);
            tool.setCursor(Cursor.HAND);
        }
        Stream.concat(creatorMap.keySet().stream().map(b -> (ToggleButton) b), Stream.of(selectionButton, deleteButton, shrinkButton, enlargeButton)).forEach(b -> b.setToggleGroup(tools));
        toolBox = new VBox(10);
        toolBox.getChildren().addAll(toolsArr);
        toolBox.setPadding(new Insets(5));
        toolBox.setStyle("-fx-background-color: #999");
        toolBox.setPrefWidth(100); // TODO: Delete magic numbers
        addEvents();
    }

    private void addEvents() {
        strokeColorPicker.setOnAction(event -> {
            pp.getSelectedFigure().ifPresent(figure -> {
                figure.setStrokeColor(strokeColorPicker.getValue().toString());
                pp.redrawCanvas();
            });
        });

        fillColorPicker.setOnAction(event -> {
            pp.getSelectedFigure().ifPresent(figure -> {
                figure.setFillColor(fillColorPicker.getValue().toString());
                pp.redrawCanvas();
            });
        });
    }

    public Optional<ColoredFigure> getSelectedFigure(Point startPoint, Point endPoint) {
        if (tools.getSelectedToggle() != null)
            return Optional.ofNullable(creatorMap.getOrDefault(tools.getSelectedToggle(), (sp, ep) -> null).apply(startPoint, endPoint));
        return Optional.empty();
    }

    public VBox getToolBox() {
        return toolBox;
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

    public void addStrokeSliderHandler(ChangeListener<Number> handler) {
        strokeSlider.valueProperty().addListener(handler);
    }

    public boolean isSelecting() {
        return selectionButton.isSelected();
    }


}
