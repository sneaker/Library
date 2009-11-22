package presentation.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import presentation.model.ModelController;
import domain.Customer;

public class UserCreateActionListener implements ActionListener {
	private final ModelController controller;

	public UserCreateActionListener(ModelController controller) {
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		Customer add = controller.library.createAndAddCustomer("Neuer", "Benutzer");;
		add.setAdress("Strasse", 1234, "Ort");
		controller.activeuser_model.setActiveUser(add);
		controller.usertab_model.setEditing(true);
		controller.status_model.setTempStatus("Neuer Benutzer erstellt");
		controller.tabbed_model.setUserTabActive();
	}
}