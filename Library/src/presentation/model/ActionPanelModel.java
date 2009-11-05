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
		// TODO Auto-generated method stub
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
	}

	public void createUser() {
		// TODO Auto-generated method stub
	}

	public void editUserSettings() {
		// TODO Auto-generated method stub
	}
}
