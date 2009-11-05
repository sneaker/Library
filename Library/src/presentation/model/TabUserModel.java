package presentation.model;

import java.util.Observable;

import domain.Customer;

public class TabUserModel extends Observable {

	private Customer activeCustomer;

	public TabUserModel(ModelController model) {
	}

	public void setActiveCustomer(Customer activeCustomer) {
		this.activeCustomer = activeCustomer;
		setChanged();
		notifyObservers();
	}

	public Customer getActiveCustomer() {
		return activeCustomer;
	}

	public String getStatus() {
		if (getActiveCustomer() == null)
			return "Benutzerdetails (kein Benutzer ausgewählt)";
		return "Anzeigen der Benutzerdetails von \""
				+ getActiveCustomer().getSurname() + ", "
				+ getActiveCustomer().getName() + "\"";
	}
}
