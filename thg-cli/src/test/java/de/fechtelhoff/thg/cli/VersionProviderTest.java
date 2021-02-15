package de.fechtelhoff.thg.cli;

import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class VersionProviderTest {

	@Test
	void getVersionTest() {
		String[] version = Assertions.assertDoesNotThrow(() -> new VersionProvider().getVersion());
		System.out.println(Arrays.toString(version));
		Assertions.assertTrue(version.length > 0);
		Assertions.assertTrue(version[0].startsWith("Version"));
	}
}
