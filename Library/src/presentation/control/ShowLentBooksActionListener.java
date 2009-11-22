package presentation.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import presentation.model.ModelController;

public class ShowLentBooksActionListener implements ActionListener {
	private final ModelController controller;

	public ShowLentBooksActionListener(ModelController controller) {
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		controller.searchtab_model.showLentBooks();
	}
}