package presentation.model;

import java.util.Observable;

import domain.Book;
import domain.Customer;

public class ActionPanelModel extends Observable {

	private ModelController controller;
	
	public ActionPanelModel(ModelController controller) {
		this.controller = controller;
	}
	
	public void update(Observable o, Object arg) {
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
		if (controller.booktab_model.getActiveBook() == null)
			return;
		controller.booktab_model.getActiveBook().setCondition(Book.Condition.WASTE);
		controller.status_model.setTempStatus("Buch wurde ausgemustert: " + controller.booktab_model.getActiveBook().getTitle().getName());
	}

	public void lendBook() {
		Customer activeuser = controller.activeuser_model.getCustomer();
		Book activebook = controller.booktab_model.getActiveBook();
		if ((activeuser != null) && (activebook != null)) 
		{
			controller.library.createAndAddLoan(activeuser, activebook);
		} 
		//TODO: [Thomas] Inform the user about error via statusbar
		else if (activeuser == null)
		{
			controller.tabbed_model.setActiveTab(LibraryTabbedPaneModel.SEARCH_TAB);
		} 
		else 
		{
			controller.tabbed_model.setActiveTab(LibraryTabbedPaneModel.SEARCH_TAB);
		}
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

	public void showavailableBooks() {
		controller.searchtab_model.showavailableBooks();
	}

	public void showDefektBooks() {
		controller.searchtab_model.showDefektBooks();
	}

	public void showLentBooks() {
		controller.searchtab_model.showLentBooks();
	}

	public void resetSearch() {
		controller.searchtab_model.resetSearchText();
	}

	public void clearuser() {
		controller.activeuser_model.clearUser();
	}

	public void showUser() {
		controller.searchtab_model.showUser();
	}
}
