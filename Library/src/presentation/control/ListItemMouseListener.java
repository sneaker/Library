/**
 * 
 */
package presentation.control;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import presentation.model.ModelController;
import util.TextUtils;
import domain.Book;
import domain.Customer;
import domain.Loan;
import domain.Message;
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
					&& controller.getActiveCustomer() == (selected))
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
			if (selected.getCondition() == Condition.WASTE || controller.getActiveCustomer() == null || controller.library.isCustomerLocked(controller.getActiveCustomer())) {
				showDetailsOf(selected);
				return;
			}
			if (controller.library.isBookLent(selected)) {
				showNotImplementedMsg();
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

	private void showNotImplementedMsg() {
		Action[] actions = new Action[1]; 
		actions[0] = new DisableGlassPaneListener(controller);
		String[] buttonnames = new String[] { "&Ok" };
		KeyStroke[] buttonkeys = new KeyStroke[1];
		buttonkeys[0] = KeyStroke.getKeyStroke("ENTER");
		String dialogText = TextUtils.markupText(TextUtils.format("Die Reservationsfunktion wurde noch nicht implementiert", 16));
		Message msg = new Message(dialogText, buttonnames, actions, buttonkeys);
		controller.main_model.setActiveMessage(msg);
	}

	private void lendBook(Book selected) {
		if (controller.getActiveCustomer() == null) {
			controller.booktab_model.setActiveBook(selected);
			controller.status_model
					.setTempStatus("Keine Ausleihe möglich: Bitte erst Benutzer auswählen");
			return;
		}
		controller.library.createAndAddLoan(controller.getActiveCustomer(), selected);
	}

	private void showDetailsOf(Book selected) {
		controller.booktab_model.setActiveBook(selected);
		controller.tabbed_model.setBookTabActive();
	}
}
