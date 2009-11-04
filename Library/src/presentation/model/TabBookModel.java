package presentation.model;

import java.util.Observable;

import domain.Book;

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

	public boolean isActiveBookMarkableAsWaste() {
		if (getActiveBook() != null && getActiveBook().getCondition() != Book.Condition.WASTE)
			return true;
		return false;
	}
}
