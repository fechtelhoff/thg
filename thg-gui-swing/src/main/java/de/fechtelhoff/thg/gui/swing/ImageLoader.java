package de.fechtelhoff.thg.gui.swing;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * Hilfsklasse zum Laden von Images.
 */
@SuppressWarnings("java:S106") // java:S106 -> Standard outputs should not be used directly to log anything
public final class ImageLoader {

	private ImageLoader() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Läd ein Image relative zur Klassen Resource.
	 *
	 * @param clazz Klasse
	 * @param name Name des Images
	 * @return Das Image als {@link BufferedImage}
	 */
	public static BufferedImage loadImageFromClassResource(final Class<?> clazz, final String name) {
		BufferedImage image = null;
		try {
			final URL resource = clazz.getResource(name);
			image = ImageIO.read(resource);
		} catch (final IOException | IllegalArgumentException exception) {
			System.out.println(exception.getMessage());
		}
		return image;
	}
}
