package presentation.model;

import java.util.Observable;

import domain.Book;
import domain.Customer;

public class TabBookModel extends Observable {

	private Book activeBook;
	private final ModelController controller;
	private boolean isEditing;
	private boolean isErrorAtTitle;
	private boolean isErrorAtPublisher;
	private boolean isErrorAtCondition;
	private Book lastBook;
	private Book backupBook;
	private boolean isErrorAtAuthor;

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
		if (getActiveBook() != null
				&& getActiveBook().getCondition() != Book.Condition.WASTE)
			return true;
		return false;
	}

	public void lendActiveBook() {
		Customer activeuser = controller.activeuser_model.getCustomer();
		if (isActiveBookLendableFor(activeuser))
			controller.library.createAndAddLoan(activeuser, getActiveBook());
	}

	private boolean isActiveBookLendableFor(Customer activeuser) {
		return (activeuser != null) && (getActiveBook() != null) && getActiveBook().getCondition() != Book.Condition.WASTE;
	}

	public boolean isBookActive() {
		return activeBook != null;
	}

	public void clearBook() {
		activeBook = null;
	}

	public boolean isEditing() {
		return isEditing;
	}

	public void setEditing(boolean b) {
		isEditing = b;
		fireDataChanged();
	}

	public void fireDataChanged() {
		setChanged();
		notifyObservers();
	}

	public void setErrorAtTitle(boolean b) {
		isErrorAtTitle = b;
	}
	
	public boolean isErrorAtTitle() {
		return isErrorAtTitle;
	}

	public boolean isErrorAtPublisher() {
		return isErrorAtPublisher;
	}

	public boolean isErrorAtCondition() {
		return isErrorAtCondition;
	}

	public void setLastBook(Book currentBook) {
		lastBook = currentBook;
	}

	public boolean isSameBook() {
		if (lastBook == null)
			return false;
		return lastBook.equals(getActiveBook());
	}

	public void backupBookContent() {
		backupBook = (Book)activeBook.clone();
	}

	public boolean restoreBookContent() {
		// TODO Feature envy: move into Title.class
		activeBook.getTitle().setName(backupBook.getTitle().getName());
		activeBook.getTitle().setAuthor(backupBook.getTitle().getAuthor());
		activeBook.getTitle().setPublisher(backupBook.getTitle().getPublisher());
		activeBook.setCondition(backupBook.getCondition());
		return true;
	}

	public boolean isError() {
		return isErrorAtTitle || isErrorAtAuthor() || isErrorAtPublisher || isErrorAtCondition;
	}

	public void setErrorAtAuthor(boolean isErrorAtAuthor) {
		this.isErrorAtAuthor = isErrorAtAuthor;
	}

	public boolean isErrorAtAuthor() {
		return isErrorAtAuthor;
	}

	public void setErrorAtPublisher(boolean b) {
		this.isErrorAtPublisher = b;
	}
}
