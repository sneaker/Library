package presentation.model;

import java.util.Observable;


import domain.Book;
import domain.Customer;
import domain.Library;

public class ActionPanelModel extends Observable {

	private ModelController controller;
	
	//TODO: Needs application as argument to do the actions
	public ActionPanelModel(ModelController controller) {
		this.controller = controller;
	}
	
	public void update(Observable o, Object arg) {
		//TODO: Does not update the list on the view
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
		if (activeuser == null) {
			//TODO: Show select user first
			controller.tabbed_model.setActiveTab(LibraryTabbedPaneModel.SEARCH_TAB);
		} else if (activebook == null){
			//TODO: Show select book then
			controller.tabbed_model.setActiveTab(LibraryTabbedPaneModel.SEARCH_TAB);
		} else {
			controller.library.createAndAddLoan(activeuser, activebook);
		}
	}

	public void createUser() {
		// TODO Auto-generated method stub
		
	}

	public void editUserSettings() {
		// TODO Auto-generated method stub
		
	}
}
