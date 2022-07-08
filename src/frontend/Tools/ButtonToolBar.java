package frontend.Tools;

import backend.model.Changes.*;
import backend.model.Changes.ColorDataChanges.FillColorChange;
import backend.model.Changes.ColorDataChanges.StrokeColorChange;
import backend.model.Changes.ColorDataChanges.StrokeWeightChange;
import backend.model.ColoredFigure;
import backend.model.Figures.Circle;
import backend.model.Figures.Ellipse;
import backend.model.Figures.Rectangle;
import backend.model.Figures.Square;
import backend.model.Point;
import frontend.GraphicsControllerFx;
import frontend.PaintPane;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class ButtonToolBar {
    private final ToggleButton selectionButton;
    private final Button deleteButton;
    private final Button enlargeButton;
    private final Button shrinkButton;
    private final Slider strokeSlider;
    private final ColorPicker fillColorPicker;
    private final ColorPicker strokeColorPicker;
    private final VBox toolBox;
    private final ToggleGroup tools;
    private final PaintPane pp;

    private static final String DEFAULT_FILL_COLOR = "#FF0080";
    private static final String DEFAULT_STROKE_COLOR = "#6600A1";
    private static final double DEFAULT_STROKE_WEIGHT = 3;

    private final Map<Toggle, BiFunction<Point, Point, ColoredFigure>> creatorMap;

    public ButtonToolBar(GraphicsControllerFx gc, PaintPane pp) {
        this.pp = pp;
        selectionButton = new ToggleButton("Seleccionar");
        deleteButton = new Button("Borrar");
        enlargeButton = new Button("Agrandar");
        shrinkButton = new Button("Achicar");
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
                rectangleButton, (startPoint, endPoint) -> new Rectangle(startPoint, endPoint, getCurrentColor(), gc),
                squareButton , (startPoint, endPoint) -> new Square(startPoint, Math.abs(endPoint.getX() - startPoint.getX()), getCurrentColor(), gc),
                ellipseButton, (startPoint, endPoint) -> {
                    Point centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
                    double sMayorAxis = Math.abs(endPoint.getX() - startPoint.getX());
                    double sMinorAxis = Math.abs(endPoint.getY() - startPoint.getY());
                    return new Ellipse(centerPoint, sMayorAxis, sMinorAxis, getCurrentColor(), gc);
                },
                circleButton, (startPoint, endPoint) -> {
                    double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
                    return new Circle(startPoint, circleRadius, getCurrentColor() , gc);
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

        Stream.concat(creatorMap.keySet().stream().map(b -> (ToggleButton) b), Stream.of(selectionButton)).forEach(b -> b.setToggleGroup(tools));
        toolBox = new VBox(10);
        toolBox.getChildren().addAll(toolsArr);
        toolBox.setPadding(new Insets(5));
        toolBox.setStyle("-fx-background-color: #999");
        toolBox.setPrefWidth(100); // TODO: Delete magic numbers
        addEvents();

        fillColorPicker.setValue(Color.web(DEFAULT_FILL_COLOR));
        strokeColorPicker.setValue(Color.web(DEFAULT_STROKE_COLOR));
        strokeSlider.setValue(DEFAULT_STROKE_WEIGHT);
        strokeSlider.addEventFilter(KeyEvent.KEY_PRESSED, Event::consume);
    }
    
    public ColoredFigure.ColorData getCurrentColor() {
        return new ColoredFigure.ColorData(fillColorPicker.getValue().toString(), strokeColorPicker.getValue().toString(), strokeSlider.getValue());
    }

    private void addEvents() {
        strokeColorPicker.setOnAction(event -> pp.onSelectedFigurePresent(figure -> new StrokeColorChange(figure, getCurrentColor())));
        fillColorPicker.setOnAction(event -> pp.onSelectedFigurePresent(figure -> new FillColorChange(figure, getCurrentColor())));
        strokeSlider.setOnMouseReleased(event -> pp.onSelectedFigurePresent(figure -> new StrokeWeightChange(figure, getCurrentColor())));
        deleteButton.setOnAction(event -> pp.deleteSelectedFigure());
        enlargeButton.setOnAction(event -> pp.onSelectedFigurePresent(EnlargeChange::new));
        shrinkButton.setOnAction(event -> pp.onSelectedFigurePresent(ShrinkChange::new));
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
