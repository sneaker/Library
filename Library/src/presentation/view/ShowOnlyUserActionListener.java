package presentation.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import presentation.model.ModelController;

public class ShowOnlyUserActionListener implements ActionListener {

	private ModelController controller;

	public ShowOnlyUserActionListener(ModelController controller) {
		this.controller = controller;
	}
	
	public void actionPerformed(ActionEvent e) {
		controller.showUser();
	}

}
