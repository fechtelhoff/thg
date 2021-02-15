module de.fechtelhoff.thg.gui.javafx {
	requires javafx.controls;
	requires javafx.fxml;

	requires de.fechtelhoff.thg.core;
	requires de.fechtelhoff.thg.utils;
	requires de.fechtelhoff.thg.gui.shared;

	opens de.fechtelhoff.thg.gui.javafx;

	exports de.fechtelhoff.thg.gui.javafx;
}
