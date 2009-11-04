package presentation.model;

import java.util.Observable;

import domain.Book;
import domain.Customer;

public class TabBookModel extends Observable {

	private Book activeBook;
	private final ModelController controller;

	public TabBookModel(ModelController controller) {
		this.controller = controller;
	}

	public void setActiveBook(Book activeBook) {
		this.activeBook = activeBook;
		setChanged();
		notifyObservers();
	}

	public Book getActiveBook() {
		return activeBook;
	}

	public String getStatus() {
		if (getActiveBook() == null)
			return "Buchdetails (kein Buch ausgewählt)";
		return "Anzeigen der Buchdetails von \""
				+ getActiveBook().getTitle().getName() + "\"";
	}

	public boolean isActiveBookLendable() {
		if (controller.activeuser_model.getCustomer() != null
				&& getActiveBook() != null
				&& getActiveBook().getCondition() != Book.Condition.WASTE
				&& !controller.library.isBookLent(getActiveBook()))
			return true;
		return false;
	}

	public boolean isActiveBookReturnable() {
		if (getActiveBook() != null
				&& controller.library.isBookLent(getActiveBook()))
			return true;
		return false;
	}

	public boolean isActiveBookNoWaste() {
		if (getActiveBook() != null && getActiveBook().getCondition() != Book.Condition.WASTE)
			return true;
		return false;
	}

	public void lendActiveBook() {
		Customer activeuser = controller.activeuser_model.getCustomer();
		if ((activeuser != null) && (getActiveBook() != null)) {
			controller.library.createAndAddLoan(activeuser, getActiveBook());
		} else if (activeuser == null) {
			// TODO: Show dialog "please select user first"
			controller.tabbed_model
					.setActiveTab(LibraryTabbedPaneModel.SEARCH_TAB);
		} else {
			// TODO: Show select book, no book activated
			controller.tabbed_model
					.setActiveTab(LibraryTabbedPaneModel.SEARCH_TAB);
		}
	}
}
