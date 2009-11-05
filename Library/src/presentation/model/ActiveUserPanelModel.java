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

	public void setNewActiveUser(Customer selected) {
		customer = selected;
		controller.main_model.fireDataChange();
		setChanged();
		notifyObservers();
	}

	public void clearUser() {
		customer = null;
		controller.main_model.fireDataChange();
		setChanged();
		notifyObservers();
	}

	public void switchtoSearch() {
		controller.tabbed_model.setActiveTab(LibraryTabbedPaneModel.SEARCH_TAB);
	}
}
