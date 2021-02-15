package de.fechtelhoff.thg.gui.shared;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GuiSharedTest {

	private static final Path TEST_DIR = Path.of("target/test-classes/test/gui/shared");
	private static final Path TEST_FILE_PATH = TEST_DIR.resolve("testfile.txt");
	public static final String OK_TEXT = "Dies ist ein Test.";
	public static final String ERROR_TEXT = "Dies ist ein Fehler.";

	@Test
	void privateConstructorTest() {
		Assertions.assertThrows(InvocationTargetException.class, () -> {
				Constructor<GuiShared> constructor = GuiShared.class.getDeclaredConstructor();
				constructor.setAccessible(true);
				constructor.newInstance();
			}
		);
	}

	@Test
	void getDirectoryToOpenInTest() {
		Assertions.assertTrue(GuiShared.getDirectoryToOpenIn(TEST_DIR).isDirectory());
		Assertions.assertTrue(GuiShared.getDirectoryToOpenIn(TEST_FILE_PATH).isDirectory());
		Assertions.assertTrue(GuiShared.getDirectoryToOpenIn(null).isDirectory());
	}

	@Test
	void printSeparatorLineTest() {
		Assertions.assertDoesNotThrow(GuiShared::printSeparatorLine);
	}

	@Test
	void redirectSystemStreamsTest() {
		final PrintStream originalOutputStream = System.out;
		final PrintStream originalErrorStream = System.err;

		final StringBuilder sb = new StringBuilder();
		GuiShared.redirectSystemStreams(sb::append);
		System.out.println(OK_TEXT);
		System.out.write(12345);
		System.err.println(ERROR_TEXT);
		System.err.write(67890);

		System.setOut(originalOutputStream);
		System.setErr(originalErrorStream);

		final String text = sb.toString();
		System.out.println(text);

		Assertions.assertTrue(text.contains(OK_TEXT));
		Assertions.assertTrue(text.contains(ERROR_TEXT));
	}
}
