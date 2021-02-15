package de.fechtelhoff.thg.utils;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Hilfsklasse zur Ermittlung des Namens von Objekten.
 */
public final class ObjectName {

	private ObjectName() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Ermittelt den Namen eines Feldes.
	 *
	 * @param child Feld dessen Name ermittelt werden soll.
	 * @param parent Klasse in dem das Feld deklariert wird.
	 * @return Name des Feldes als String.
	 */
	public static String getFieldName(final Object child, final Object parent) {
		return Arrays.stream(parent.getClass().getDeclaredFields())
			.filter(field -> isEqual(field, child, parent))
			.map(Field::getName)
			.findFirst()
			.orElse(null);
	}

	@SuppressWarnings("java:S3011")// java:S3011 -> Reflection should not be used to increase accessibility of classes, methods, or fields
	private static Boolean isEqual(final Field field, Object child, final Object parent) {
		try {
			field.setAccessible(true);
			return field.get(parent).equals(child);
		} catch (final IllegalAccessException | NullPointerException exception) {
			return false;
		}
	}
}
