package frontend;

import backend.CanvasState;
import frontend.drawable.*;
import backend.model.Figure;
import backend.model.Limits;
import backend.model.Point;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.*;

public class PaintPane extends BorderPane {

	// BackEnd
	private final CanvasState canvasState;

	// Drawing variables
	private static final Color DEFAULT_LINE_COLOR = Color.BLACK;
	private static final Color DEFAULT_FILL_COLOR = Color.YELLOW;
	private static final double DEFAULT_LINE_WIDTH = 1;
	private final Canvas canvas = new Canvas(800, 600);
	private final GraphicsContext gc = canvas.getGraphicsContext2D();
	private Color lineColor = DEFAULT_LINE_COLOR;
	private Color fillColor = DEFAULT_FILL_COLOR;
	private double lineWidth = DEFAULT_LINE_WIDTH;

	// SideBar buttons
	private final ToggleButton selectionButton = new ToggleButton("Seleccionar", createButtonGraphic("select"));
	private final ToggleButton rectangleButton = new ToggleButton("Rectángulo", createButtonGraphic("rectangle"));
	private final ToggleButton circleButton = new ToggleButton("Círculo", createButtonGraphic("circle"));
	private final ToggleButton squareButton = new ToggleButton("Cuadrado", createButtonGraphic("square"));
	private final ToggleButton ellipseButton = new ToggleButton("Elípse", createButtonGraphic("ellipse"));
	private final ToggleButton lineButton = new ToggleButton("Línea", createButtonGraphic("line"));
	private final Button deleteButton = new Button("Borrar", createButtonGraphic("delete"));
	private final Button sendToBackButton = new Button("Al Fondo", createButtonGraphic("send-to-back"));
	private final Button bringToFrontButton = new Button("Al Frente", createButtonGraphic("bring-to-front"));

	// Selection handler
	private final SelectionHandler selector = new SelectionHandler();

	// Point where the user pressed
	private Point startPoint;

	// StatusBar
	private final StatusPane statusPane;

	// For more consistent element dragging
	private boolean dragging = false;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		// UI creation
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		ToggleButton[] togglesArr = {selectionButton, squareButton, rectangleButton, circleButton, ellipseButton, lineButton};
		selectionButton.setSelected(true);
		Button[] buttonsArr = {deleteButton, bringToFrontButton, sendToBackButton};
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : togglesArr) {
			tool.setMinWidth(120);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
			tool.setOnAction(event -> clearSelectionAndRedraw());
		}
		for (Button tool : buttonsArr) {
			tool.setMinWidth(120);
			tool.setCursor(Cursor.HAND);
			tool.setOnAction(event -> clearSelectionAndRedraw());
		}
		VBox buttonsBox = new VBox(10);
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(130);
		Separator separator1 = new Separator();
		separator1.setStyle("-fx-border-color: null; -fx-background-color: black");
		Separator separator2 = new Separator();
		separator2.setStyle("-fx-border-color: null; -fx-background-color: black");
		Slider borderSlider = new Slider(0, 25, lineWidth);
		borderSlider.setShowTickMarks(true);
		borderSlider.setShowTickLabels(true);
		borderSlider.setSnapToTicks(true);
		borderSlider.setMajorTickUnit(5);
		borderSlider.setMinorTickCount(4);
		ColorPicker lineColorPicker = new ColorPicker(lineColor);
		ColorPicker fillColorPicker = new ColorPicker(fillColor);
		buttonsBox.getChildren().addAll(togglesArr);
		buttonsBox.getChildren().add(separator1);
		buttonsBox.getChildren().addAll(buttonsArr);
		buttonsBox.getChildren().add(separator2);
		buttonsBox.getChildren().add(new Label("Borde"));
		buttonsBox.getChildren().add(lineColorPicker);
		buttonsBox.getChildren().add(borderSlider);
		buttonsBox.getChildren().add(new Label("Relleno"));
		buttonsBox.getChildren().add(fillColorPicker);

		// Event Listeners
		deleteButton.setOnAction(event -> {
			deleteFigure();
		});

		sendToBackButton.setOnAction(event -> {
			if (selector.noSelection()) {
				noSelectionAlert();
				return;
			}
			selector.getSelectedFigures().descendingSet().forEach(canvasState::sendToBack);
			redrawCanvas();
		});

		bringToFrontButton.setOnAction(event -> {
			if (selector.noSelection()) {
				noSelectionAlert();
				return;
			}
			selector.getSelectedFigures().forEach(canvasState::bringToFront);
			redrawCanvas();
		});

		lineColorPicker.setOnAction(event -> {
			lineColor = lineColorPicker.getValue();
			selector.getSelectedFigures().forEach(figure -> ((Drawable) figure).getDrawConfiguration().setLineColor(lineColor));
			redrawCanvas();
		});

		fillColorPicker.setOnAction(event -> {
			fillColor = fillColorPicker.getValue();
			selector.getSelectedFigures().forEach(figure -> ((Drawable) figure).getDrawConfiguration().setFillColor(fillColor));
			redrawCanvas();
		});

		borderSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			lineWidth = (double) newValue;
			selector.getSelectedFigures().forEach(figure -> ((Drawable) figure).getDrawConfiguration().setLineWidth(lineWidth));
			redrawCanvas();
		});

		canvas.setOnMousePressed(event -> {
			startPoint = new Point(event.getX(), event.getY());

			if (selectionButton.isSelected()) {
				if (!selector.noSelection() && selector.isPointInSelection(startPoint)) {
					dragging = true;
				} else {
					selector.clearSelection();
					selector.setStartPoint(startPoint);
				}
			}
		});

		canvas.setOnMouseReleased(event -> {
			if (startPoint == null) {
				return ;
			}

			Point endPoint = new Point(event.getX(), event.getY());

			if (selectionButton.isSelected()) {
				selector.setEndPoint(endPoint);
				StringBuilder label = new StringBuilder("Se seleccionó: ");
				Set<Figure> selectedFigures = selector.getSelectedFigures();
				if (selectedFigures.isEmpty()) {
					statusPane.updateStatus("Ninguna figura encontrada");
					selector.clearSelection();
				} else {
					selectedFigures.forEach(label::append);
					statusPane.updateStatus(label.toString());
				}
			} else {
				Figure newFigure = null;
				boolean validEndPoint = !(endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY());
				if (lineButton.isSelected()) {
					newFigure = new DrawableLine(startPoint, endPoint, canvasState.getHigherZIndex(), lineColor, fillColor, lineWidth);
				} else if (validEndPoint) {
					if (rectangleButton.isSelected()) {
						newFigure = new DrawableRectangle(startPoint, endPoint, canvasState.getHigherZIndex(), lineColor, fillColor, lineWidth);
					}
					else if (circleButton.isSelected()) {
						double edge = endPoint.getX() - startPoint.getX();
						newFigure = new DrawableCircle(startPoint, edge, canvasState.getHigherZIndex(), lineColor, fillColor, lineWidth);
					} else if (squareButton.isSelected()) {
						double edge = endPoint.getX() - startPoint.getX();
						newFigure = new DrawableSquare(startPoint, edge, canvasState.getHigherZIndex(), lineColor, fillColor, lineWidth);
					} else if (ellipseButton.isSelected()) {
						newFigure = new DrawableEllipse(startPoint, endPoint, canvasState.getHigherZIndex(), lineColor, fillColor, lineWidth);
					}
				} else {
					return;
				}
				canvasState.addFigure(newFigure);
			}

			if (dragging) {
				dragging = false;
				selector.clearSelection();
			}

			startPoint = null;
			redrawCanvas();
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			Optional<Figure> figureFound = canvasState.getFigure(eventPoint);
			statusPane.updateStatus(figureFound.isPresent() ? figureFound.get().toString() : eventPoint.toString());
		});

		canvas.setOnMouseDragged(event -> {
			if (dragging) {
				double diffX = (event.getX() - startPoint.getX());
				double diffY = (event.getY() - startPoint.getY());
				startPoint.move(diffX,diffY);
				selector.moveSelection(diffX, diffY);
				redrawCanvas();
			}
		});

		// UI
		setLeft(buttonsBox);
		setRight(canvas);
	}

	/**
	 * Method in charge of displaying all the figures in the canvas
	 */
	private void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		Set<Figure> selectedFigures = selector.getSelectedFigures();
		for (Figure figure : canvasState.figures()) {
			((Drawable) figure).draw(gc, selectedFigures.contains(figure));
		}
	}

	/**
	 * Method used to create the JavaFX image that goes in every button in the UI
	 * @param imageName the name of the image, without extension. Must be placed in resources package
	 * @return JavaFX's ImageView with the desired image
	 */
	private ImageView createButtonGraphic(String imageName) {
		Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/" + imageName + ".png")));
		return new ImageView(img);
	}

	/**
	 * Method used to display an alert in case that a button that requires a selection is pressed without a figure selected
	 */
	private void noSelectionAlert() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("No se puede realizar la acción");
		alert.setContentText("No hay una figura seleccionada.");
		alert.showAndWait();
	}

	/**
	 * Self-explanatory
	 */
	private void clearSelectionAndRedraw() {
		selector.clearSelection();
		redrawCanvas();
	}

	/**
	 * Method used to delete function. Will be called if button is clicked and if Backspace/Delete keys are pressed
	 */
	protected void deleteFigure() {
		if (selector.noSelection()) {
			noSelectionAlert();
			return;
		}
		selector.getSelectedFigures().forEach(canvasState::remove);
		clearSelectionAndRedraw();
	}

	/**
	 * Class in charge of handling selection. Will decide what figures are selected after releasing mouse
	 */
	private class SelectionHandler {
		private Point startPoint;
		private Point endPoint;
		
		private final TreeSet<Figure> figures = new TreeSet<>();

		/**
		 * Will be called when mouse is pressed
		 * @param startPoint the top left point of the selection
		 */
		public void setStartPoint(Point startPoint) {
			this.startPoint = startPoint;
		}

		/**
		 * Will be called when mouse is released. Will decide if user clicked or dragged in the screen, and act accordingly
		 * @param endPoint - the bottom right point of the selection
		 */
		public void setEndPoint(Point endPoint) {
			this.endPoint = endPoint;

			if (isClick()) {
				selectFigure(endPoint);
			} else {
				canvasState.figures().forEach(figure -> {
					if (isFigureInSelection(figure)) {
						figures.add(figure);
					}
				});
			}
		}

		/**
		 * Boolean function that determines if something is selected
		 * @return true if nothing is selected and false otherwise
		 */
		public boolean noSelection() {
			return figures.isEmpty();
		}

		/**
		 * Will clear selection and leave the handler idle for a new selection
		 */
		public void clearSelection() {
			startPoint = null;
			endPoint = null;
			figures.clear();
		}

		/**
		 * Called when user selected one or more figures and dragged them
		 * @param diffX how much figure(s) must be moved in the X axis
		 * @param diffY how much figure(s) must be moved in the Y axis
		 */
		public void moveSelection(double diffX, double diffY) {
			if (startPoint != null && endPoint != null) {
				getSelectedFigures().forEach(figure -> ((Movable) figure).move(diffX, diffY));
			}
		}

		/**
		 * Will determine if a point is inside de imaginary rectangle of selection
		 * @param point the point in question
		 * @return true if point is in selection and false otherwise
		 */
		public boolean isPointInSelection(Point point) {
			return (startPoint != null && endPoint != null) && startPoint.getX() < point.getX() &&
					startPoint.getY() < point.getY() &&
					point.getX() < endPoint.getX() &&
					point.getY() < endPoint.getY();
		}

		/**
		 * Used to retrieve the selected figures
		 * @return a TreeSet of the selected figures
		 */
		public TreeSet<Figure> getSelectedFigures() {
			return figures;
		}

		/**
		 * Internal use. Used to determine if the selection was created correctly (from top left to bottom right)
		 * @return true if selection is valid and false otherwise
		 */
		private boolean isSelectionValid() {
			return startPoint.getX() < endPoint.getX() && startPoint.getY() < endPoint.getY();
		}

		/**
		 * Internal use. Used to determine if the user clicked or dragged
		 * @return true if user clicked and false otherwise
		 */
		private boolean isClick() {
			return startPoint != null && startPoint.equals(endPoint);
		}

		/**
		 * Internal use. Used to select the figure if user clicked
		 * @param point where the user clicked
		 */
		private void selectFigure(Point point) {
			Optional<Figure> figureFound = canvasState.getFigure(point);
			figureFound.ifPresent(figure -> {
				figures.add(figure);
				Limits limits = figure.getLimits();
				startPoint = limits.getStart();
				endPoint = limits.getEnd();
			});
		}

		/**
		 * Internal use. Used to determine if a whole figure is inside selection. Called if user dragged
		 * @param figure the figure in question
		 * @return true if figure is inside selection and false otherwise
		 */
		private boolean isFigureInSelection(Figure figure) {
			Limits limits = figure.getLimits();
			if (startPoint == null || endPoint == null) {
				throw new IllegalStateException("No startPoint or endPoint");
			}

			return isSelectionValid() &&
					isPointInSelection(limits.getStart()) &&
					isPointInSelection(limits.getEnd());
		}
	}
}
