package presentation.view;

import java.awt.Color;

import javax.swing.Action;
import javax.swing.KeyStroke;

import presentation.control.DisableGlassPaneListener;
import presentation.model.ModelController;
import util.TextUtils;

public class DialogFactory {
	static final int DIALOG_SIZE_MULTIPLICATOR = 28;
	static final int ASPECT_RATIO_Y = 9;
	static final int ASPECT_RATIO_X = 16;
	static final Color SHINY_BLUE = new Color(0xADD8E6);

	public static DialogChoice createAboutDialog(ModelController controller,
			String message) {
		final String[] buttonNames = new String[] { "&OK" };
		Action[] buttonActions = new Action[buttonNames.length];
		buttonActions[0] = new DisableGlassPaneListener(controller);

		KeyStroke[] buttonKeys = new KeyStroke[buttonNames.length];
		buttonKeys[0] = KeyStroke.getKeyStroke("ESCAPE");

		String dialogText = TextUtils
				.markupText(TextUtils
						.format(
								"<h2>BücherBox - Bücherverwaltung</h2>Ein Studenteprojekt realisiert durch Thomas Kallenberg und Martin Schwab an der HSR Rapperwil. <br />Dieses Projekt nutzt Bilder von Wikimedia Commons: http://commons.wikimedia.org/<br />(Nuvola- und Vista-Ion Set). ",
								16));
		return new DialogChoice(dialogText, buttonNames, buttonActions, buttonKeys);
	}

	/**
	 * Create a dialog showing different options.
	 * 
	 * @param message
	 *            The question to be posed
	 * @param options
	 *            The buttons wich should be shown to offer different options
	 * @param buttonActions
	 *            The actions which should be performed when clicking on a
	 *            button; must have the same size as options
	 * @param buttonKeys
	 *            keys can be null; must have the same size as options
	 * @return the dialog as a JPanel which can be displayed on the
	 *         rootGlassPane.
	 */
	public static DialogChoice createChoiceDialog(String message,
			String[] options, Action[] buttonActions, KeyStroke[] buttonKeys) {
		return new DialogChoice(message, options, buttonActions, buttonKeys);
	}
}
