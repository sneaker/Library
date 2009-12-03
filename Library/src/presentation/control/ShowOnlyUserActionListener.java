package presentation.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import presentation.model.ControllerFacade;

public class ShowOnlyUserActionListener implements ActionListener {

	private ControllerFacade controller;

	public ShowOnlyUserActionListener(ControllerFacade controller) {
		this.controller = controller;
	}
	
	public void actionPerformed(ActionEvent e) {
		controller.showUser();
	}

}
