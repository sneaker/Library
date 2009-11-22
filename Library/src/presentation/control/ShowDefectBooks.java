/**
 * 
 */
package presentation.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import presentation.model.ModelController;

public class ShowDefectBooks implements ActionListener {
	private final ModelController controller;

	public ShowDefectBooks(ModelController controller) {
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		controller.showDefektBooks();
	}
}