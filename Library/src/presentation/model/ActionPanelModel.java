package presentation.model;

import java.util.Observable;

import domain.Book;

public class ActionPanelModel extends Observable {

	private ModelController controller;

	// TODO: Needs application as argument to do the actions
	public ActionPanelModel(ModelController controller) {
		this.controller = controller;
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
		if (controller.booktab_model.getActiveBook() == null)
			return;
		controller.booktab_model.getActiveBook().setCondition(Book.Condition.WASTE);
		controller.status_model.setTempStatus("Buch wurde ausgemustert: " + controller.booktab_model.getActiveBook().getTitle().getName());
	}

	public void lendBook() {
		controller.booktab_model.lendActiveBook();
		fireDataChanged();
		controller.status_model.setTempStatus("Buch wurde ausgeliehen: " + controller.booktab_model.getActiveBook().getTitle().getName() + getCustomerName("für: "));
	}

	public void createUser() {
		// TODO Auto-generated method stub

	}

	public void editUserSettings() {
		// TODO Auto-generated method stub

	}

	public void returnBook() {
		controller.library.returnBook(controller.booktab_model.getActiveBook());
		fireDataChanged();
		controller.status_model.setTempStatus("Zurück in der Bibliothek: " + controller.booktab_model.getActiveBook().getTitle().getName() + getCustomerName("von: "));
	}

	private String getCustomerName(String vor) {
		if (controller.activeuser_model.getCustomer() == null)
			return "";
		else
			return " (" + vor + controller.activeuser_model.getFullActiveCustomerName() + ")";
	}

	public void creaNewBook() {
		// TODO Auto-generated method stub

	}

	public void fireDataChanged() {
		setChanged();
		notifyObservers();
	}
}
