package presentation.view;

import java.util.ArrayList;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import domain.Book;
import domain.Title;

public class SearchResultListModel implements ListModel {

	ArrayList<Book> list;
	
	public SearchResultListModel() {
		list = new ArrayList<Book>();
		list.add(new Book(new Title("Java 2 - Introduction")));
		list.add(new Book(new Title("Design Patterns")));
		list.add(new Book(new Title("Software Architecture")));
		list.add(new Book(new Title("Item 5")));
		list.add(new Book(new Title("Item 6")));
	}
	
	public void addListDataListener(ListDataListener l) {
	}

	public Book getElementAt(int index) {
		return list.get(index);
	}

	public int getSize() {
		return list.size();
	}

	public void removeListDataListener(ListDataListener l) {
	}

}