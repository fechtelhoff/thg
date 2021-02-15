package de.fechtelhoff.thg.cli;

import de.fechtelhoff.thg.utils.ProjectProperties;
import picocli.CommandLine;

/**
 * Stellt die Version des Projektes für Picocli (CLI Framework) zur Verfügung.
 */
public class VersionProvider implements CommandLine.IVersionProvider {

	public String[] getVersion() {
		final String projectVersion = ProjectProperties.getProjectVersion();
		final String programVersion = "Version " + projectVersion;
		return new String[]{programVersion};
	}
}
