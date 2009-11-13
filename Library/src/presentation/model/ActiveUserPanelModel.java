package presentation.model;

import java.util.Observable;

import domain.Customer;

public class ActiveUserPanelModel extends Observable {

	private ModelController controller;
	private Customer customer;
	
	public ActiveUserPanelModel(ModelController controller) {
		this.controller = controller;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public String getFullActiveCustomerName() {
		if (getCustomer() == null)
			return "";
		return getCustomer().getSurname() + ", " + getCustomer().getName();
	}

	public void setActiveUser(Customer selected) {
		customer = selected;
		fireDataChanged();
	}

	public void clearUser() {
		customer = null;
		fireDataChanged();
	}

	public void switchtoSearch() {
		controller.tabbed_model.setActiveTab(LibraryTabbedPaneModel.SEARCH_TAB);
	}

	public boolean isCustomerActive() {
		return customer != null;
	}

	public void fireDataChanged() {
		setChanged();
		notifyObservers();
	}
}
