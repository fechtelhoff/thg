package de.fechtelhoff.thg.gui.swing;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ImageLoaderTest {

	@Test
	void privateConstructorTest() {
		Assertions.assertThrows(InvocationTargetException.class, () -> {
				Constructor<ImageLoader> constructor = ImageLoader.class.getDeclaredConstructor();
				constructor.setAccessible(true);
				constructor.newInstance();
			}
		);
	}

	@Test
	void loadImageFromClassResourceTest() {
		Assertions.assertDoesNotThrow(() -> ImageLoader.loadImageFromClassResource(this.getClass(), "/nothing"));
	}
}
