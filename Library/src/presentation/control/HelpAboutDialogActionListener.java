package presentation.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.KeyStroke;

import presentation.model.ModelController;
import util.TextUtils;
import domain.Message;

/**
 * Tells the responsible model to set the "About" dialog active whenever this
 * action occurs.
 */
public class HelpAboutDialogActionListener implements ActionListener {
	private static final String ABOUT_TEXT = "<h2>BücherBox - Bücherverwaltung</h2>Ein Studenteprojekt realisiert durch Thomas Kallenberg und Martin Schwab an der HSR Rapperwil. <br />Dieses Projekt nutzt Bilder von Wikimedia Commons: http://commons.wikimedia.org/<br />(Nuvola- und Vista-Ion Set). ";
	private final ModelController controller;

	public HelpAboutDialogActionListener(ModelController controller) {
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		final String[] buttonNames = new String[] { "&OK" };
		Action[] buttonActions = new Action[buttonNames.length];
		buttonActions[0] = new DisableGlassPaneListener(controller);

		KeyStroke[] buttonKeys = new KeyStroke[buttonNames.length];
		buttonKeys[0] = KeyStroke.getKeyStroke("ESCAPE");

		String text = TextUtils.markupText(TextUtils.format(ABOUT_TEXT, 16));
		Message msg = new Message(text, buttonNames, buttonActions, buttonKeys);
		controller.main_model.addActiveMessage(msg);
	}
}