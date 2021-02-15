package de.fechtelhoff.thg.gui.shared;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Hilfsklasse für allgemeine im GUI verwendetet Methoden.
 */
@SuppressWarnings("java:S106") // java:S106 -> Standard outputs should not be used directly to log anything
public class GuiShared {

	private static final int SEPARATOR_LINE_LENGTH = 100;

	private GuiShared() {
		throw new UnsupportedOperationException();
	}

	public static File getDirectoryToOpenIn(final Path path) {
		final File openInDirectory;
		if (Objects.nonNull(path)) {
			if (Files.isDirectory(path)) {
				openInDirectory = path.toFile();
			} else {
				openInDirectory = path.getParent().toFile();
			}
		} else {
			openInDirectory = new File("").getAbsoluteFile();
		}
		return openInDirectory;
	}

	public static void printSeparatorLine() {
		System.out.println("-".repeat(SEPARATOR_LINE_LENGTH));
	}

	public static void redirectSystemStreams(final Consumer<String> consumer) {
		OutputStream out = new OutputStream() {
			@Override
			public void write(int b) {
				consumer.accept(String.valueOf((char) b));
			}

			@Override
			public void write(byte[] b) {
				write(b, 0, b.length);
			}

			@Override
			public void write(byte[] b, int off, int len) {
				consumer.accept(new String(b, off, len));
			}
		};
		System.setOut(new PrintStream(out, true));
		System.setErr(new PrintStream(out, true));
	}
}
