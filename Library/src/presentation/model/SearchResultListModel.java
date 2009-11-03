package presentation.model;

import java.util.ArrayList;
import java.util.Observable;

import javax.management.Attribute;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import domain.Book;
import domain.Customer;
import domain.Library;
import domain.Loan;
import domain.Searchable;
import domain.Title;

public class SearchResultListModel extends Observable implements ListModel {

	private ArrayList<ArrayList<Searchable>> history = new ArrayList<ArrayList<Searchable>>();
	private ArrayList<Searchable> displayed_results;
//	private ModelController controller;
	private Library library;
	private String searchstring = new String();

	public SearchResultListModel(ModelController controller) {
//		this.controller = controller;
		library = controller.library;
		displayed_results = new ArrayList<Searchable>();
		// TODO: Display Results instead of fakes [Martin]
		for (int i = 0; i < 10; i++)
			displayed_results.add(library.getAvailableBooks().get(i));
		/*
		 * for (Searchable user : library.getCustomers()) {
		 * displayed_results.add(user); }
		 */
		Customer test = new Customer("Hans", "Tester");
		test.setAdress("Testdrive 3", 6667, "Oklahoma");
		library.getLoans()
				.add(new Loan(test, new Book(new Title("jfkldsfls"))));
		library.getLoans().add(
				new Loan(test, new Book(new Title("jfkldsfls1"))));
		library.getLoans().add(
				new Loan(test, new Book(new Title("jfkldsfls2"))));
		library.getLoans().add(
				new Loan(test, new Book(new Title("jfkldsfls3"))));
		displayed_results.add(0, test);
		Title title = new Title("Mein ausgeliehener Titel");
		title.setAuthor("Autorius Buchius");
		title.setPublisher("Limmatdruck");
		Book tbook = new Book(title);
		library.createAndAddLoan(test, tbook);
		displayed_results.add(1, tbook);
	}

	public void addListDataListener(ListDataListener l) {
	}

	public Searchable getElementAt(int index) {
		return displayed_results.get(index);
	}

	public int getSize() {
		return displayed_results.size();
	}

	public void removeListDataListener(ListDataListener l) {
	}

	public void addchar(char c) {
		searchstring += c;
		newsearch();
	}

	// TODO: remove Debugtext in here
	private void newsearch() {
		history.add(displayed_results);

		ArrayList<Searchable> tmplist = new ArrayList<Searchable>();

		for (Searchable item : displayed_results) {
			if (item.searchTitle().toLowerCase().contains(searchstring))
				tmplist.add(item);
			else {
				for (Attribute att : item.searchDetail().asList()) {
					System.out.println(att.getValue() + "----" + searchstring);
					if (att.getValue().toString().toLowerCase().contains(
							searchstring)) {
						tmplist.add(item);
						break;
					}
				}
			}
		}

		displayed_results = tmplist;
		setChanged();
		notifyObservers();
		System.out.println("newsearch finished with: " + searchstring
				+ " and found " + displayed_results.size() + " entries");
	}

	public void delchar() {
		if (searchstring.length() > 0) {
			searchstring = searchstring.substring(0, searchstring.length() - 1);
			displayed_results = history.remove(history.size() - 1);
			System.out
					.println("delchar called, new string is: " + searchstring);
		}
		setChanged();
		notifyObservers();
		System.out.println(displayed_results.size());
	}
}