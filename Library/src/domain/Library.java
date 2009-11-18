package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Library {
	private List<Book> books;
	private List<Customer> customers;
	private List<Loan> loans;
	private List<Title> titles;
	private List<Loan> result;

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

	public void returnBook(Book selected) {
		for (Loan l : getLoansPerTitle(selected.getTitle())) {
			if (l.getBook().getInventoryNumber() != (selected
					.getInventoryNumber()))
				continue;
			if (!l.returnBook())
				return;
			return;
		}
	}

	public void reserveBook(Book selected) {
		// TODO: Implement reservation of a book.
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

	public List<Book> getAvailableBooks() {
		return getBooks(false);
	}

	public List<Book> getLentBooks() {
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

	/**
	 * Searches for loans of a specific book and returns a list of Loans which
	 * stores the newest Loans at the end of the list.
	 * 
	 * @param book
	 *            the book for which loans are looked for
	 * @return empty list if nothing found
	 * @return list of all loans found for the given book, sorted by the most
	 *         recent loans.
	 */
	public List<Loan> getLoansPerBook(Book book) {
		ArrayList<Loan> result = new ArrayList<Loan>();
		for (Loan l : getLoans()) {
			if (l.getBook().equals(book))
				result.add(l);
		}
		Collections.sort(new ArrayList<Loan>(), new Comparator<Loan>() {
			public int compare(Loan a, Loan b) {
				if (a.getPickupDate() == null || b.getPickupDate() == null)
					return 0;
				if (a.getReturnDate() == null && b.getReturnDate() == null) {
					return a.getPickupDate().compareTo(b.getPickupDate());
				}
				if (a.getReturnDate() == null)
					return 1;
				if (b.getReturnDate() == null)
					return -1;
				return (a.getReturnDate().compareTo(b.getReturnDate()));
			}
		});
		return result;
	}

	/**
	 * Picks out the last Loan of getRecentLoanOf(Book) which is the most recent
	 * one.
	 * 
	 * @param book
	 *            search the most recent loan of this specific book
	 * @return the most recent loan if available, else null
	 */
	public Loan getRecentLoanOf(Book book) {
		List<Loan> l = getLoansPerBook(book);
		if (l.size() < 1)
			return null;
		return l.get(l.size() - 1);
	}

	public int getMaxLendDays() {
		return Loan.MAX_LEND_DAYS;
	}

	public ArrayList<Loan> getCustomerMahnungen(Customer c) {
		ArrayList<Loan> result = new ArrayList<Loan>();
		for (Loan l : getCustomerActiveLoans(c)) {
			if (l.isOverdue())
				result.add(l);
		}
		return result;
	}

	public List<Loan> getCustomerActiveLoans(Customer c) {
		result = new ArrayList<Loan>();
		for (Loan l : getCustomerLoans(c))
			if (l.isLent())
				result.add(l);
		return result;
	}

	public boolean isCustomerLocked(Customer customer) {
		return (getCustomerMahnungen(customer).size() > 0);
	}
}
