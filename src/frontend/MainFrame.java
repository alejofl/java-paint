package frontend;

import backend.CanvasState;
import javafx.scene.layout.VBox;

public class MainFrame extends VBox {

    private final PaintPane paintPane;

    public MainFrame(CanvasState canvasState) {
        getChildren().add(new AppMenuBar());
        StatusPane statusPane = new StatusPane();
        paintPane = new PaintPane(canvasState, statusPane);
        getChildren().add(paintPane);
        getChildren().add(statusPane);
    }

    protected void deleteFigure() {
        paintPane.deleteFigure();
    }

}
