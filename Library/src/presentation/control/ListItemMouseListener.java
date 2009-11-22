/**
 * 
 */
package presentation.control;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import presentation.model.ModelController;
import domain.Book;
import domain.Customer;
import domain.Loan;
import domain.Book.Condition;

public class ListItemMouseListener extends MouseAdapter {

	private ModelController controller;

	public ListItemMouseListener(ModelController controller) {
		this.controller = controller;
	}

	public void mouseClicked(MouseEvent e) {
		int index = controller.resultlist_model.getIndex();
		if (index < 0)
			return;

		Object o = controller.resultlist_model.getElementAt(index);
		if (o instanceof Book) {
			Book selected = (Book) o;
			handleBookClick(e, index, selected);
			return;
		}

		if (o instanceof Customer) {
			Customer selected = (Customer) o;
			handleUserClick(e, index, selected);
			return;
		}
	}

	private void handleUserClick(MouseEvent e, int index, Customer selected) {
		if (controller.resultlist_model.isFirstIconHit(e)) {
			if (controller.activeuser_model.isCustomerActive()
					&& controller.activeuser_model.getCustomer() == (selected))
				controller.activeuser_model.setActiveUser(null);
			else
				controller.activeuser_model.setActiveUser(selected);
			controller.resultlist_model.fireDataChanged(this, index);
		} else {
			controller.activeuser_model.setActiveUser(selected);
			controller.tabbed_model.setUserTabActive();
		}
	}

	public void loanClicked(MouseEvent e, int index) {
		if (index < 0)
			return;

		Object o = controller.loanModel.getElementAt(index);
		if (!(o instanceof Loan))
			return;
		
		handleLoanClick(e, index, (Loan)o);
	}

	private void handleLoanClick(MouseEvent e, int index, Loan selected) {
		if (isSecondIconHit(e, index) && controller.library.isBookLent(selected.getBook())) {
			controller.library.returnBook(selected.getBook());
			return;
		}
	}

	// Duplicated from resultlistmodel
	private boolean isSecondIconHit(MouseEvent e, int index) {
		if (e.getX() < 110 || e.getX() > (110 + 32))
			return false;
		if ((e.getY() < 30 + 64 * index)
				|| e.getY() > 62 + 64 * index)
			return false;
		return true;
	}

	/**
	 * Determines where an item has been clicked and executes the appropriate
	 * action. JList items cannot listen to events so the list itself listens
	 * for events for its items.
	 * 
	 * @param e
	 *            gives information about the mouse event including its
	 *            coordinates to calculate the clicked item.
	 * @param index
	 *            of the list item.
	 * @param selected
	 *            the book or user which is affected by this click.
	 */
	private void handleBookClick(MouseEvent e, int index, Book selected) {
		controller.booktab_model.setActiveBook(selected);
		if (controller.resultlist_model.isFirstIconHit(e)) {
			if (selected.getCondition() == Condition.WASTE || controller.activeuser_model.getCustomer() == null || controller.library.isCustomerLocked(controller.activeuser_model.getCustomer())) {
				showDetailsOf(selected);
				return;
			}
			if (controller.library.isBookLent(selected)) {
				controller.library.reserveBook(selected);
			} else {
				lendBook(selected);
			}
			controller.resultlist_model.update();
			return;
		}
		if (controller.resultlist_model.isSecondIconHit(e) && controller.library.isBookLent(selected)) {
			controller.library.returnBook(selected);
			controller.resultlist_model.update();
			return;
		}

		showDetailsOf(selected);
	}

	private void lendBook(Book selected) {
		if (controller.activeuser_model.getCustomer() == null) {
			controller.booktab_model.setActiveBook(selected);
			controller.status_model
					.setTempStatus("Keine Ausleihe möglich: Bitte erst Benutzer auswählen");
			return;
		}
		controller.library.createAndAddLoan(controller.activeuser_model
				.getCustomer(), selected);
	}

	private void showDetailsOf(Book selected) {
		controller.booktab_model.setActiveBook(selected);
		controller.tabbed_model.setBookTabActive();
	}
}