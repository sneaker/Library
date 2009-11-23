package presentation.model;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import domain.Book;
import domain.Customer;
import domain.IllegalLoanOperationException;
import domain.SearchEngine;
import domain.Searchable;

public class SearchResultListModel implements ListModel, Observer {

	private ModelController controller;
	private SearchEngine search_engine;
	
	private ArrayList<Searchable> displayed_results = new ArrayList<Searchable>();
	private ArrayList<ListDataListener> listeners = new ArrayList<ListDataListener>();
	
	private int index = 0;
	private int height;
	private int width;

	public SearchResultListModel(ModelController controller) {
		this.controller = controller;
		search_engine = new SearchEngine(controller.library);
		search_engine.addObserver(this);
		search_engine.startNewSearch();
		setTestData();
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
	
	public void forwardKeyEvent(char c, String wholestring) {
		search_engine.decideWhatHappend_ADD(c, wholestring);
		search_engine.startNewSearch();
	}
	
	public void forwardDelEvent(char c, String wholestring) {
		search_engine.decideWhatHappend_DEL(c, wholestring);
	}

	public void resetSearch() {
		search_engine.reset();
	}
	
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	public Searchable getElementAt(int index) {
		return displayed_results.get(index);
	}


	public void removeListDataListener(ListDataListener l) {
		listeners.clear();
	}

	public int getSize() {
		return displayed_results.size();
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
			controller.setActiveBook((Book) item);
			controller.setBookTabActive();
		} else if (item instanceof Customer) {
			controller.setActiveCustomer((Customer) item);
			controller.tabbed_model.setUserTabActive();
		}
	}

	public void fireDataChanged(MouseListener source, int index) {
		for (ListDataListener l : listeners) {
			l.contentsChanged(new ListDataEvent(source, ListDataEvent.CONTENTS_CHANGED, index, index));
		}
	}

	public void update(Observable o, Object arg) {
		displayed_results = (ArrayList<Searchable>) arg;
		if (!listeners.isEmpty())
			update();
	}
	
	public void update() {
		for (ListDataListener l : listeners) {			
			l.contentsChanged(null);
		}
	}

	public void showavailableBooks() {
		search_engine.showAvailableBooks();
	}

	public void showDefectBooks() {
		search_engine.showDefektBooks();
	}

	public void showLentBooks() {
		search_engine.showLentBooks();
	}

	public void showUser() {
		search_engine.showUser();
	}

}
