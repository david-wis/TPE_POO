package frontend;

import backend.CanvasState;
import backend.model.*;
import frontend.Tools.ToolBar;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;

import java.util.Optional;

public class PaintPane extends BorderPane {

	// TODO: Add access modifiers

	// BackEnd
	CanvasState canvasState;

	// Canvas y relacionados
	Canvas canvas = new Canvas(800, 600);

	GraphicsController gc = new GraphicsController(canvas.getGraphicsContext2D());

	// Dibujar una figura
	Point startPoint;

	// Seleccionar una figura
	private ColoredFigure selectedFigure;

	// StatusBar
	StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		ToolBar tb = new ToolBar(gc, this);

		canvas.setOnMousePressed(event -> startPoint = new Point(event.getX(), event.getY()));

		canvas.setOnMouseReleased(event -> {
			Point endPoint = new Point(event.getX(), event.getY());
			if(startPoint == null) {
				return ;
			}
			if(endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
				return ;
			}
			tb.getSelectedFigure(startPoint, endPoint).ifPresent(canvasState::addFigure);
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

		tb.addDeleteButtonHandler(event -> {
			if (selectedFigure != null) {
				canvasState.deleteFigure(selectedFigure);
				selectedFigure = null;
				redrawCanvas();
			}
		});

		tb.addEnlargeButtonHandler(event -> {
			if (selectedFigure != null) {
				selectedFigure.enlarge();
				redrawCanvas();
			}
		});

		tb.addShrinkButtonHandler(event -> {
			if (selectedFigure != null) {
				selectedFigure.shrink();
				redrawCanvas();
			}
		});

		tb.addStrokeSliderHandler((observable, oldValue, newValue) -> {
			if (selectedFigure != null) {
				selectedFigure.setStrokeWeight(newValue.doubleValue());
				redrawCanvas();
			}
		});

		setLeft(tb.getToolBox());
		setRight(canvas);
	}

	public Optional<ColoredFigure> getSelectedFigure() {
		return Optional.ofNullable(selectedFigure);
	}

	public void redrawCanvas() {
		gc.clear(canvas.getWidth(), canvas.getHeight());
		for(Figure figure : canvasState.figures()) {
			figure.draw(figure == selectedFigure);
		}
	}
}
