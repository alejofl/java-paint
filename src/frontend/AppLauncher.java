package frontend;

import backend.CanvasState;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class AppLauncher extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		CanvasState canvasState = new CanvasState(); // BackEnd
		MainFrame frame = new MainFrame(canvasState);
		Scene scene = new Scene(frame);
		scene.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE) {
				frame.deleteFigure();
			}
		});
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(event -> System.exit(0));
	}

}
