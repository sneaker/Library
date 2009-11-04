package presentation.model;

import java.util.Observable;

import domain.Book;
import domain.Customer;

public class ActionPanelModel extends Observable {

	private ModelController controller;

	// TODO: Needs application as argument to do the actions
	public ActionPanelModel(ModelController controller) {
		this.controller = controller;
	}

	public void update(Observable o, Object arg) {
		// TODO: Does not update the list on the view
		setChanged();
		notifyObservers();
	}

	public void changetoSearch() {
		controller.tabbed_model.setActiveTab(LibraryTabbedPaneModel.SEARCH_TAB);
		setChanged();
		notifyObservers();
	}

	public void createNewUser() {
		// TODO Auto-generated method stub

	}

	public void markDefekt() {
		// TODO Auto-generated method stub
	}

	public void lendBook() {
		Customer activeuser = controller.activeuser_model.getCustomer();
		Book activebook = controller.booktab_model.getActiveBook();
		if ((activeuser != null) && (activebook != null)) {
			controller.library.createAndAddLoan(activeuser, activebook);
		} else if (activeuser == null) {
			// TODO: Show select user first
			controller.tabbed_model
					.setActiveTab(LibraryTabbedPaneModel.SEARCH_TAB);
		} else {
			// TODO: Show select book, no book activated
			controller.tabbed_model
					.setActiveTab(LibraryTabbedPaneModel.SEARCH_TAB);
		}
	}

	public void createUser() {
		// TODO Auto-generated method stub

	}

	public void editUserSettings() {
		// TODO Auto-generated method stub

	}

	public void returnBook() {
		// TODO Auto-generated method stub

	}

	public void creaNewBook() {
		// TODO Auto-generated method stub

	}

	public void fireDataChanged() {
		setChanged();
		notifyObservers();
	}
}
