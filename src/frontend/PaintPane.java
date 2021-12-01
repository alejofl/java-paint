package frontend;

import backend.CanvasState;
//import backend.drawable.DrawableCircle;
import backend.drawable.*;
import backend.model.Figure;
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

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class PaintPane extends BorderPane {

	// BackEnd
	private final CanvasState canvasState;

	private static final Color DEFAULT_LINE_COLOR = Color.BLACK;
	private static final Color DEFAULT_FILL_COLOR = Color.YELLOW;
	private static final double DEFAULT_LINE_WIDTH = 1;
	private final Canvas canvas = new Canvas(800, 600);
	private final GraphicsContext gc = canvas.getGraphicsContext2D();
	private Color lineColor = DEFAULT_LINE_COLOR;
	private Color fillColor = DEFAULT_FILL_COLOR;
	private double lineWidth = DEFAULT_LINE_WIDTH;

	// Botones Barra Izquierda
	private final ToggleButton selectionButton = new ToggleButton("Seleccionar", createButtonGraphic("select"));
	private final ToggleButton rectangleButton = new ToggleButton("Rectángulo", createButtonGraphic("rectangle"));
	private final ToggleButton circleButton = new ToggleButton("Círculo", createButtonGraphic("circle"));
	private final ToggleButton squareButton = new ToggleButton("Cuadrado", createButtonGraphic("square"));
	private final ToggleButton ellipseButton = new ToggleButton("Elípse", createButtonGraphic("ellipse"));
	private final ToggleButton lineButton = new ToggleButton("Línea", createButtonGraphic("line"));
	private final Button deleteButton = new Button("Borrar", createButtonGraphic("delete"));
	private final Button sendToBackButton = new Button("Al Fondo", createButtonGraphic("send-to-back"));
	private final Button bringToFrontButton = new Button("Al Frente", createButtonGraphic("bring-to-front"));

	private final SelectionHandler selector = new SelectionHandler();

	private Point startPoint;

	// StatusBar
	private final StatusPane statusPane;

	// dragging variable for more consistent element dragging
	private boolean dragging = false;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
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

		deleteButton.setOnAction(event -> {
			deleteFigure();
		});

		sendToBackButton.setOnAction(event -> {
			if (selector.noSelection()) {
				noSelectionAlert();
				return;
			}

			selector.getSelectedFigures().forEach(canvasState::sendToBack);
			selector.clearSelection();
			redrawCanvas();
		});

		bringToFrontButton.setOnAction(event -> {
			if (selector.noSelection()) {
				noSelectionAlert();
				return;
			}
			selector.getSelectedFigures().forEach(canvasState::bringToFront);
			selector.clearSelection();
			redrawCanvas();
		});

		lineColorPicker.setOnAction(event -> {
			lineColor = lineColorPicker.getValue();
			selector.getSelectedFigures().forEach(figure -> figure.setStrokeColor(lineColor));
			redrawCanvas();
		});

		fillColorPicker.setOnAction(event -> {
			fillColor = fillColorPicker.getValue();
			selector.getSelectedFigures().forEach(figure -> figure.setFillColor(fillColor));
			redrawCanvas();
		});

		borderSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			lineWidth = (double) newValue;
			selector.getSelectedFigures().forEach(figure -> figure.setLineWidth(lineWidth));
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
				Set<DrawableFigure> selectedFigures = selector.getSelectedFigures();
				if (selectedFigures.isEmpty()) {
					statusPane.updateStatus("Ninguna figura encontrada");
					selector.clearSelection();
				} else {
					selectedFigures.forEach(label::append);
					statusPane.updateStatus(label.toString());
				}
			} else {
				DrawableFigure newFigure = null;
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
			Optional<DrawableFigure> figureFound = canvasState.getFigure(eventPoint);
			if (figureFound.isPresent()) {
				statusPane.updateStatus(figureFound.get().toString());
			} else {
				statusPane.updateStatus(eventPoint.toString());
			}
		});

		canvas.setOnMouseDragged(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			if (dragging) {
				double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
				double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
				selector.moveSelection(diffX, diffY);
				redrawCanvas();
			}
		});
		setLeft(buttonsBox);
		setRight(canvas);
	}

	private void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		Set<DrawableFigure> selectedFigures = selector.getSelectedFigures();
		for (DrawableFigure figure : canvasState.figures()) {
			figure.draw(gc, selectedFigures.contains(figure));
		}
	}

	private ImageView createButtonGraphic(String imageName) {
		Image img = new Image(getClass().getResourceAsStream("/resources/" + imageName + ".png"));
		return new ImageView(img);
	}

	private void noSelectionAlert() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("No se puede realizar la acción");
		alert.setContentText("No hay una figura seleccionada.");
		alert.showAndWait();
	}

	private void clearSelectionAndRedraw() {
		selector.clearSelection();
		redrawCanvas();
	}

	protected void deleteFigure() {
		if (selector.noSelection()) {
			noSelectionAlert();
			return;
		}
		selector.getSelectedFigures().forEach(canvasState::remove);
		selector.clearSelection();
		redrawCanvas();
	}

	private class SelectionHandler {
		private Point startPoint;
		private Point endPoint;
		
		private final Set<DrawableFigure> figures = new TreeSet<>();

		public void setStartPoint(Point startPoint) {
			this.startPoint = startPoint;
		}

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

		private void selectFigure(Point point) {
			Optional<DrawableFigure> figureFound = canvasState.getFigure(point);
			figureFound.ifPresent(figure -> {
				figures.add(figure);
				Figure.Limits limits = figure.limits();
				startPoint = limits.getStart();
				endPoint = limits.getEnd();
			});
		}

		private boolean isFigureInSelection(Figure figure) {
			Figure.Limits limits = figure.limits();
			if (startPoint == null || endPoint == null) {
				throw new IllegalStateException("No startPoint or endPoint");
			}

			return isSelectionValid() &&
					isPointInSelection(limits.getStart()) &&
					isPointInSelection(limits.getEnd());
		}

		public boolean noSelection() {
			return figures.isEmpty();
		}

		public boolean isClick() {
			return startPoint != null && startPoint.equals(endPoint);
		}

		public void clearSelection() {
			startPoint = null;
			endPoint = null;
			figures.clear();
		}

		public void moveSelection(double diffX, double diffY) {
			if (startPoint != null && endPoint != null) {
				startPoint.move(diffX, diffY);
				endPoint.move(diffX, diffY);
				getSelectedFigures().forEach(figure -> figure.move(diffX, diffY));
			}
		}

		public boolean isPointInSelection(Point point) {
			return (startPoint != null && endPoint != null) && startPoint.getX() < point.getX() &&
					startPoint.getY() < point.getY() &&
					point.getX() < endPoint.getX() &&
					point.getY() < endPoint.getY();
		}

		private boolean isSelectionValid() {
			return startPoint.getX() < endPoint.getX() && startPoint.getY() < endPoint.getY();
		}

		private Set<DrawableFigure> getSelectedFigures() {
			return figures;
		}
	}
}
