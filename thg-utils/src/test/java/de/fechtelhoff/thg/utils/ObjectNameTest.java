package de.fechtelhoff.thg.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@SuppressWarnings("FieldCanBeLocal")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ObjectNameTest {

	private final String stringFieldName = "stringField";
	private final String stringField = "xyz";

	private final String integerFieldName = "integerField";
	private final Integer integerField = 100;

	private final String booleanFieldName = "booleanField";
	private final Boolean booleanField = true;

	@Test
	@Order(1)
	void privateConstructorTest() {
		Assertions.assertThrows(InvocationTargetException.class, () -> {
				Constructor<ObjectName> constructor = ObjectName.class.getDeclaredConstructor();
				constructor.setAccessible(true);
				constructor.newInstance();
			}
		);
	}

	@Test
	@Order(2)
	void getFieldName4String() {
		getFieldName(stringField, stringFieldName);
	}

	@Test
	@Order(3)
	void getFieldName4Integer() {
		getFieldName(integerField, integerFieldName);
	}

	@Test
	@Order(4)
	void getFieldName4Boolean() {
		getFieldName(booleanField, booleanFieldName);
	}

	private void getFieldName(final Object object, final String name) {
		final String fieldName = ObjectName.getFieldName(object, this);
		System.out.println(fieldName);
		assertEquals(name, fieldName);
	}
}
