/**
 * 
 */
package presentation.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import presentation.model.ModelController;

public class NewSearchAction implements ActionListener {
	private final ModelController controller;

	public NewSearchAction(ModelController controller) {
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		controller.setSearchTabActive();
		controller.resetSearchText();
	}
}