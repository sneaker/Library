package presentation.model;

import java.util.ArrayList;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import domain.Book;
import domain.Library;

public class SearchResultListModel implements ListModel {

	ArrayList<Book> list;
	
	public SearchResultListModel(Library library) {
		list = new ArrayList<Book>();
		// TODO: Display Results instead of fakes [Martin]
		for (int i = 0; i < library.getAvailableBooks().size(); i++)
			list.add(library.getAvailableBooks().get(i));
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