package presentation.model;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.GregorianCalendar;

import javax.management.Attribute;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import domain.Book;
import domain.Customer;
import domain.IllegalLoanOperationException;
import domain.Library;
import domain.Searchable;

public class SearchResultListModel implements ListModel {

	private ArrayList<ArrayList<Searchable>> history = new ArrayList<ArrayList<Searchable>>();
	private ArrayList<Searchable> tmplist = new ArrayList<Searchable>();
	private ArrayList<Searchable> displayed_results;
	private ListDataListener listener;
	//private ArrayList<ListDataListener> listeners = new ArrayList<ListDataListener>();
	private Library library;
	private String searchstring = new String();
	private ModelController controller;

	private int index = 0;
	private int height;
	private int width;

	public SearchResultListModel(ModelController controller) {
		this.controller = controller;
		library = controller.library;
		initLibrary();
	}

	private void initLibrary() {
		reBulidIndex();
		setTestData();
		sort();
	}


	private void setTestData() {
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
	}

	public void addListDataListener(ListDataListener l) {
		listener = l;
	}

	public Searchable getElementAt(int index) {
		return displayed_results.get(index);
	}

	public int getSize() {
		return displayed_results.size();
	}

	public void removeListDataListener(ListDataListener l) {
		listener = null;
	}

	public void simpleKeyAdd(String newstring) {
		if (newstring.length() < 1 || newstring.substring(0, newstring.length()-1).equals(searchstring)) {
			searchstring = newstring;
			newsearch();
		}
		else {
			searchstring = newstring;
			buildagain(searchstring);
		}
	}

	private void newsearch() {
		history.add(displayed_results);
		
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
		
		sort();
		update();
		tmplist = new ArrayList<Searchable>();
	}

	public void delchar(String newstring) {
		System.out.println("del newstring: " + newstring);
		System.out.println("length: " + (searchstring.length()));
		if (newstring.isEmpty()) {
			resetSearch();
		}
		// the last char has been deleted
		else if (newstring.equals(searchstring.substring(0, searchstring.length()-1))) 
		{
			goOneBack();
		} 
		else //anything else happend 
		{
			System.out.println("went in here");
			resetSearch();
			System.out.println("newstring before new: " + newstring);
			buildagain(newstring);
		}

		sort();
		update();
	}

	private void buildagain(String newstring) {
		for (char c : newstring.toCharArray()) {
			searchstring += c;
			newsearch();
		}
	}

	private void goOneBack() {
		searchstring = searchstring.substring(0, searchstring.length() - 1);
		displayed_results = history.remove(history.size() - 1);
	}
	
	public void resetAndDisplaySearch() {
		resetSearch();
		sort();
		update();
	}

	private void reBulidIndex() {
		history.clear();
		displayed_results = new ArrayList<Searchable>();
		for (Searchable user : library.getCustomers()) {
			displayed_results.add(user);
		}
		for (int i = 0; i < 10; i++) {
			displayed_results.add(library.getAvailableBooks().get(i));
		}
		history.add(displayed_results);
	}
	
	private void resetSearch() {
		if (history.size() == 1) 
			return;
		searchstring = "";
		reBulidIndex();
	}

	public void update() {
		listener.contentsChanged(null);
	}

	private void sort() {
		java.util.Collections.sort(displayed_results,
				new Comparator<Searchable>() {
					public int compare(Searchable o1, Searchable o2) {
						if (o1 instanceof Book && o2 instanceof Customer)
							return 1;
						if (o1 instanceof Customer && o2 instanceof Book)
							return -1;
						else
							return o1.searchTitle().compareTo(o2.searchTitle());
					}
				});
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

		sort();
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

		sort();
		update();
	}

	public void showLentBooks() {
		ArrayList<Searchable> tmplist = new ArrayList<Searchable>();

		for (Book book : library.getLentBooks()) {
			tmplist.add(book);
		}
		displayed_results = tmplist;
		history.add(displayed_results);

		sort();
		update();
	}

	public void showUser() {
		ArrayList<Searchable> tmplist = new ArrayList<Searchable>();

		for (Customer customer : library.getCustomers()) {
			tmplist.add(customer);
		}
		displayed_results = tmplist;
		history.add(displayed_results);

		sort();
		update();
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public boolean isFirstIconHit(MouseEvent e) {
		if (e.getX() < 70 || e.getX() > (70 + 32))
			return false;
		if ((e.getY() < 30 + getCellHeight() * index)
				|| e.getY() > 62 + getCellHeight() * index)
			return false;
		return true;
	}

	public boolean isSecondIconHit(MouseEvent e) {
		if (e.getX() < 110 || e.getX() > (110 + 32))
			return false;
		if ((e.getY() < 30 + getCellHeight() * index)
				|| e.getY() > 62 + getCellHeight() * index)
			return false;
		return true;
	}

	public void setCellHeight(int height) {
		this.height = height;
	}

	public void setCellWidth(int width) {
		this.width = width;
	}

	public int getCellHeight() {
		return height;
	}

	public int getCellWidth() {
		return width;
	}

	public void selectSingleElement() {
		if (displayed_results.size() != 1)
			return;
		Searchable item = getElementAt(0);
		if (item instanceof Book) {
			controller.booktab_model.setActiveBook((Book) item);
			controller.tabbed_model
					.setActiveTab(LibraryTabbedPaneModel.BOOK_TAB);
		} else if (item instanceof Customer) {
			controller.activeuser_model.setActiveUser((Customer) item);
			controller.tabbed_model
					.setActiveTab(LibraryTabbedPaneModel.USER_TAB);
		}
	}

	public void fireDataChanged(MouseListener source, int index) {
			listener.contentsChanged(new ListDataEvent(source, ListDataEvent.CONTENTS_CHANGED, index, index));
	}
}
