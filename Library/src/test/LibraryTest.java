package test;

import java.util.GregorianCalendar;
import java.util.List;

import junit.framework.TestCase;
import domain.Book;
import domain.Customer;
import domain.IllegalLoanOperationException;
import domain.Library;
import domain.Loan;
import domain.Title;

public class LibraryTest extends TestCase {
	
	private static final GregorianCalendar MIDDLE_OLD = new GregorianCalendar(2014, GregorianCalendar.JULY, 16);
	private static final GregorianCalendar RECENT_DATE = new GregorianCalendar(2021, GregorianCalendar.APRIL, 28);
	private static final GregorianCalendar OLD_DATE = new GregorianCalendar(2001, GregorianCalendar.JANUARY, 1);
	Library library;
	private Customer c1;
	private Book b1;
	private Title t1;
	private Title t2;
	private Title t3;
	
	@Override
	protected void setUp() throws Exception {
		
		library = new Library();
		
		t1 = library.createAndAddTitle("Design Pattern");
		t2 = library.createAndAddTitle("Refactoring");
		t3 = library.createAndAddTitle("Clean Code");
		
		b1 = library.createAndAddBook(t1);
		library.createAndAddBook(t1);
		library.createAndAddBook(t1);
		
		library.createAndAddBook(t2);
		library.createAndAddBook(t2);		
		
		library.createAndAddBook(t3);
		
		
		c1 = library.createAndAddCustomer("Keller", "Peter");
		library.createAndAddCustomer("Mueller", "Fritz");
		library.createAndAddCustomer("Meier", "Martin");
		
	}

	public void testOneRecentLoan() throws IllegalLoanOperationException {
		Loan recent = createLoan(RECENT_DATE);
		assertEquals (recent, library.getRecentLoanOf(b1));
	}
	
	public void testMultipleRecentLoan() throws IllegalLoanOperationException {
		createLoan(OLD_DATE);
		createLoan(MIDDLE_OLD);
		Loan newest = createLoan(RECENT_DATE);
		assertEquals (newest, library.getRecentLoanOf(b1));
	}
	
	public void testMultipleRecentLoanSize() throws IllegalLoanOperationException {
		createLoan(OLD_DATE);
		createLoan(MIDDLE_OLD);
		createLoan(RECENT_DATE);
		assertEquals (3, library.getLoansPerBook(b1).size());
	}

	private Loan createLoan(GregorianCalendar cal) throws IllegalLoanOperationException {
		Loan loan = library.createAndAddLoan(c1, b1);
		loan.setPickupDate(cal);
		loan.returnBook(cal);
		return loan;
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
