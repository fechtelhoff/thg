package de.fechtelhoff.thg.gui.swing;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import de.fechtelhoff.thg.core.Core;
import de.fechtelhoff.thg.gui.shared.GuiShared;
import de.fechtelhoff.thg.utils.ObjectName;
import de.fechtelhoff.thg.utils.ProjectProperties;

/**
 * Einfaches Swing GUI welches die Funktionalität des Core-Moduls abbildet.
 * <p/>
 * Damit diese Klasse auch mit Maven ein ausführbares Ergebnis (JAR with Dependencies) liefert muss:
 * <ul>
 *     <li>
 *         die Dependency <a href="https://mvnrepository.com/artifact/com.intellij/forms_rt">forms_rt</a> eingebunden werden,
 *     </li>
 *     <li>
 *         in den IDEA Settings (Strg + Alt + S) unter "Editor -> GUI Designer" die Einstellung von "Generate GUI into"
 *         auf "Java source code" gesetzt werden. Damit wird die Initialisierung der Komponenten automatisch in die Klasse generiert.
 *     </li>
 * </ul>
 * <p/>
 * Nicht schön aber selten und "<b>It works</b>".
 */

// java:S100 -> Method names should comply with a naming convention
// java:S106 -> Standard outputs should not be used directly to log anything
// java:S1068 -> Unused "private" fields should be removed
// java:S1171 -> Only static class initializers should be used
@SuppressWarnings({"java:S100", "java:S106", "java:S1068", "java:S1171"})
public class SwingGui {

	private static final String APPLICATION_NAME = "The Holy Graal - Version " + ProjectProperties.getProjectVersion();

	private Path inputFilePath;
	private Path outputDirectoryPath;

	private JFrame frame;
	private JPanel contentPane;

	private JTextField textFieldInputFile;
	private JButton buttonOpenFileChooser4InputFile;

	private JTextField textFieldOutputDirectory;
	private JButton buttonOpenFileChooser4OutputDirectory;

	private JButton buttonExecute;
	private JButton buttonClose;

	private JTextArea textAreaOutput;
	private JButton buttonClearOutput;

	public static void main(final String[] args) {
		SwingUtilities.invokeLater(SwingGui::new);
	}

	public SwingGui() {
		initializeFrame();
		initializePanels();
		initializeButtons();
		initializeTextFields();
		initializeTextArea();
	}

	public JFrame getFrame() {
		return frame;
	}

	private void initializeFrame() {
		frame = new JFrame(APPLICATION_NAME);
		setNameForComponent(frame);
		frame.setContentPane(contentPane);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setIconImage(ImageLoader.loadImageFromClassResource(this.getClass(), "/logo.png"));
		frame.setSize(new Dimension(600, 400));
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void initializePanels() {
		setNameForComponent(contentPane);
	}

	private void initializeButtons() {
		buttonExecute.addActionListener(this::executeAction);
		setNameForComponent(buttonExecute);
		buttonClose.addActionListener(this::closeAction);
		setNameForComponent(buttonClose);
		buttonOpenFileChooser4InputFile.addActionListener(this::fileChooserInputFileAction);
		setNameForComponent(buttonOpenFileChooser4InputFile);
		buttonOpenFileChooser4OutputDirectory.addActionListener(this::fileChooserOutputDirectoryAction);
		setNameForComponent(buttonOpenFileChooser4OutputDirectory);
		buttonClearOutput.addActionListener(this::clearOutputAction);
		setNameForComponent(buttonClearOutput);
	}

	private void initializeTextFields() {
		setNameForComponent(textFieldInputFile);
		setNameForComponent(textFieldOutputDirectory);
		setNameForComponent(textAreaOutput);
	}

	private void initializeTextArea() {
		GuiShared.redirectSystemStreams(this::updateTextArea);
		System.out.println("Start ...");
	}

	private void executeAction(final ActionEvent event) {
		if (Objects.nonNull(inputFilePath) && Objects.nonNull(outputDirectoryPath)) {
			GuiShared.printSeparatorLine();
			new Core().reverseFile(inputFilePath, outputDirectoryPath);
			enableOrDisableClearOutputButton();
		}
	}

	private void closeAction(final ActionEvent event) {
		System.exit(0);
	}

	private void clearOutputAction(final ActionEvent event) {
		textAreaOutput.setText(null);
		enableOrDisableClearOutputButton();
	}

	private void fileChooserInputFileAction(final ActionEvent event) {
		final File openInDirectory = GuiShared.getDirectoryToOpenIn(inputFilePath);
		Optional<String> maybeInputFile = fileChooserAction("fileChooserInputFile", JFileChooser.FILES_ONLY, openInDirectory);
		if (maybeInputFile.isPresent()) {
			final String selectedFile = maybeInputFile.get();
			inputFilePath = Path.of(selectedFile);
			textFieldInputFile.setText(selectedFile);
		}
		enableOrDisableExecuteButton();
	}

	private void fileChooserOutputDirectoryAction(final ActionEvent event) {
		final File openInDirectory;
		if (Objects.nonNull(outputDirectoryPath)) {
			openInDirectory = GuiShared.getDirectoryToOpenIn(outputDirectoryPath);
		} else {
			openInDirectory = GuiShared.getDirectoryToOpenIn(inputFilePath);
		}
		Optional<String> maybeOutputDirectory = fileChooserAction("fileChooserOutputDirectory", JFileChooser.DIRECTORIES_ONLY, openInDirectory);
		if (maybeOutputDirectory.isPresent()) {
			final String selectedDirectory = maybeOutputDirectory.get();
			outputDirectoryPath = Path.of(selectedDirectory);
			textFieldOutputDirectory.setText(selectedDirectory);
		}
		enableOrDisableExecuteButton();
	}

	private Optional<String> fileChooserAction(final String name, final int fileSelectionMode, final File openInDirectory) {
		final String selectedFile;
		final JFileChooser fileChooser = new JFileChooser(openInDirectory);
		fileChooser.setFileSelectionMode(fileSelectionMode);
		fileChooser.setName(name);
		final int returnVal = fileChooser.showOpenDialog(frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile().getAbsolutePath();
		} else {
			selectedFile = null;
		}
		return Optional.ofNullable(selectedFile);
	}

	private void enableOrDisableExecuteButton() {
		buttonExecute.setEnabled(Objects.nonNull(inputFilePath) && Objects.nonNull(outputDirectoryPath));
	}

	private void enableOrDisableClearOutputButton() {
		buttonClearOutput.setEnabled(Objects.nonNull(textAreaOutput.getText()));
	}

	private void setNameForComponent(final Container container) {
		container.setName(ObjectName.getFieldName(container, this));
	}

	private void updateTextArea(final String text) {
		SwingUtilities.invokeLater(() -> textAreaOutput.append(text));
	}

	{
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
		$$$setupUI$$$();
	}

	/**
	 * Method generated by IntelliJ IDEA GUI Designer
	 * >>> IMPORTANT!! <<<
	 * DO NOT edit this method OR call it in your code!
	 *
	 * @noinspection ALL
	 */
	private void $$$setupUI$$$() {
		contentPane = new JPanel();
		contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(5, 5, 5, 5), -1, -1));
		final JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
		contentPane
			.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		textFieldInputFile = new JTextField();
		textFieldInputFile.setEditable(false);
		panel1
			.add(textFieldInputFile, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
		buttonOpenFileChooser4InputFile = new JButton();
		buttonOpenFileChooser4InputFile.setText("...");
		panel1
			.add(buttonOpenFileChooser4InputFile, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final JLabel label1 = new JLabel();
		label1.setText("Ausgabeverzeichnis:");
		panel1
			.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		textFieldOutputDirectory = new JTextField();
		textFieldOutputDirectory.setEditable(false);
		panel1
			.add(textFieldOutputDirectory, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
		buttonOpenFileChooser4OutputDirectory = new JButton();
		buttonOpenFileChooser4OutputDirectory.setText("...");
		panel1
			.add(buttonOpenFileChooser4OutputDirectory, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
		panel1
			.add(panel2, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		buttonExecute = new JButton();
		buttonExecute.setEnabled(false);
		Font buttonExecuteFont = this.$$$getFont$$$(null, -1, -1, buttonExecute.getFont());
		if (buttonExecuteFont != null) {
			buttonExecute.setFont(buttonExecuteFont);
		}
		buttonExecute.setText("Ausführen");
		panel2
			.add(buttonExecute, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 3, false));
		buttonClose = new JButton();
		buttonClose.setText("Beenden");
		panel2
			.add(buttonClose, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final JLabel label2 = new JLabel();
		label2.setText("Eingabedatei:");
		panel1
			.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final JPanel panel3 = new JPanel();
		panel3.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
		contentPane
			.add(panel3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
		final JScrollPane scrollPane1 = new JScrollPane();
		panel3
			.add(scrollPane1, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		textAreaOutput = new JTextArea();
		textAreaOutput.setBackground(new Color(-1));
		textAreaOutput.setEditable(false);
		scrollPane1.setViewportView(textAreaOutput);
		buttonClearOutput = new JButton();
		buttonClearOutput.setEnabled(true);
		buttonClearOutput.setText("Ausgabe löschen");
		panel3
			.add(buttonClearOutput, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
	}

	/** @noinspection ALL */
	private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
		if (currentFont == null) {
			return null;
		}
		String resultName;
		if (fontName == null) {
			resultName = currentFont.getName();
		} else {
			Font testFont = new Font(fontName, Font.PLAIN, 10);
			if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
				resultName = fontName;
			} else {
				resultName = currentFont.getName();
			}
		}
		Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
		boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
		Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext()
			.getFont(font.getFamily(), font.getStyle(), font.getSize());
		return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
	}

	/** @noinspection ALL */
	public JComponent $$$getRootComponent$$$() {
		return contentPane;
	}
}
