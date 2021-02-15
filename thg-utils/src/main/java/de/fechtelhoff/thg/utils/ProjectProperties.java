package de.fechtelhoff.thg.utils;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * Hilfsklasse die projektweite Einstellungen zur Verfügung stellt.
 */
public final class ProjectProperties {

	private static final String PROJECT_VERSION_PROPERTY_NAME = "projectVersion";

	private static String projectVersion;

	private ProjectProperties() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Liest die Version aus dem POM des Projektes.
	 * <p>
	 * Gelesen wird der Wert aus der Maven-Variable: ${project.version}
	 * <p>
	 * Über Maven Resource Filtering wird die Version in die Datei <code>src\main\resources\project.properties</code> bzw. dann
	 * in die Datei <code>target\classes\project.properties</code> geschrieben von wo der Wert über Properties gelesen werden kann.
	 *
	 * @return Version des Projektes
	 */
	public static String getProjectVersion() {
		if (Objects.isNull(projectVersion)) {
			projectVersion = readProjectVersion();
		}
		return projectVersion;
	}

	private static String readProjectVersion() {
		try {
			final Properties properties = new Properties();
			properties.load(ProjectProperties.class.getClassLoader().getResourceAsStream("project.properties"));
			return properties.getProperty(PROJECT_VERSION_PROPERTY_NAME);
		} catch (final IOException exception) {
			throw new IllegalStateException("Die 'project.properties' konnten nicht geladen werden.", exception);
		}
	}
}
