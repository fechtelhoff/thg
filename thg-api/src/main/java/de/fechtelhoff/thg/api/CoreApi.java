package de.fechtelhoff.thg.api;

import java.nio.file.Path;

/**
 * API für der Core Funktion.
 */
public interface CoreApi {

	/**
	 * Dreht den Inhalt einer Datei um.
	 * D. h. die letzte Zeile wird zur ersten Zeile usw. und das letzte Zeichen einer Zeile wird zum ersten Zeichen.
	 *
	 * @param inputFilePath Eingabedatei
	 * @param outputDirectoryPath Ausgabeverzeichnis
	 */
	void reverseFile(final Path inputFilePath, final Path outputDirectoryPath);
}
