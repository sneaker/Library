package presentation.model;

import java.util.Observable;

import domain.Customer;

public class ActiveUserPanelModel extends Observable {

	private ModelController controller;
	Customer customer;
	
	public ActiveUserPanelModel(ModelController controller) {
		this.controller = controller;
	}
	
	public Customer getActiveCustomer() {
		return customer;
	}
	
	public String getFullActiveCustomerName() {
		if (customer == null)
			return "";
		return customer.getSurname() + ", " + customer.getName();
	}

	public void setActiveUser(Customer selected) {
		customer = selected;
		fireDataChanged();
	}

	public void clearUser() {
		customer = null;
		fireDataChanged();
		controller.resultlist_model.update();
	}

	public void switchtoSearch() {
		controller.tabbed_model.setSearchTabActive();
	}

	public boolean isCustomerActive() {
		return customer != null;
	}

	public void fireDataChanged() {
		setChanged();
		notifyObservers();
	}

}
