/**
 * 
 */
package presentation.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import presentation.model.ModelController;

public class ShowAvailableBooksAction implements ActionListener {
	private final ModelController controller;

	public ShowAvailableBooksAction(ModelController controller) {
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		controller.searchtab_model.showavailableBooks();
	}
}