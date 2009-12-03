package presentation.model;

import java.util.Observable;

import domain.Customer;

/**
 * Stores the state of the active customer. The customer who stands at the
 * library desk and gives his name will become the active user so the librarian
 * can lend books for him/her.
 */
public class ActiveUserPanelModel extends Observable {

	private ControllerFacade controller;
	Customer active;

	public ActiveUserPanelModel(ControllerFacade controller) {
		this.controller = controller;
	}

	protected Customer getActiveCustomer() {
		return active;
	}

	protected void setActiveCustomer(Customer selected) {
		active = selected;
		fireDataChanged();
	}

	protected void resetActiveCustomer() {
		active = null;
		fireDataChanged();
		controller.resultlist_model.update();
	}

	protected boolean isCustomerActive() {
		return active != null;
	}

	public void fireDataChanged() {
		setChanged();
		notifyObservers();
	}

}
