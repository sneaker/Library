package presentation.model;

import java.util.Observable;

import domain.Customer;

public class ActiveUserPanelModel extends Observable {

	private ModelController controller;
	
	public ActiveUserPanelModel(ModelController controller) {
		this.controller = controller;
	}

	public void setNewActiveUser(Customer selected) {
		controller.main_model.fireDataChange();
		setChanged();
		notifyObservers(selected.getSurname() + " " + selected.getName());
	}

	public void clearUser() {
		controller.main_model.fireDataChange();
		setChanged();
		notifyObservers("none");
	}
}
