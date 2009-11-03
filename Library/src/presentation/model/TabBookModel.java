package presentation.model;

import java.util.Observable;

import domain.Book;

public class TabBookModel extends Observable {

	private Book activeBook;
//	private ModelController controller;

	public TabBookModel(ModelController controller) {
//		this.controller = controller;
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
