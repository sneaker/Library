/**
 * 
 */
package presentation.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import presentation.model.LibraryTabbedPaneModel;
import presentation.model.ModelController;
import domain.Book;
import domain.Customer;

final class ListItemMouseListener extends MouseAdapter {

	private ModelController controller;

	public ListItemMouseListener(SearchResultList searchResultList,
			ModelController controller) {
		this.controller = controller;
	}

	public void mouseClicked(MouseEvent e) {
		int index = controller.resultlist_model.getIndex();
		if (index < 0)
			return;

		Object o = controller.resultlist_model.getElementAt(index);
		if (o instanceof Book) {
			Book selected = (Book) o;
			controller.booktab_model.setActiveBook(selected);
			handleBookClick(e, index, selected);
			return;
		}

		if (o instanceof Customer) {
			Customer selected = (Customer) o;
			// TODO: Coole Idee: Direkt-setActive-button für Benutzer
			// auswählen
			controller.activeuser_model.setNewActiveUser(selected);
			controller.usertab_model.setActiveCustomer(selected);
			controller.tabbed_model
					.setActiveTab(LibraryTabbedPaneModel.USER_TAB);
		}
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
			controller.library.returnBook(selected);
			controller.resultlist_model.update();
			return;
		}
		if (controller.resultlist_model.isSecondIconHit(e)) {
			if (controller.library.isBookLent(selected)) {
				controller.library.reserveBook(selected);
			} else {
				lendBook(selected);
			}
			controller.resultlist_model.update();
			return;
		}

		controller.booktab_model.setActiveBook(selected);
		showDetailsOf(selected);
	}

	public void lendBook(Book selected) {
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
		controller.tabbed_model.setActiveTab(LibraryTabbedPaneModel.BOOK_TAB);
	}

}