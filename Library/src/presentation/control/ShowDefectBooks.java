package presentation.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import presentation.model.ControllerFacade;

public class ShowDefectBooks implements ActionListener {
	private final ControllerFacade controller;

	public ShowDefectBooks(ControllerFacade controller) {
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		controller.showDefektBooks();
	}
}