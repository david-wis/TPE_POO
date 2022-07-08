package frontend;

import backend.CanvasState;
import backend.model.*;
import backend.model.Changes.Change;
import backend.model.Changes.CreateChange;
import backend.model.Changes.DeleteChange;
import frontend.Tools.ButtonToolBar;
import frontend.Tools.ChangesBar;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.StreamSupport;

public class PaintPane extends BorderPane {

	// BackEnd
	private final CanvasState canvasState;

	// Canvas y relacionados
	private final Canvas canvas = new Canvas(800, 600);

	private final GraphicsControllerFx gc = new GraphicsControllerFx(canvas.getGraphicsContext2D());

	// Dibujar una figura
	private Point startPoint;

	// Seleccionar una figura
	private ColoredFigure selectedFigure;

	// StatusBar
	private final StatusPane statusPane;

	private final ButtonToolBar tb;
	private final ChangesBar cb;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		tb = new ButtonToolBar(gc, this);
		cb = new ChangesBar(this);

		canvas.setOnMousePressed(event -> startPoint = new Point(event.getX(), event.getY()));

		canvas.setOnMouseReleased(event -> {
			Point endPoint = new Point(event.getX(), event.getY());
			Optional.ofNullable(startPoint).ifPresent(sp -> {
				if (endPoint.getX() >= startPoint.getX() && endPoint.getY() >= startPoint.getY())	 {
					tb.getFigureFromSelectedButton(startPoint, endPoint).ifPresent(figure -> canvasState.addChange(new CreateChange(figure, canvasState)));
					startPoint = null;
					redrawCanvas();
				}
			});
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			onLastFoundFigure(new Point(event.getX(), event.getY()), "", (figure) -> {}, () -> statusPane.updateStatus(eventPoint.toString()));
		});

		canvas.setOnMouseClicked(event -> {
			if(tb.isSelecting()) {
				onLastFoundFigure(new Point(event.getX(), event.getY()), "Se seleccionó: ", (figure) -> {
					selectedFigure = figure;
					tb.setFigureData(selectedFigure);
				}, () -> {
					selectedFigure = null;
					statusPane.updateStatus("Ninguna figura encontrada");
				});
				redrawCanvas();
			}
		});

		canvas.setOnMouseDragged(event -> {
			if(tb.isSelecting()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
				double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
				if (selectedFigure != null)
					selectedFigure.move(diffX, diffY);
				redrawCanvas();
			}
		});
		setTop(cb.getChangesBox());
		setLeft(tb.getToolBox());
		setRight(canvas);
	}

	private void onLastFoundFigure(Point eventPoint, String baseMessage, Consumer<ColoredFigure> figureFoundConsumer, Runnable figureNotFoundRunnable) {
		StreamSupport
				.stream(canvasState.figures().spliterator(), false)
				.filter(f -> f.pointBelongs(eventPoint))
				.reduce((o, n) -> n) // Get last figure
				.ifPresentOrElse(f -> {
					figureFoundConsumer.accept(f);
					StringBuilder label = new StringBuilder(baseMessage);
					label.append(selectedFigure);
					statusPane.updateStatus(label.toString());
				}, figureNotFoundRunnable);
	}

	private Optional<ColoredFigure> getSelectedFigure() {
		return Optional.ofNullable(selectedFigure);
	}

	public void onSelectedFigurePresent(Function<ColoredFigure, Change> changeFunction) {
		getSelectedFigure().ifPresent((figure) -> {
			canvasState.addChange(changeFunction.apply(figure));
			redrawCanvas();
		});
	}

	public void deleteSelectedFigure(){
		onSelectedFigurePresent(figure -> {
			Change change = new DeleteChange(selectedFigure, canvasState);
			selectedFigure = null;
			return change;
		});
	}

	public void redoChange() {
		canvasState.redoChange();
		redrawCanvas();
	}

	public void undoChange() {
		canvasState.undoChange();
		redrawCanvas();
	}

	private void redrawCanvas() {
		gc.clear(canvas.getWidth(), canvas.getHeight());
		canvasState.figures().forEach(f -> f.draw(f == selectedFigure));
		cb.setChangeLabels(canvasState.getChangeData());
	}
}
