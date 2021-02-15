package de.fechtelhoff.thg.cli;

import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import com.ginsberg.junit.exit.ExpectSystemExitWithStatus;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommandLineApplicationTest {

	private static final Path TEST_DIR = Path.of("target/test-classes/test/cli");
	private static final Path TEST_FILE_PATH = TEST_DIR.resolve("testfile.txt");

	@Test
	@Order(1)
	@ExpectSystemExitWithStatus(0)
	void helpShortTest() {
		Assertions.assertThrows(Exception.class, () -> CommandLineApplication.main(new String[]{"-h"}));
	}

	@Test
	@Order(2)
	@ExpectSystemExitWithStatus(0)
	void helpLongTest() {
		Assertions.assertThrows(Exception.class, () -> CommandLineApplication.main(new String[]{"--help"}));
	}

	@Test
	@Order(3)
	@ExpectSystemExitWithStatus(0)
	void versionShortTest() {
		Assertions.assertThrows(Exception.class, () -> CommandLineApplication.main(new String[]{"-v"}));
	}

	@Test
	@Order(4)
	@ExpectSystemExitWithStatus(0)
	void versionLongTest() {
		Assertions.assertThrows(Exception.class, () -> CommandLineApplication.main(new String[]{"--version"}));
	}

	@Test
	@Order(5)
	@ExpectSystemExitWithStatus(0)
	void normalOperationShortTest() {
		final String inputFile = TEST_FILE_PATH.toAbsolutePath().toString();
		final String outputDirectory = TEST_DIR.toAbsolutePath().toString();
		Assertions.assertThrows(Exception.class, () -> CommandLineApplication.main(new String[]{"-i", inputFile, "-o", outputDirectory}));
	}

	@Test
	@Order(6)
	@ExpectSystemExitWithStatus(0)
	void normalOperationLongTest() {
		final String inputFile = TEST_FILE_PATH.toAbsolutePath().toString();
		final String outputDirectory = TEST_DIR.toAbsolutePath().toString();
		Assertions.assertThrows(Exception.class, () -> CommandLineApplication.main(new String[]{"--input", inputFile, "--output", outputDirectory}));
	}

	@Test
	@Order(7)
	@ExpectSystemExitWithStatus(0)
	void noInputTest() {
		Assertions.assertThrows(Exception.class, () -> CommandLineApplication.main(new String[]{}));
	}

	@Test
	@Order(8)
	@ExpectSystemExitWithStatus(0)
	void noInputForInputFileTest() {
		final String inputFile = TEST_FILE_PATH.toAbsolutePath().toString();
		Assertions.assertThrows(Exception.class, () -> CommandLineApplication.main(new String[]{"--input", inputFile}));
	}

	@Test
	@Order(9)
	@ExpectSystemExitWithStatus(0)
	void noInputForOutputDirectoryTest() {
		final String outputDirectory = TEST_DIR.toAbsolutePath().toString();
		Assertions.assertThrows(Exception.class, () -> CommandLineApplication.main(new String[]{"--output", outputDirectory}));
	}
}
