package presentation.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.management.Attribute;

import domain.Book;
import domain.Customer;
import domain.Library;
import domain.Searchable;

public class SearchEngine extends Observable implements Observer{

	private Library library;
	
	private ArrayList<ArrayList<Searchable>> history; 
	private ArrayList<Searchable> tmplist; 
	private ArrayList<Searchable> results;
	
	private String querry = new String();
	
	public SearchEngine(Library library) {
		this.library = library; 
		library.addObserver(this);
		library.setFiringEnabled(true);
		
		history = new ArrayList<ArrayList<Searchable>>();
		tmplist = new ArrayList<Searchable>();
		
		buildNewIndex();
	}
	
	public void startNewSearch() {
		if (!querry.isEmpty())
			search();
			
		setChanged();
		notifyObservers(results.clone());
	}
	
	private void buildNewIndex() {
		history.clear();
		results = new ArrayList<Searchable>();
		
		for (Searchable user : library.getCustomers()) {
			results.add(user);
		}
		
		for (int i = 0; i < 10; i++) {
			results.add(library.getAvailableBooks().get(i));
		}
		
		history.add(results);
	}
	

	private void search() {
		history.add(results);
		
		for (Searchable item : results) {
			Pattern p = Pattern.compile(querry);
			Matcher m = p.matcher(item.searchTitle());
			
			if (m.find()) {
				tmplist.add(item);
			}
			else {
				for (Attribute att : item.searchDetail().asList()) {
					p = Pattern.compile(querry);
					m = p.matcher(att.toString());
					if (m.find()) {
						tmplist.add(item);
						break;
					}
				}
			}
		}
		results = tmplist;
		tmplist = new ArrayList<Searchable>();
	}
	
	
	public void decideWhatHappend_ADD(char c, String wholestring) {
		//System.out.println("**********");
		//System.out.println("querry + c: " + (querry+c));
		//System.out.println("wholestring: " + wholestring);
		if ((querry + c).equals(wholestring)) {
			querry += c;
		}
		else {
			reBuildWholeSearchTree(wholestring);
		}
	}

	private void reBuildWholeSearchTree(String wholestring) {
		buildNewIndex();
		querry = "";
		for (char single_char : wholestring.toCharArray()) {
			//System.out.println("the single char is: " +single_char);
			//System.out.println("and the querry is: " +querry);
			querry += single_char;
			search();
		}
		setChanged();
		notifyObservers(results);
	}
	
	public void decideWhatHappend_DEL(char c, String wholestring) {
		if (wholestring.isEmpty()) {
			querry = "";
			history.clear();
			buildNewIndex();
			setChanged();
			notifyObservers(results);
			return;
		}
		if (wholestring != "" && wholestring.equals(querry.substring(0, wholestring.length()))) {
			querry = wholestring;
			//System.out.println("-------------");
			//System.out.println("querry: " + querry);
			//System.out.println("wholestring: " + wholestring);
			deleteLastChar();
		} else {
			//System.out.println("hit else del part");
			reBuildWholeSearchTree(wholestring);
		}
	}
	
	public void deleteLastChar() {
		if (history.size() == 1)
			return;
		
		history.remove(history.size()-1);
		results = history.get(history.size()-1);
		
		setChanged();
		notifyObservers(results);
	}
	
	private void sort() {
		java.util.Collections.sort(results,
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
		
		ArrayList<Searchable> tmplist = new ArrayList<Searchable>();

		for (Searchable item : results) {
			if (item instanceof Book) {
				Book book = (Book) item;

				if (book.getCondition() == Book.Condition.GOOD
						|| book.getCondition() == Book.Condition.NEW)
					tmplist.add(item);
			}
		}

		results = tmplist;
		history.add(results);

		setChanged();
		notifyObservers(results.clone());
	}

	public void showDefektBook() {
	
		ArrayList<Searchable> tmplist = new ArrayList<Searchable>();

		for (Searchable item : results) {
			if (item instanceof Book) {
				Book book = (Book) item;
				if (book.getCondition() == Book.Condition.DAMAGED)
					tmplist.add(item);
			}
		}
		results = tmplist;
		history.add(results);
	}

	public void showLentBooks() {
		ArrayList<Searchable> tmplist = new ArrayList<Searchable>();

		for (Book book : library.getLentBooks()) {
			tmplist.add(book);
		}
		results = tmplist;
		history.add(results);
	}

	public void showUser() {
		ArrayList<Searchable> tmplist = new ArrayList<Searchable>();

		for (Customer customer : library.getCustomers()) {
			tmplist.add(customer);
		}
		results = tmplist;
		history.add(results);

		sort();
	}

	@Override
	public void update(Observable o, Object arg) {
		reBuildWholeSearchTree(querry);
	}

}
