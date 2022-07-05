package frontend.Tools;

import backend.model.ColoredFigure;
import backend.model.Point;
import backend.model.Resizable;
import frontend.FxFigures.CircleFx;
import frontend.FxFigures.EllipseFx;
import frontend.FxFigures.RectangleFx;
import frontend.FxFigures.SquareFx;
import frontend.GraphicsController;
import frontend.PaintPane;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
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

    private static final String DEFAULT_FILL_COLOR = "#FF0080";
    private static final String DEFAULT_STROKE_COLOR = "#6600A1";
    private static final double DEFAULT_STROKE_WEIGHT = 1;

    private final Map<Toggle, BiFunction<Point, Point, ColoredFigure>> creatorMap;

    public ToolBar(GraphicsController gc, PaintPane pp) {
        this.pp = pp;
        selectionButton = new ToggleButton("Seleccionar");
        deleteButton = new ToggleButton("Borrar");
        enlargeButton = new ToggleButton("Agrandar");
        shrinkButton = new ToggleButton("Achicar");
        strokeSlider = new Slider(1, 50, 0);
        strokeSlider.setShowTickMarks(true);
        strokeSlider.setShowTickLabels(true);
        fillColorPicker = new ColorPicker();
        strokeColorPicker = new ColorPicker();

        ToggleButton rectangleButton = new ToggleButton("Rectángulo"),
                    squareButton = new ToggleButton("Cuadrado"),
                    ellipseButton = new ToggleButton("Elipse"),
                    circleButton = new ToggleButton("Circulo");

        creatorMap = Map.of(
                rectangleButton, (startPoint, endPoint) -> new RectangleFx(startPoint, endPoint, getCurrentColor() ,gc),
                squareButton , (startPoint, endPoint) -> new SquareFx(startPoint, Math.abs(endPoint.getX() - startPoint.getX()), getCurrentColor(), gc),
                ellipseButton, (startPoint, endPoint) -> {
                    Point centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
                    double sMayorAxis = Math.abs(endPoint.getX() - startPoint.getX());
                    double sMinorAxis = Math.abs(endPoint.getY() - startPoint.getY());
                    return new EllipseFx(centerPoint, sMayorAxis, sMinorAxis, getCurrentColor(), gc);
                },
                circleButton, (startPoint, endPoint) -> {
                    double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
                    return new CircleFx(startPoint, circleRadius, getCurrentColor() , gc);
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
                new Label("Borde"),
                strokeSlider,
                strokeColorPicker,
                new Label("Relleno"),
                fillColorPicker
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

        fillColorPicker.setValue(Color.web(DEFAULT_FILL_COLOR));
        strokeColorPicker.setValue(Color.web(DEFAULT_STROKE_COLOR));
        strokeSlider.setValue(DEFAULT_STROKE_WEIGHT);
    }
    
    private ColoredFigure.ColorData getCurrentColor() {
        return new ColoredFigure.ColorData(fillColorPicker.getValue().toString(), strokeColorPicker.getValue().toString(), strokeSlider.getValue());
    }

    private void addEvents() {
        strokeColorPicker.setOnAction(event -> onSelectedFigurePresent(figure -> figure.setStrokeColor(strokeColorPicker.getValue().toString())));
        fillColorPicker.setOnAction(event -> onSelectedFigurePresent(figure -> figure.setFillColor(fillColorPicker.getValue().toString())));
        deleteButton.setOnAction(event -> onSelectedFigurePresent(figure -> pp.deleteSelectedFigure()));
        enlargeButton.setOnAction(event -> onSelectedFigurePresent(Resizable::enlarge));
        shrinkButton.setOnAction(event -> onSelectedFigurePresent(Resizable::shrink));
        strokeSlider.valueProperty().addListener((observable, oldValue, newValue) -> onSelectedFigurePresent((figure) -> figure.setStrokeWeight(newValue.doubleValue())));
    }

    private void onSelectedFigurePresent(Consumer<ColoredFigure> figureConsumer) {
        pp.getSelectedFigure().ifPresent((figure) -> {
            figureConsumer.accept(figure);
            pp.redrawCanvas();
        });
    }

    public Optional<ColoredFigure> getFigureFromSelectedButton(Point startPoint, Point endPoint) {
        if (tools.getSelectedToggle() != null)
            return Optional.ofNullable(creatorMap.getOrDefault(tools.getSelectedToggle(), (sp, ep) -> null).apply(startPoint, endPoint));
        return Optional.empty();
    }

    public VBox getToolBox() {
        return toolBox;
    }

    public void setFigureData(ColoredFigure figure) {
        strokeSlider.setValue(figure.getStrokeWeight());
        fillColorPicker.setValue(Color.web(figure.getFillColor()));
        strokeColorPicker.setValue(Color.web(figure.getStrokeColor(false)));
    }

    public boolean isSelecting() {
        return selectionButton.isSelected();
    }


}
