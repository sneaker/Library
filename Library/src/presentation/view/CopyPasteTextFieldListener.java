/**
 * 
 */
package presentation.view;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.text.JTextComponent;

import presentation.model.ModelController;

public final class CopyPasteTextFieldListener extends MouseAdapter {
	/**
	 * 
	 */
	private final String title;
	private final JTextComponent what;
	private final ModelController controller;

	public CopyPasteTextFieldListener(String title, JTextComponent what, ModelController controller) {
		this.title = title;
		this.what = what;
		this.controller = controller;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JPopupMenu pop = new JPopupMenu();
		JMenuItem menuItem = new JMenuItem(title);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clipboard clipboard;
				clipboard = Toolkit.getDefaultToolkit()
						.getSystemClipboard();
				StringSelection newcontents = new StringSelection(what
						.getText());
				clipboard.setContents(newcontents, controller.main_model);
			}
		});
		pop.add(menuItem);
		pop.show(what, e.getX(), e.getY());
		pop.setVisible(true);
	}
}