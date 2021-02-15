package de.fechtelhoff.thg.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import de.fechtelhoff.thg.api.CoreApi;

/**
 * Einfache Klasse die eine Text-Datei einliest und verkehrt herum wieder schreibt.
 */
@SuppressWarnings("java:S106") // java:S106 -> Standard outputs should not be used directly to log anything
public class Core implements CoreApi {

	public static final String FILE_TEXT = "Die Datei ";
	public static final String DIRECTORY_TEXT = "Das Verzeichnis ";
	public static final String TIMESTAMP_PATTERN = "yyyyMMddHHmmssnn";

	@Override
	public void reverseFile(final Path inputFilePath, final Path outputDirectoryPath) {
		if (checkParameter(inputFilePath, outputDirectoryPath)) {
			System.out.println("Input File: " + inputFilePath.toAbsolutePath());
			System.out.println("Output Directory: " + outputDirectoryPath.toAbsolutePath());
			final List<String> inputList = readFile(inputFilePath);
			final List<String> reversedList = reverseList(inputList);
			final List<String> outputList = reversedList
				.stream()
				.map(this::reverseString)
				.collect(Collectors.toList());
			final Path outputFilePath = getOutputFilePath(inputFilePath, outputDirectoryPath);
			writeFile(outputFilePath, outputList);
		}
	}

	private boolean checkParameter(final Path inputFilePath, final Path outputDirectoryPath) {
		if (Objects.isNull(inputFilePath)) {
			System.out.println("Eingabedatei darf nicht 'null' sein.");
			return false;
		}
		if (!Files.exists(inputFilePath)) {
			System.out.println(FILE_TEXT + inputFilePath.toAbsolutePath() + " existiert nicht.");
			return false;
		}
		if (!Files.isRegularFile(inputFilePath)) {
			System.out.println(inputFilePath.toAbsolutePath() + " ist keine Datei.");
			return false;
		}
		if (Objects.isNull(outputDirectoryPath)) {
			System.out.println("Ausgabeverzeichnis darf nicht 'null' sein.");
			return false;
		}
		if (!Files.exists(outputDirectoryPath)) {
			System.out.println(DIRECTORY_TEXT + outputDirectoryPath.toAbsolutePath() + " existiert nicht.");
			return false;
		}
		if (!Files.isDirectory(outputDirectoryPath)) {
			System.out.println(outputDirectoryPath.toAbsolutePath() + " ist kein Verzeichnis.");
			return false;
		}
		return true;
	}

	private List<String> reverseList(final List<String> list) {
		final List<String> reversedList = new ArrayList<>(list);
		Collections.reverse(reversedList);
		return reversedList;
	}

	private String reverseString(final String input) {
		return new StringBuilder(input).reverse().toString();
	}

	static List<String> readFile(final Path path) {
		final List<String> list = new ArrayList<>();
		try {
			list.addAll(Files.readAllLines(path));
		} catch (final IOException exception) {
			System.err.println(FILE_TEXT + path.toAbsolutePath() + " kann nicht gelesen werden.");
		}
		return list;
	}

	static void writeFile(final Path path, final List<String> list) {
		try {
			System.out.println("Output File: " + path.toAbsolutePath());
			Files.write(path, list);
		} catch (final IOException exception) {
			System.err.println(FILE_TEXT + path.toAbsolutePath() + " kann nicht geschrieben werden.");
		}
	}

	private static Path getOutputFilePath(final Path inputFilePath, final Path outputDirectoryPath) {
		final String fileName = inputFilePath.getFileName().toString();
		return outputDirectoryPath.resolve(fileName + ".reversed." + getTimeStamp());
	}

	private static String getTimeStamp() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN));
	}
}
