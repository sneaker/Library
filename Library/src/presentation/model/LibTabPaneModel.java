package presentation.model;

import java.util.Observable;

import domain.Book;
import domain.Customer;

public class LibTabPaneModel extends Observable {

	private int activeTab = 0;
	private Book activeBook;
	private Customer activeCustomer;

	public int getActiveTab() {
		return activeTab;
	}

	public void setActiveTab(int newIndex) {
		this.activeTab = newIndex;
		setChanged();
		notifyObservers();
	}

	public void setActiveBook(Book newBook) {
		this.activeBook = newBook;
		setChanged();
		notifyObservers(newBook);
	}

	public Book getActiveBook() {
		return activeBook;
	}

	public void setActiveCustomer(Customer newCustomer) {
		this.activeCustomer = newCustomer;
	}
}
