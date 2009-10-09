package test;

import java.util.List;

import junit.framework.TestCase;
import domain.Customer;
import domain.Library;
import domain.Loan;
import domain.Title;

public class LibraryTest extends TestCase {
	
	Library library;
	
	@Override
	protected void setUp() throws Exception {
		
		library = new Library();
		
		// Titles
		Title t1 = library.createAndAddTitle("Design Pattern");
		Title t2 = library.createAndAddTitle("Refactoring");
		Title t3 = library.createAndAddTitle("Clean Code");
		
		// Books
		library.createAndAddBook(t1);
		library.createAndAddBook(t1);
		library.createAndAddBook(t1);
		
		library.createAndAddBook(t2);
		library.createAndAddBook(t2);		
		
		library.createAndAddBook(t3);
		
		
		// Customers
		library.createAndAddCustomer("Keller", "Peter");
		library.createAndAddCustomer("Mueller", "Fritz");
		library.createAndAddCustomer("Meier", "Martin");
		
	}

	public void testGetBooksPerTitle() {
		
		Title t = library.findByTitleName("Design Pattern");
		assertEquals(3, library.getBooksPerTitle(t).size());
		
		Title t2 = library.findByTitleName("Clean Code");
		assertEquals(1, library.getBooksPerTitle(t2).size());
		
		Title t3 = library.findByTitleName("noTitle");
		assertEquals(0, library.getBooksPerTitle(t3).size());
	}
	
	public void testLoans() {
		Title t = library.findByTitleName("Design Pattern");
		
		assertEquals(0, library.getLoansPerTitle(t).size());
		
		Customer c = library.getCustomers().get(0);
		
		Loan lo = library.createAndAddLoan(c, library.getBooksPerTitle(t).get(0));
		
		assertEquals(1, library.getLoansPerTitle(t).size());
		assertEquals(lo, library.getLoansPerTitle(t).get(0));
		
		Loan lo2 = library.createAndAddLoan(c, library.getBooksPerTitle(t).get(0));
		assertNull(lo2);
		
		List<Loan> lo3 = library.getCustomerLoans(c);
		assertEquals(1, lo3.size());
		
	}
	
	public void testAvailability () {
		assertEquals(library.getBooks().size(),library.getAvailableBooks().size());
		
		Title t = library.findByTitleName("Refactoring");
		Customer c = library.getCustomers().get(1);
		library.createAndAddLoan(c, library.getBooksPerTitle(t).get(0));
		
		assertEquals(1,library.getLentBooks().size());

	}
	

}
