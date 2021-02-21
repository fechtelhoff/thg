package de.fechtelhoff.thg.gui.javafx;

import de.fechtelhoff.thg.utils.ProjectProperties;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Einfaches JavaFX GUI welches die Funktionalität des Core-Moduls abbildet.
 */
public class JavaFxGui extends Application {

	private static final String APPLICATION_NAME = "The Holy Graal - Version " + ProjectProperties.getProjectVersion();

	@Override
	public void start(final Stage mainStage) throws Exception {
		final Parent root = FXMLLoader.load(getClass().getResource("JavaFxGuiView.fxml"));
		mainStage.setTitle(APPLICATION_NAME);
		mainStage.setOnCloseRequest(this::closeAction);
		mainStage.getIcons().add(loadImageFromClassResource("/logo.png"));
		mainStage.setScene(new Scene(root, 600, 400));
		mainStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void closeAction(final WindowEvent event) {
		Platform.exit();
		System.exit(0);
	}

	@SuppressWarnings("SameParameterValue")
	private Image loadImageFromClassResource(final String name) {
		return new Image(getClass().getResourceAsStream(name));
	}
}
