package de.fechtelhoff.thg.cli;

import java.nio.file.Path;
import java.util.Objects;
import de.fechtelhoff.thg.core.Core;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * Command Line Interface für die Umsetzung der Funktionalität aus dem Core-Modul.
 */
@SuppressWarnings("java:S106") // java:S106 -> Standard outputs should not be used directly to log anything
@Command(
	name = "thg-cli",
	description = "\nThe Holy Graal - Command Line Interface\n",
	footer = "\nCopyright(c) 2021",
	versionProvider = de.fechtelhoff.thg.cli.VersionProvider.class
)
public class CommandLineApplication implements Runnable {

	@Option(names = {"-i", "--input"}, description = "Input File")
	private String inputFile;

	@Option(names = {"-o", "--output"}, description = "Output Directory")
	private String outputDirectory;

	@Option(names = {"-v", "--version"}, versionHelp = true, description = "Version")
	private boolean version;

	@Option(names = {"-h", "--help"}, usageHelp = true, description = "Help")
	private boolean help;

	public static void main(final String[] args) {
		final int exitCode = new CommandLine(new CommandLineApplication()).execute(args);
		System.exit(exitCode);
	}

	@Override
	public void run() {
		if (Objects.nonNull(inputFile) && Objects.nonNull(outputDirectory)) {
			final Path inputFilePath = Path.of(inputFile);
			final Path outputDirectoryPath = Path.of(outputDirectory);
			new Core().reverseFile(inputFilePath, outputDirectoryPath);
		} else {
			usage();
		}
	}

	private void usage() {
		new CommandLine(new CommandLineApplication()).usage(System.out);
	}
}
