package de.fechtelhoff.thg.gui.swing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.DialogFixture;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.launcher.ApplicationLauncher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SwingGuiTest {

	private static final Path TEST_DIR = Path.of("target/test-classes/test/gui/swing").toAbsolutePath();
	private static final Path TEST_FILE_PATH = TEST_DIR.resolve("testfile.txt").toAbsolutePath();

	private FrameFixture frameFixture;

	private JButtonFixture buttonExecuteFixture;
	private JButtonFixture buttonCloseFixture;
	private JButtonFixture buttonClearOutputFixture;
	private JButtonFixture buttonOpenFileChooser4InputFileFixture;
	private JButtonFixture buttonOpenFileChooser4OutputDirectoryFixture;

	@BeforeAll
	static void beforeAll() {
		Assertions.assertTrue(Files.exists(TEST_FILE_PATH));
		FailOnThreadViolationRepaintManager.install();
	}

	@BeforeEach
	void beforeEach() {
		frameFixture = new FrameFixture(GuiActionRunner.execute(SwingGui::new).getFrame());
		buttonExecuteFixture = frameFixture.button(JButtonMatcher.withText("Ausführen"));
		buttonCloseFixture = frameFixture.button(JButtonMatcher.withText("Beenden"));
		buttonClearOutputFixture = frameFixture.button(JButtonMatcher.withText("Ausgabe löschen"));
		buttonOpenFileChooser4InputFileFixture = frameFixture.button(JButtonMatcher.withName("buttonOpenFileChooser4InputFile"));
		buttonOpenFileChooser4OutputDirectoryFixture = frameFixture.button(JButtonMatcher.withName("buttonOpenFileChooser4OutputDirectory"));
	}

	@Test
	@Order(1)
	void mainMethodExecution() {
		Assertions.assertDoesNotThrow(() -> ApplicationLauncher.application(SwingGui.class).start());
	}

	@Test
	@Order(2)
	void checkButtonStatesAndLabel() {
		Assertions.assertEquals("Ausführen", buttonExecuteFixture.text());
		buttonExecuteFixture.requireDisabled();
		Assertions.assertEquals("Beenden", buttonCloseFixture.text());
		buttonCloseFixture.requireEnabled();
		Assertions.assertEquals("Ausgabe löschen", buttonClearOutputFixture.text());
		buttonClearOutputFixture.requireEnabled();
		buttonOpenFileChooser4InputFileFixture.requireEnabled();
		buttonOpenFileChooser4OutputDirectoryFixture.requireEnabled();
	}

	@Test
	@Order(3)
	void selectInputFileFirst() {
		final int numberOfFiles = numberOfFilesInTestDirectory();
		selectInputFile();
		selectOutputDirectory();
		clickExecuteButton();
		clearOutputTextArea();
		Assertions.assertEquals(numberOfFiles, numberOfFilesInTestDirectory() - 1);
	}

	@Test
	@Order(4)
	void selectInputFileCancel() {
		Assertions.assertDoesNotThrow(this::doNotSelectInputFile);
	}

	@Test
	@Order(5)
	void selectOutputDirectoryFirst() {
		final int numberOfFiles = numberOfFilesInTestDirectory();
		selectOutputDirectory();
		selectInputFile();
		clickExecuteButton();
		clearOutputTextArea();
		Assertions.assertEquals(numberOfFiles, numberOfFilesInTestDirectory() - 1);
	}

	@Test
	@Order(6)
	void selectOutputDirectoryCancel() {
		Assertions.assertDoesNotThrow(this::doNotSelectOutputDirectory);
	}

	@Test
	@Order(7)
	void selectOutputDirectoryTwice() {
		Assertions.assertDoesNotThrow(this::selectOutputDirectory);
		Assertions.assertDoesNotThrow(this::selectOutputDirectory);
	}

	@Test
	@Order(8)
	@Disabled("Funktioniert nicht mit Junit da System.exit aufgerufen wird.")
	void clickCloseButton() {
		Assertions.assertDoesNotThrow(() -> buttonCloseFixture.click());
	}

	@AfterEach
	void afterEach() {
		if (Objects.nonNull(frameFixture)) {
			frameFixture.cleanUp();
		}
	}

	private void selectInputFile() {
		buttonOpenFileChooser4InputFileFixture.click();
		final DialogFixture dialog = frameFixture.dialog();
		dialog.fileChooser("fileChooserInputFile").selectFile(TEST_FILE_PATH.toFile());
		dialog.fileChooser("fileChooserInputFile").approve();
	}

	private void doNotSelectInputFile() {
		buttonOpenFileChooser4InputFileFixture.click();
		final DialogFixture dialog = frameFixture.dialog();
		dialog.fileChooser("fileChooserInputFile").cancel();
	}

	private void selectOutputDirectory() {
		buttonOpenFileChooser4OutputDirectoryFixture.click();
		final DialogFixture dialog = frameFixture.dialog();
		dialog.fileChooser("fileChooserOutputDirectory").selectFile(TEST_DIR.toFile());
		dialog.fileChooser("fileChooserOutputDirectory").approve();
	}

	private void doNotSelectOutputDirectory() {
		buttonOpenFileChooser4OutputDirectoryFixture.click();
		final DialogFixture dialog = frameFixture.dialog();
		dialog.fileChooser("fileChooserOutputDirectory").cancel();
	}

	private void clickExecuteButton() {
		buttonExecuteFixture.requireEnabled();
		buttonExecuteFixture.click();
	}

	private void clearOutputTextArea() {
		buttonClearOutputFixture.requireEnabled();
		buttonClearOutputFixture.click();
	}

	private int numberOfFilesInTestDirectory() {
		try {
			return (int) Files.list(TEST_DIR).count();
		} catch (IOException exception) {
			throw new IllegalStateException("Unhandled exception occurred.", exception);
		}
	}
}
