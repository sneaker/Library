package presentation.model;

import java.util.Observable;

import domain.Customer;

public class TabUserModel extends Observable {

	private final ModelController controller;

	public TabUserModel(ModelController controller) {
		this.controller = controller;
	}
	
	private Customer getActiveCustomer() {
		return controller.activeuser_model.getCustomer();
	}

	public String getStatus() {
		if (getActiveCustomer() == null)
			return "Benutzerdetails (kein Benutzer ausgewählt)";
		return "Anzeigen der Benutzerdetails von \""
				+ getActiveCustomer().getSurname() + ", "
				+ getActiveCustomer().getName() + "\"";
	}
}
