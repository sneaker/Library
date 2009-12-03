package presentation.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import presentation.model.ControllerFacade;

public class ShowAvailableBooksAction implements ActionListener {
	private final ControllerFacade controller;

	public ShowAvailableBooksAction(ControllerFacade controller) {
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		controller.showAvailableBooks();
	}
}