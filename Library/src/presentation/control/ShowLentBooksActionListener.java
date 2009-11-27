package presentation.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import presentation.model.ControllerFacade;

public class ShowLentBooksActionListener implements ActionListener {
	private final ControllerFacade controller;

	public ShowLentBooksActionListener(ControllerFacade controller) {
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		controller.showLentBooks();
	}
}