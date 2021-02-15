package de.fechtelhoff.thg.gui.javafx;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.WaitForAsyncUtils;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JavaFxGuiTest extends ApplicationTest {

	private static final Path TEST_DIR = Path.of("target/test-classes/test/gui/javafx").toAbsolutePath();
	private static final Path TEST_FILE_PATH = TEST_DIR.resolve("testfile.txt").toAbsolutePath();

	private static final String BUTTON_EXECUTE_ID = "#buttonExecute";
	private static final String BUTTON_CLOSE_ID = "#buttonClose";
	private static final String BUTTON_CLEAR_OUTPUT_ID = "#buttonClearOutput";
	private static final String BUTTON_OPEN_FILE_CHOOSER_4_INPUT_FILE_ID = "#buttonOpenFileChooser4InputFile";
	private static final String BUTTON_OPEN_FILE_CHOOSER_4_OUTPUT_DIRECTORY_ID = "#buttonOpenFileChooser4OutputDirectory";

	private static final String TEXT_FIELD_INPUT_FILE = "#textFieldInputFile";
	private static final String TEXT_FIELD_OUTPUT_DIRECTORY = "#textFieldOutputDirectory";

	@BeforeAll
	static void beforeAll() {
		Assertions.assertTrue(Files.exists(TEST_FILE_PATH));
	}

	@BeforeEach
	public void beforeEach() throws Exception {
		FxToolkit.registerPrimaryStage();
		FxToolkit.setupApplication(JavaFxGui::new);
		FxToolkit.showStage();
		WaitForAsyncUtils.waitForFxEvents(100);
	}

	@Override
	public void start(final Stage primaryStage) {
		primaryStage.toFront();
	}

	@Test
	@Order(1)
	void checkButtonStatesAndLabel() {
		FxAssert.verifyThat(BUTTON_EXECUTE_ID, LabeledMatchers.hasText("Ausführen"));
		FxAssert.verifyThat(BUTTON_EXECUTE_ID, NodeMatchers.isDisabled());
		FxAssert.verifyThat(BUTTON_CLOSE_ID, LabeledMatchers.hasText("Beenden"));
		FxAssert.verifyThat(BUTTON_CLOSE_ID, NodeMatchers.isEnabled());
		FxAssert.verifyThat(BUTTON_CLEAR_OUTPUT_ID, LabeledMatchers.hasText("Ausgabe löschen"));
		FxAssert.verifyThat(BUTTON_CLEAR_OUTPUT_ID, NodeMatchers.isEnabled());
		FxAssert.verifyThat(BUTTON_OPEN_FILE_CHOOSER_4_INPUT_FILE_ID, NodeMatchers.isEnabled());
		FxAssert.verifyThat(BUTTON_OPEN_FILE_CHOOSER_4_OUTPUT_DIRECTORY_ID, NodeMatchers.isEnabled());
	}

	@Test
	@Order(2)
	void selectInputFileFirst() {
		final int numberOfFiles = numberOfFilesInTestDirectory();
		clickOn(TEXT_FIELD_INPUT_FILE).write(TEST_FILE_PATH.toString()).type(KeyCode.ENTER);
		clickOn(TEXT_FIELD_OUTPUT_DIRECTORY).write(TEST_DIR.toString()).type(KeyCode.ENTER);
		clickExecuteButton();
		clearOutputTextArea();
		Assertions.assertEquals(numberOfFiles, numberOfFilesInTestDirectory() - 1);
	}

	@Test
	@Order(3)
	void selectOutputDirectoryFirst() {
		final int numberOfFiles = numberOfFilesInTestDirectory();
		clickOn(TEXT_FIELD_OUTPUT_DIRECTORY).write(TEST_DIR.toString()).type(KeyCode.ENTER);
		clickOn(TEXT_FIELD_INPUT_FILE).write(TEST_FILE_PATH.toString()).type(KeyCode.ENTER);
		clickExecuteButton();
		clearOutputTextArea();
		Assertions.assertEquals(numberOfFiles, numberOfFilesInTestDirectory() - 1);
	}

	@Test
	@Order(4)
	@Disabled("Funktioniert nicht mit Junit da System.exit aufgerufen wird.")
	void clickCloseButton() {
		Assertions.assertDoesNotThrow(() -> clickOn(BUTTON_CLOSE_ID));
	}

	@AfterEach
	public void afterEach() throws TimeoutException {
		if (FxToolkit.isFXApplicationThreadRunning()) {
			FxToolkit.cleanupStages();
		}
	}

	@SuppressWarnings("unused")
	private void selectInputFile() {
		final FileChooser fileChooser = Mockito.mock(FileChooser.class);
		Mockito.when(fileChooser.showOpenDialog(this.targetWindow())).thenReturn(TEST_FILE_PATH.toFile());
		clickOn(BUTTON_OPEN_FILE_CHOOSER_4_INPUT_FILE_ID);
		push(KeyCode.ENTER);
	}

	@SuppressWarnings("unused")
	private void selectOutputDirectory() {
		final FileChooser fileChooser = Mockito.mock(FileChooser.class);
		Mockito.when(fileChooser.showOpenDialog(this.targetWindow())).thenReturn(TEST_DIR.toFile());
		clickOn(BUTTON_OPEN_FILE_CHOOSER_4_OUTPUT_DIRECTORY_ID);
		push(KeyCode.ENTER);
	}

	private void clickExecuteButton() {
		FxAssert.verifyThat(BUTTON_EXECUTE_ID, NodeMatchers.isEnabled());
		clickOn(BUTTON_EXECUTE_ID);
	}

	private void clearOutputTextArea() {
		FxAssert.verifyThat(BUTTON_CLEAR_OUTPUT_ID, NodeMatchers.isEnabled());
		clickOn(BUTTON_CLEAR_OUTPUT_ID);
		FxAssert.verifyThat(BUTTON_CLEAR_OUTPUT_ID, NodeMatchers.isDisabled());
	}

	private int numberOfFilesInTestDirectory() {
		try {
			return (int) Files.list(TEST_DIR).count();
		} catch (IOException exception) {
			throw new IllegalStateException("Unhandled exception occurred.", exception);
		}
	}
}
