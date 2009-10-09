package domain;

import java.util.ArrayList;
import java.util.List;

public class Library {

	private List<Book> books;
	private List<Customer> customers;
	private List<Loan> loans;
	private List<Title> titles;

	public Library() {
		books = new ArrayList<Book>();
		customers = new ArrayList<Customer>();
		loans = new ArrayList<Loan>();
		titles = new ArrayList<Title>();
	}

	public Loan createAndAddLoan(Customer customer, Book book) {
		if (!isBookLent(book)) {
			Loan l = new Loan(customer, book);
			loans.add(l);
			return l;
		} else {
			return null;
		}
	}

	public Customer createAndAddCustomer(String name, String surname) {
		Customer c = new Customer(name, surname);
		customers.add(c);
		return c;
	}

	public Title createAndAddTitle(String name) {
		Title t = new Title(name);
		titles.add(t);
		return t;
	}

	public Book createAndAddBook(Title title) {
		Book b = new Book(title);
		books.add(b);
		return b;
	}

	public Title findByTitleName(String name) {
		for (Title t : titles) {
			if (t.getName().equals(name)) {
				return t;
			}
		}
		return null;
	}

	public boolean isBookLent(Book book) {
		for (Loan l : loans) {
			if (l.getBook().equals(book) && l.isLent()) {
				return true;
			}
		}
		return false;
	}

	public List<Book> getBooksPerTitle(Title title) {
		List<Book> res = new ArrayList<Book>();
		for (Book b : books) {
			if (b.getTitle().equals(title)) {
				res.add(b);
			}
		}

		return res;
	}

	public List<Loan> getLoansPerTitle(Title title) {
		List<Loan> lentBooks = new ArrayList<Loan>();
		for (Loan l : loans) {
			if (l.getBook().getTitle().equals(title) && l.isLent()) {
				lentBooks.add(l);
			}
		}
		return lentBooks;
	}

	public List<Loan> getCustomerLoans(Customer customer) {
		List<Loan> lentBooks = new ArrayList<Loan>();
		for (Loan l : loans) {
			if (l.getCustomer().equals(customer)) {
				lentBooks.add(l);
			}
		}
		return lentBooks;
	}
	
	public List<Book> getAvailableBooks(){
		return getBooks(false);
	}
	
	public List<Book> getLentBooks(){
		return getBooks(true);
	}

	private List<Book> getBooks(boolean isLent) {
		List<Book> retBooks = new ArrayList<Book>();
		for (Book b : books) {
			if (isLent == isBookLent(b)) {
				retBooks.add(b);
			}
		}
		return retBooks;
	}

	public List<Book> getBooks() {
		return books;
	}

	public List<Loan> getLoans() {
		return loans;
	}

	public List<Title> getTitles() {
		return titles;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

}
