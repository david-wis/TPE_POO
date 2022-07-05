package frontend;

import backend.CanvasState;
import backend.model.*;
import frontend.Tools.ToolBar;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;

import java.util.Optional;
import java.util.function.Function;

public class PaintPane extends BorderPane {

	// TODO: Add access modifiers

	// BackEnd
	private CanvasState canvasState;

	// Canvas y relacionados
	Canvas canvas = new Canvas(800, 600);

	GraphicsController gc = new GraphicsController(canvas.getGraphicsContext2D());

	// Dibujar una figura
	Point startPoint;

	// Seleccionar una figura
	private ColoredFigure selectedFigure;

	// StatusBar
	StatusPane statusPane;

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
			if(startPoint == null) {
				return ;
			}
			if(endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
				return ;
			}
			tb.getFigureFromSelectedButton(startPoint, endPoint).ifPresent(figure -> canvasState.addChange(new CreateChange(figure, canvasState)));
			startPoint = null;
			redrawCanvas();
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder();
			for(Figure figure : canvasState.figures()) {
				if(figure.pointBelongs(eventPoint)) {
					found = true;
					label.append(figure);
				}
			}
			if(found) {
				statusPane.updateStatus(label.toString());
			} else {
				statusPane.updateStatus(eventPoint.toString());
			}
		});

		canvas.setOnMouseClicked(event -> {
			if(tb.isSelecting()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				boolean found = false;
				StringBuilder label = new StringBuilder("Se seleccionó: ");
				for (ColoredFigure figure : canvasState.figures()) {
					if(figure.pointBelongs(eventPoint)) {
						found = true;
						selectedFigure = figure;
						tb.setFigureData(figure);
						label.append(figure);
					}
				}
				if (found) {
					statusPane.updateStatus(label.toString());
				} else {
					selectedFigure = null;
					statusPane.updateStatus("Ninguna figura encontrada");
				}
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

		setLeft(tb.getToolBox());
		setRight(canvas);
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
