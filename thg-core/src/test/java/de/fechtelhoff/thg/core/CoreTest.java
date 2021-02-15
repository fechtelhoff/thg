package de.fechtelhoff.thg.core;

import java.nio.file.Path;
import java.util.Collections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CoreTest {

	private static final Path TEST_DIR = Path.of("target/test-classes/test/core");
	private static final Path TEST_FILE_PATH = TEST_DIR.resolve("testfile.txt");

	@Nested
	@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
	class Test_of_Method_reverseFile {

		Core core;

		@BeforeEach
		void beforeEach() {
			core = new Core();
		}

		@Test
		@Order(1)
		void with_valid_Parameters() {
			Assertions.assertDoesNotThrow(() -> core.reverseFile(TEST_FILE_PATH, TEST_DIR));
		}

		@Test
		@Order(2)
		void with_non_existing_inputFile() {
			Assertions.assertDoesNotThrow(() -> core.reverseFile(TEST_FILE_PATH.resolve("xyz"), TEST_DIR));
		}

		@Test
		@Order(3)
		void with_null_as_inputFile() {
			Assertions.assertDoesNotThrow(() -> core.reverseFile(null, TEST_DIR));
		}

		@Test
		@Order(4)
		void with_Directory_as_inputFile() {
			Assertions.assertDoesNotThrow(() -> core.reverseFile(TEST_FILE_PATH.getParent(), TEST_DIR));
		}

		@Test
		@Order(5)
		void with_non_existing_outputDirectory() {
			Assertions.assertDoesNotThrow(() -> core.reverseFile(TEST_FILE_PATH, TEST_DIR.resolve("xyz")));
		}

		@Test
		@Order(6)
		void with_null_as_outputDirectory() {
			Assertions.assertDoesNotThrow(() -> core.reverseFile(TEST_FILE_PATH, null));
		}

		@Test
		@Order(7)
		void with_File_as_outputDirectory() {
			Assertions.assertDoesNotThrow(() -> core.reverseFile(TEST_FILE_PATH, TEST_FILE_PATH));
		}
	}

	@Test
	void Test_of_Method_readFile() {
		Assertions.assertDoesNotThrow(() -> Core.readFile(TEST_DIR));
	}

	@Test
	void Test_of_Method_writeFile() {
		Assertions.assertDoesNotThrow(() -> Core.writeFile(TEST_DIR, Collections.emptyList()));
	}
}
