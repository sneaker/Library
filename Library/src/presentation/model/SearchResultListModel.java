package presentation.model;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.management.Attribute;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import domain.Book;
import domain.Customer;
import domain.IllegalLoanOperationException;
import domain.Library;
import domain.Searchable;

public class SearchResultListModel implements ListModel {

	private ArrayList<ArrayList<Searchable>> history = new ArrayList<ArrayList<Searchable>>();
	private ArrayList<ListDataListener> listeners = new ArrayList<ListDataListener>();
	private ArrayList<Searchable> displayed_results;
	private Library library;
	private String searchstring = new String();

	public SearchResultListModel(ModelController controller) {
		library = controller.library;
		displayed_results = new ArrayList<Searchable>();
		for (Searchable user : library.getCustomers()) {
			displayed_results.add(user);
		}

		// TODO [Release] Remove test loan, but leave for Demonstration [Martin]
		// Betrifft Benutzer Jenni Heinrich
		if (displayed_results.get(0) instanceof Customer) {
			try {
				controller.library.getCustomerActiveLoans(
						((Customer) displayed_results.get(0))).get(0)
						.setPickupDate(
								new GregorianCalendar(2009,
										GregorianCalendar.OCTOBER, 30));
				controller.library.getCustomerActiveLoans(
						((Customer) displayed_results.get(0))).get(1)
						.setPickupDate(
								new GregorianCalendar(2009,
										GregorianCalendar.OCTOBER, 10));
			} catch (IllegalLoanOperationException e) {
			}
		}

		for (int i = 0; i < 10; i++)
			displayed_results.add(library.getAvailableBooks().get(i));

		history.add(displayed_results);
	}

	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	public Searchable getElementAt(int index) {
		return displayed_results.get(index);
	}

	public int getSize() {
		return displayed_results.size();
	}

	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}

	public void addchar(char c) {
		searchstring += c;
		newsearch();
	}

	private void newsearch() {

		ArrayList<Searchable> tmplist = new ArrayList<Searchable>();

		for (Searchable item : displayed_results) {
			if (item.searchTitle().toLowerCase().contains(searchstring))
				tmplist.add(item);
			else {
				for (Attribute att : item.searchDetail().asList()) {
					if (att.getValue().toString().toLowerCase().contains(
							searchstring)) {
						tmplist.add(item);
						break;
					}
				}
			}
		}
		displayed_results = tmplist;
		history.add(displayed_results);

		update();
	}

	public void delchar(String newstring) {
		if ((!newstring.isEmpty())
				&& newstring.equals(searchstring.substring(0, searchstring
						.length() - 1))) {
			searchstring = searchstring.substring(0, searchstring.length() - 1);
			displayed_results = history.remove(history.size() - 1);
		} else {
			resetSearch();
		}

		update();
	}

	public void resetSearch() {
		searchstring = "";
		displayed_results = history.get(0);
		history.clear();
		history.add(displayed_results);

		update();
	}

	private void update() {
		for (ListDataListener listener : listeners) {
			listener.contentsChanged(null);
		}
	}

	public void showavailableBooks() {
		resetSearch();
		ArrayList<Searchable> tmplist = new ArrayList<Searchable>();

		for (Searchable item : displayed_results) {
			if (item instanceof Book) {
				Book book = (Book) item;

				if (book.getCondition() == Book.Condition.GOOD
						|| book.getCondition() == Book.Condition.NEW)
					tmplist.add(item);
			}
		}

		displayed_results = tmplist;
		history.add(displayed_results);

		update();
	}

	public void showDefektBook() {
		resetSearch();
		ArrayList<Searchable> tmplist = new ArrayList<Searchable>();

		for (Searchable item : displayed_results) {
			if (item instanceof Book) {
				Book book = (Book) item;
				if (book.getCondition() == Book.Condition.DAMAGED)
					tmplist.add(item);
			}
		}
		displayed_results = tmplist;
		history.add(displayed_results);
		
		update();
	}
	
	public void showLentBooks() {
		ArrayList<Searchable> tmplist = new ArrayList<Searchable>();
		
		for(Book book : library.getLentBooks()) {
			tmplist.add(book);
		}
		displayed_results = tmplist;
		history.add(displayed_results);
		
		update();
	}

	public void showUser() {
		ArrayList<Searchable> tmplist = new ArrayList<Searchable>();
		
		for(Customer customer : library.getCustomers()) {
			tmplist.add(customer);
		}
		displayed_results = tmplist;
		history.add(displayed_results);
		update();
	}
}
