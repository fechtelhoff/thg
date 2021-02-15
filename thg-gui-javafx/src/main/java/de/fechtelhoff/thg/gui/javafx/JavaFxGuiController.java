package de.fechtelhoff.thg.gui.javafx;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;
import de.fechtelhoff.thg.core.Core;
import de.fechtelhoff.thg.gui.shared.GuiShared;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

@SuppressWarnings("java:S106") // java:S106 -> Standard outputs should not be used directly to log anything
public class JavaFxGuiController {

	private Path inputFilePath;
	private Path outputDirectoryPath;

	@FXML
	private Button buttonExecute;

	@FXML
	@SuppressWarnings("unused")
	private Button buttonClose;

	@FXML
	@SuppressWarnings("unused")
	private Button buttonClearOutput;

	@FXML
	@SuppressWarnings("unused")
	private Button buttonOpenFileChooser4InputFile;

	@FXML
	@SuppressWarnings("unused")
	private Button buttonOpenFileChooser4OutputDirectory;

	@FXML
	private TextField textFieldInputFile;

	@FXML
	private TextField textFieldOutputDirectory;

	@FXML
	private TextArea textAreaOutput;

	@FXML
	public void initialize() {
		GuiShared.redirectSystemStreams(this::updateTextArea);
		System.out.println("Start ...");
	}

	@FXML
	@SuppressWarnings("unused")
	void buttonExecuteAction(ActionEvent event) {
		if (Objects.nonNull(inputFilePath) && Objects.nonNull(outputDirectoryPath)) {
			GuiShared.printSeparatorLine();
			new Core().reverseFile(inputFilePath, outputDirectoryPath);
			enableOrDisableClearOutputButton();
		}
	}

	@FXML
	@SuppressWarnings("unused")
	void buttonCloseAction(ActionEvent event) {
		Platform.exit();
		System.exit(0);
	}

	@FXML
	@SuppressWarnings("unused")
	void buttonClearOutputAction(ActionEvent event) {
		textAreaOutput.setText(null);
		enableOrDisableClearOutputButton();
	}

	@FXML
	@SuppressWarnings("unused")
	void textFieldInputFileAction(ActionEvent event) {
		final String text = textFieldInputFile.getText();
		if (Objects.nonNull(text) && text.trim().length() > 0) {
			inputFilePath = Path.of(text);
		} else {
			inputFilePath = null;
			textFieldInputFile.setText(null);
		}
		enableOrDisableExecuteButton();
	}

	@FXML
	@SuppressWarnings("unused")
	void textFieldOutputDirectoryAction(ActionEvent event) {
		final String text = textFieldOutputDirectory.getText();
		if (Objects.nonNull(text) && text.trim().length() > 0) {
			outputDirectoryPath = Path.of(text);
		} else {
			outputDirectoryPath = null;
			textFieldOutputDirectory.setText(null);
		}
		enableOrDisableExecuteButton();
	}

	@FXML
	@SuppressWarnings("unused")
	void buttonOpenFileChooser4InputFileAction(ActionEvent event) {
		final File openInDirectory = GuiShared.getDirectoryToOpenIn(inputFilePath);
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Eingabedatei");
		chooser.setInitialDirectory(openInDirectory);
		File file = chooser.showOpenDialog(new Stage());
		if (Objects.nonNull(file)) {
			inputFilePath = file.toPath().toAbsolutePath();
			textFieldInputFile.setText(inputFilePath.toString());
		}
		enableOrDisableExecuteButton();
	}

	@FXML
	@SuppressWarnings("unused")
	void buttonOpenFileChooser4OutputDirectoryAction(ActionEvent event) {
		final File openInDirectory;
		if (Objects.nonNull(outputDirectoryPath)) {
			openInDirectory = GuiShared.getDirectoryToOpenIn(outputDirectoryPath);
		} else {
			openInDirectory = GuiShared.getDirectoryToOpenIn(inputFilePath);
		}
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Ausgabeverzeichnis");
		chooser.setInitialDirectory(openInDirectory);
		File file = chooser.showDialog(new Stage());
		if (Objects.nonNull(file)) {
			outputDirectoryPath = file.toPath().toAbsolutePath();
			textFieldOutputDirectory.setText(outputDirectoryPath.toString());
		}
		enableOrDisableExecuteButton();
	}

	private void enableOrDisableExecuteButton() {
		buttonExecute.setDisable(Objects.isNull(inputFilePath) || Objects.isNull(outputDirectoryPath));
	}

	private void enableOrDisableClearOutputButton() {
		buttonClearOutput.setDisable(Objects.isNull(textAreaOutput.getText()));
	}

	private void updateTextArea(final String text) {
		final String currentContent = textAreaOutput.getText();
		if (Objects.nonNull(currentContent)) {
			textAreaOutput.setText(currentContent + text);
		} else {
			textAreaOutput.setText(text);
		}
	}
}
