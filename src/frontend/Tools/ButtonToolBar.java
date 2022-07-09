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

    // Constantes
    private static final String SELECTION_BUTTON_TEXT = "Seleccionar";
    private static final String DELETE_BUTTON_TEXT = "Borrar";
    private static final String ENLARGE_BUTTON_TEXT = "Agrandar";
    private static final String SHRINK_BUTTON_TEXT = "Achicar";
    private static final String RECTANGLE = "Rectángulo";
    private static final String SQUARE = "Cuadrado";
    private static final String ELLIPSE = "Elipse" ;
    private static final String CIRCLE = "Circulo";
    private static final String BORDER = "Borde";
    private static final String FILL = "Relleno";
    private static final double MIN_WIDTH = 90.0;
    private static final double VBOX_SIZE = 10.0;
    private static final double PADDING = 5.0;
    private static final String BACKGROUND_COLOR = "-fx-background-color: #999";
    private static final double PREF_WIDTH = 100.0;
    private static final String DEFAULT_FILL_COLOR = "#FF0080";
    private static final String DEFAULT_STROKE_COLOR = "#6600A1";
    private static final double MIN_STROKE_WEIGHT = 1.0;
    private static final double MAX_STROKE_WEIGHT = 50.0;
    private static final double DEFAULT_STROKE_WEIGHT = 3.0;


    private final Map<Toggle, BiFunction<Point, Point, ColoredFigure>> creatorMap;

    public ButtonToolBar(GraphicsControllerFx gc, PaintPane pp) {
        this.pp = pp;
        selectionButton = new ToggleButton(SELECTION_BUTTON_TEXT);
        deleteButton = new Button(DELETE_BUTTON_TEXT);
        enlargeButton = new Button(ENLARGE_BUTTON_TEXT);
        shrinkButton = new Button(SHRINK_BUTTON_TEXT);
        strokeSlider = new Slider(MIN_STROKE_WEIGHT, MAX_STROKE_WEIGHT, DEFAULT_STROKE_WEIGHT);
        strokeSlider.setShowTickMarks(true);
        strokeSlider.setShowTickLabels(true);
        fillColorPicker = new ColorPicker();
        strokeColorPicker = new ColorPicker();

        ToggleButton rectangleButton = new ToggleButton(RECTANGLE),
                    squareButton = new ToggleButton(SQUARE),
                    ellipseButton = new ToggleButton(ELLIPSE),
                    circleButton = new ToggleButton(CIRCLE);

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
                    double circleRadius = startPoint.distanceTo(endPoint);
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
                new Label(BORDER),
                strokeSlider,
                strokeColorPicker,
                new Label(FILL),
                fillColorPicker
        };

        tools = new ToggleGroup();
        for (Control tool : toolsArr) {
            tool.setMinWidth(MIN_WIDTH);
            tool.setCursor(Cursor.HAND);
        }

        Stream.concat(creatorMap.keySet().stream().map(b -> (ToggleButton) b), Stream.of(selectionButton)).forEach(b -> b.setToggleGroup(tools));
        toolBox = new VBox(VBOX_SIZE);
        toolBox.getChildren().addAll(toolsArr);
        toolBox.setPadding(new Insets(PADDING));
        toolBox.setStyle(BACKGROUND_COLOR);
        toolBox.setPrefWidth(PREF_WIDTH);
        addEvents();

        fillColorPicker.setValue(Color.web(DEFAULT_FILL_COLOR));
        strokeColorPicker.setValue(Color.web(DEFAULT_STROKE_COLOR));
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
        enlargeButton.setOnAction(event -> pp.onSelectedFigurePresentMandatory(EnlargeChange::new));
        shrinkButton.setOnAction(event -> pp.onSelectedFigurePresentMandatory(ShrinkChange::new));
    }

    public Optional<ColoredFigure> getFigureFromSelectedButton(Point startPoint, Point endPoint) {
        if (tools.getSelectedToggle() != null)
            return Optional.ofNullable(creatorMap.getOrDefault(tools.getSelectedToggle(), (sp, ep) -> null).apply(startPoint, endPoint));
        return Optional.empty();
    }

    public VBox getToolBox() {
        return toolBox;
    }

    public void setColorData(ColoredFigure.ColorData colorData) {
        strokeSlider.setValue(colorData.getStrokeWeight());
        fillColorPicker.setValue(Color.web(colorData.getFillColor()));
        strokeColorPicker.setValue(Color.web(colorData.getStrokeColor()));
    }

    public boolean isSelecting() {
        return selectionButton.isSelected();
    }


}
