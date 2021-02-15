package de.fechtelhoff.thg.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectPropertiesTest {

	@Test
	@Order(1)
	void privateConstructorTest() {
		Assertions.assertThrows(InvocationTargetException.class, () -> {
				Constructor<ProjectProperties> constructor = ProjectProperties.class.getDeclaredConstructor();
				constructor.setAccessible(true);
				constructor.newInstance();
			}
		);
	}

	@Test
	@Order(2)
	void getProjectVersionTest() {
		Assertions.assertDoesNotThrow(() -> System.out.println(ProjectProperties.getProjectVersion()));
	}

	@Test
	@Order(3)
	void getProjectVersionSecondReadTest() {
		Assertions.assertDoesNotThrow(() -> System.out.println(ProjectProperties.getProjectVersion()));
	}
}
