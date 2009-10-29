package presentation.model;

import java.util.ArrayList;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import domain.Book;
import domain.Customer;
import domain.Library;
import domain.Loan;
import domain.Searchable;
import domain.Title;

public class SearchResultListModel implements ListModel {

	ArrayList<Searchable> list;
	
	public SearchResultListModel(Library library) {
		list = new ArrayList<Searchable>();
		// TODO: Display Results instead of fakes [Martin]
		for (int i = 0; i < 10; i++)
			list.add(library.getAvailableBooks().get(i));
		Customer test = new Customer("Hans", "Tester");
		test.setAdress("Testdrive 3", 6667, "Oklahoma");
		library.getLoans().add(new Loan(test, new Book(new Title("jfkldsfls"))));
		library.getLoans().add(new Loan(test, new Book(new Title("jfkldsfls1"))));
		library.getLoans().add(new Loan(test, new Book(new Title("jfkldsfls2"))));
		library.getLoans().add(new Loan(test, new Book(new Title("jfkldsfls3"))));
		list.add(0, test);
	}
	
	public void addListDataListener(ListDataListener l) {
	}

	public Searchable getElementAt(int index) {
		return list.get(index);
	}

	public int getSize() {
		return list.size();
	}

	public void removeListDataListener(ListDataListener l) {
	}

}