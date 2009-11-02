package presentation.model;

import java.util.Observable;

import domain.Book;

public class TabBookPanelModel extends Observable {

	private Book activeBook;
	private ModelController controller;
	
	public TabBookPanelModel(ModelController controller) {
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

}
