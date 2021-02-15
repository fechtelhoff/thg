package de.fechtelhoff.thg.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import de.fechtelhoff.thg.utils.ProjectProperties;

/**
 * Integrationstest für die Klasse CommandLineApplication.
 *
 * Ruft das erzeugte JAR (with-dependencies) direkt mit verschiedenen Parametern auf.
 */
class CommandLineApplicationIntTest {

	private static final Path TARGET_DIR = Path.of("target");

	private static final Path TEST_DIR = Path.of("target/test-classes/test/cli").toAbsolutePath();
	private static final Path TEST_FILE_PATH = TEST_DIR.resolve("testfile.txt").toAbsolutePath();

	private static String jarFile;

	@BeforeAll
	static void beforeAll() {
		final String version = ProjectProperties.getProjectVersion();
		jarFile = TARGET_DIR.resolve("thg-cli-" + version + "-jar-with-dependencies.jar").toAbsolutePath().toString();
		System.out.println("Executing Jar-File: " + jarFile);
	}

	@Test
	void checkIfJarFileExists() {
		Assertions.assertTrue(Files.exists(Path.of(jarFile)));
	}

	@Test
	void runJarWithoutParameters() throws IOException {
		ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", jarFile);
		processBuilder.directory(TARGET_DIR.toFile());
		Process process = processBuilder.start();
		printStandardInputAndStandardError(process);
		Assertions.assertEquals(0, process.exitValue());
	}

	@Test
	void runJarWithParameters() throws IOException {
		ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", jarFile, "-i", TEST_FILE_PATH.toString(), "-o", TEST_DIR.toString());
		processBuilder.directory(TARGET_DIR.toFile());
		Process process = processBuilder.start();
		printStandardInputAndStandardError(process);
		Assertions.assertEquals(0, process.exitValue());
	}

	@Test
	void runJarWithWrongParameters() throws IOException {
		ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", jarFile, "-i", TEST_DIR.toString(), "-o", TEST_DIR.toString());
		processBuilder.directory(TARGET_DIR.toFile());
		Process process = processBuilder.start();
		printStandardInputAndStandardError(process);
		Assertions.assertEquals(0, process.exitValue());
	}

	private void printStandardInputAndStandardError(final Process process) {
		final String stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()))
			.lines()
			.collect(Collectors.joining("\n"));
		System.out.println("Here is the standard output of the command:\n");
		System.out.println(stdInput);
		System.out.println();

		final String stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()))
			.lines()
			.collect(Collectors.joining("\n"));
		System.out.println("Here is the standard error of the command (if any):\n");
		System.out.println(stdError);
	}
}
