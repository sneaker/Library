package presentation.model;

import java.util.Observable;

import domain.Book;
import domain.Customer;
import domain.Book.Condition;

public class TabBookModel extends Observable {

	private Book activeBook;
	private final ControllerFacade controller;
	private boolean isEditing;
	private boolean isErrorAtTitle;
	private boolean isErrorAtPublisher;
	private boolean isErrorAtCondition;
	private Book lastBook;
	private Book backupBook;
	private boolean isErrorAtAuthor;

	public TabBookModel(ControllerFacade controller) {
		this.controller = controller;
	}

	protected void setActiveBook(Book activeBook) {
		this.activeBook = activeBook;
		fireDataChanged();
	}

	protected Book getActiveBook() {
		return activeBook;
	}

	public String getStatus() {
		if (getActiveBook() == null)
			return "Buchdetails (kein Buch ausgewählt)";
		return "Anzeigen der Buchdetails von \""
				+ getActiveBook().getTitle().getName() + "\"";
	}

	public boolean isActiveBookLendable() {
		if (controller.getActiveCustomer() != null
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

	protected void lendActiveBook() {
		Customer activeuser = controller.getActiveCustomer();
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
		fireDataChanged();
	}

	public boolean isEditing() {
		return isEditing;
	}

	public void setEditing(boolean b) {
		isEditing = b;
		if (!isEditing) {
			isErrorAtTitle = false;
			isErrorAtAuthor = false;
			isErrorAtPublisher = false;
			isErrorAtCondition = false;
		}
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
		backupBook = activeBook.clone();
	}

	public boolean restoreBookContent() {
		activeBook.copyContentFrom(backupBook);
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

	public void resetConditionCombo(Condition condition) {
		setChanged();
		notifyObservers(condition);
	}
}
