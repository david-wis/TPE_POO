package frontend;

import backend.CanvasState;
import backend.model.*;
import backend.model.Changes.Change;
import backend.model.Changes.CreateChange;
import backend.model.Changes.DeleteChange;
import frontend.Tools.ButtonToolBar;
import frontend.Tools.ChangesBar;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.StreamSupport;

/**
 * 	Panel que contiene el canvas y los controles
 */
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

	// Barra de la izquierda
	private final ButtonToolBar tb;

	// Barra de la derecha
	private final ChangesBar cb;

	// Constantes
	private static final String SELECTED_STROKE_COLOR = "#FF0000";
	private static final String NO_FIGURES_SELECTED = "No hay ningún objeto seleccionado";
	private static final String NO_FIGURES_FOUND = "Ninguna figura encontrada";
	private static final String ERROR = "Error";
	private static final String WAS_SELECTED = "Se seleccionó: ";

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		tb = new ButtonToolBar(gc, this);
		cb = new ChangesBar(this);
		addEvents();
		setTop(cb.getChangesBox());
		setLeft(tb.getToolBox());
		setRight(canvas);
	}

	/**
	 * Setea eventos sobre el canvas
	 */
	private void addEvents() {
		// Poner punto inicial
		canvas.setOnMousePressed(event -> startPoint = new Point(event.getX(), event.getY()));

		// Poner punto final
		canvas.setOnMouseReleased(event -> {
			Point endPoint = new Point(event.getX(), event.getY());
			Optional.ofNullable(startPoint).ifPresent(sp -> {
				if (endPoint.getX() > startPoint.getX() && endPoint.getY() > startPoint.getY())	 {
					tb.getFigureFromSelectedButton(startPoint, endPoint).ifPresent(figure -> canvasState.addChange(new CreateChange(figure, canvasState)));
					startPoint = null;
					redrawCanvas();
				}
			});
		});

		// Mostrar figura hovereada
		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			onLastFoundFigure(new Point(event.getX(), event.getY()), "", (figure) -> {}, () -> statusPane.updateStatus(eventPoint.toString()));
		});

		// Seleccionar / deseleccionar figuras
		canvas.setOnMouseClicked(event -> {
			if(tb.isSelecting()) { // Si el toggle de seleccion esta marcado
				onLastFoundFigure(new Point(event.getX(), event.getY()), WAS_SELECTED, (figure) -> {
					selectedFigure = figure;
					tb.setColorData(selectedFigure.getColorData()); // Cargar colores de figura en el slider
				}, () -> {
					selectedFigure = null; // Deseleccion
					statusPane.updateStatus(NO_FIGURES_FOUND);
				});
				redrawCanvas();
			}
		});

		// Mover figura seleccionada
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
	}


	/**
	 * Ejecuta una funcion sobre la ultima figura que contiene un punto
	 * Si la figura no existe llama a un runnable de error
	 * @param eventPoint Punto dentro de la figura
	 * @param baseMessage Mensaje base para poner en el status pane
	 * @param figureFoundConsumer Consumer que realiza una accion sobre la ultima figura que tiene el punto
	 * @param figureNotFoundRunnable Funcion a ejecutar si no se encuentra la figura
	 */
	private void onLastFoundFigure(Point eventPoint, String baseMessage, Consumer<ColoredFigure> figureFoundConsumer, Runnable figureNotFoundRunnable) {
		StreamSupport
				.stream(canvasState.figures().spliterator(), false)
				.filter(f -> f.pointBelongs(eventPoint))
				.reduce((o, n) -> n) // Se obtiene la ultima figura del iterable
				.ifPresentOrElse(f -> {
					figureFoundConsumer.accept(f);
					statusPane.updateStatus(baseMessage + f);
				}, figureNotFoundRunnable);
	}


	/**
	 * @return Optional con la figura seleccionada
	 */
	private Optional<ColoredFigure> getSelectedFigure() {
		return Optional.ofNullable(selectedFigure);
	}


	/**
	 * @param changeFunction Funcion a ejecutar si hay una figura seleccionada. Recibe un cambio y una figura con color
	 */
	public void onSelectedFigurePresent(Function<ColoredFigure, Change> changeFunction) {
		getSelectedFigure().ifPresent((figure) -> {
			canvasState.addChange(changeFunction.apply(figure));
			redrawCanvas();
		});
	}


	/**
	 * Variante de onSelectedFigurePresent que requiere obligatoriamente que exista una figura seleccionada.
	 * Si no hay tira error
	 * @param changeFunction Funcion a ejecutar si hay una figura seleccionada. Recibe un cambio y una figura con color
	 */
	public void onSelectedFigurePresentMandatory(Function<ColoredFigure, Change> changeFunction) {
		if (selectedFigure == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle(ERROR);
			alert.setHeaderText(NO_FIGURES_SELECTED);
			alert.show();
			return;
		}
		onSelectedFigurePresent(changeFunction);
	}


	/**
	 * 	Si hay una figura seleccionada, la elimina y registra el cambio
	 */
	public void deleteSelectedFigure(){
		onSelectedFigurePresentMandatory(figure -> {
			Change change = new DeleteChange(selectedFigure, canvasState);
			selectedFigure = null;
			return change;
		});
	}

	/**
	 * Rehace el ultimo cambio
	 */
	public void redoChange() {
		selectedFigure = null;
		canvasState.redoChange();
		redrawCanvas();
	}

	/**
	 * Revierte el ultimo cambio
	 */
	public void undoChange() {
		selectedFigure = null;
		canvasState.undoChange();
		redrawCanvas();
	}

	/**
	 * Limpia el canvas, dibuja todas las figuras y setea los labels
	 */
	private void redrawCanvas() {
		gc.clear(canvas.getWidth(), canvas.getHeight());
		canvasState.figures().forEach(f -> {
			if (selectedFigure == f)
				f.draw(SELECTED_STROKE_COLOR);
			else
				f.draw();
		});
		cb.setChangeLabels(canvasState.getChangeData());
	}
}
