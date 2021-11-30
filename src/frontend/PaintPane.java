package frontend;

import backend.CanvasState;
import backend.drawable.DrawableCircle;
import backend.drawable.DrawableFigure;
import backend.drawable.DrawableRectangle;
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

import java.util.Optional;

public class PaintPane extends BorderPane {

	// BackEnd
	private CanvasState canvasState;

	// Canvas y relacionados
	private Canvas canvas = new Canvas(800, 600);
	private GraphicsContext gc = canvas.getGraphicsContext2D();
	private Color lineColor = Color.BLACK;
	private Color fillColor = Color.YELLOW;
	private double lineWidth = 1;

	// Botones Barra Izquierda
	private ToggleButton selectionButton = new ToggleButton("Seleccionar", createButtonGraphic("select"));
	private ToggleButton rectangleButton = new ToggleButton("Rectángulo", createButtonGraphic("rectangle"));
	private ToggleButton circleButton = new ToggleButton("Círculo", createButtonGraphic("circle"));
	private ToggleButton squareButton = new ToggleButton("Cuadrado", createButtonGraphic("square"));
	private ToggleButton ellipseButton = new ToggleButton("Elípse", createButtonGraphic("ellipse"));
	private ToggleButton lineButton = new ToggleButton("Línea", createButtonGraphic("line"));
	private Button deleteButton = new Button("Borrar", createButtonGraphic("delete"));
	private Button sendToBackButton = new Button("Al Fondo", createButtonGraphic("send-to-back"));
	private Button bringToFrontButton = new Button("Al Frente", createButtonGraphic("bring-to-front"));

	// Dibujar una figura
	private Point startPoint;

	// Seleccionar una figura
	private DrawableFigure selectedFigure;

	// StatusBar
	private StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		ToggleButton[] togglesArr = {selectionButton, squareButton, rectangleButton, circleButton, ellipseButton, lineButton};
		Button[] buttonsArr = {deleteButton, bringToFrontButton, sendToBackButton};
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : togglesArr) {
			tool.setMinWidth(120);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}
		for (Button tool : buttonsArr) {
			tool.setMinWidth(120);
			tool.setCursor(Cursor.HAND);
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
			if (selectedFigure == null) {
				noSelectionAlert();
				return;
			}
			// TODO
		});

		sendToBackButton.setOnAction(event -> {
			if (selectedFigure == null) {
				noSelectionAlert();
				return;
			}
			// TODO
		});

		bringToFrontButton.setOnAction(event -> {
			if (selectedFigure == null) {
				noSelectionAlert();
				return;
			}
			// TODO
		});

		lineColorPicker.setOnAction(event -> {
			lineColor = lineColorPicker.getValue();
			if (selectedFigure != null) {
				selectedFigure.setStrokeColor(lineColor);
				redrawCanvas();
			}
		});

		fillColorPicker.setOnAction(event -> {
			fillColor = fillColorPicker.getValue();
			if (selectedFigure != null) {
				selectedFigure.setFillColor(fillColor);
				redrawCanvas();
			}
		});

		borderSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			lineWidth = (double) newValue;
			if (selectedFigure != null) {
				selectedFigure.setLineWidth(lineWidth);
				redrawCanvas();
			}
		});

		canvas.setOnMousePressed(event -> {
			startPoint = new Point(event.getX(), event.getY());
		});

		canvas.setOnMouseReleased(event -> {
			Point endPoint = new Point(event.getX(), event.getY());
			if (startPoint == null) {
				return ;
			}
			if (endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
				return ;
			}
			DrawableFigure newFigure = null;
			if (rectangleButton.isSelected()) {
				newFigure = new DrawableRectangle(startPoint, endPoint, canvasState.getHigherZIndex(), lineColor, fillColor, lineWidth);
			}
			else if (circleButton.isSelected()) {
				double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
				newFigure = new DrawableCircle(startPoint, circleRadius, canvasState.getHigherZIndex(), lineColor, fillColor, lineWidth);
			} else if (squareButton.isSelected()) {
				// TODO
			} else if (ellipseButton.isSelected()) {
				// TODO
			} else if (lineButton.isSelected()) {
				// TODO
			} else {
				return ;
			}
			canvasState.addFigure(newFigure);
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

		canvas.setOnMouseClicked(event -> {
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				StringBuilder label = new StringBuilder("Se seleccionó: ");
				Optional<DrawableFigure> figureFound = canvasState.getFigure(eventPoint);
				if (figureFound.isPresent()) {
					selectedFigure = figureFound.get();
					label.append(selectedFigure);
					statusPane.updateStatus(label.toString());
				} else {
					selectedFigure = null;
					statusPane.updateStatus("Ninguna figura encontrada");
				}
				redrawCanvas();
			}
		});

		canvas.setOnMouseDragged(event -> {
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
				double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
				if (selectedFigure != null) {
					selectedFigure.move(diffX, diffY);
				}
				redrawCanvas();
			}
		});
		setLeft(buttonsBox);
		setRight(canvas);
	}

	private void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for (DrawableFigure figure : canvasState.figures()) {
			figure.draw(gc, figure == selectedFigure);
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
}
