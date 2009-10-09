package test;

import java.util.GregorianCalendar;

import junit.framework.TestCase;
import domain.*;

public class LoanTest extends TestCase {

	public void testLoanCreation() {
		Loan l = createSampleLoan();
		assertTrue(new GregorianCalendar().equals(l.getPickupDate()));
		assertEquals("Keller",l.getCustomer().getName());
		assertEquals("Design Pattern", l.getBook().getTitle().getName());
	}

	private Loan createSampleLoan() {
		Customer c = new Customer("Keller", "Peter");
		Title t = new Title("Design Pattern");
		Book b = new Book(t);
		Loan l = new Loan(c,b);
		return l;
	}
	
	public void testReturn() {
		Loan l = createSampleLoan();
		assertTrue(l.isLent());
		l.returnBook();
		assertFalse(l.isLent());
	}
	
	public void testDurationCalculation() throws IllegalLoanOperationException {
		Loan l = createSampleLoan();
		assertEquals(-1, l.getDaysOfLoanDuration());
		
		GregorianCalendar returnDate = (GregorianCalendar) l.getPickupDate().clone();
		returnDate.add(GregorianCalendar.DAY_OF_YEAR, 12);
		
		l.returnBook(returnDate);
		
		assertEquals(12, l.getDaysOfLoanDuration());
		assertEquals(l.getReturnDate(), returnDate);
		
	}
	
	public void testDateConsistency() throws IllegalLoanOperationException {
		Loan l = createSampleLoan();
		l.setPickupDate(new GregorianCalendar(2009,01,01));
		assertTrue(l.isLent());
		try {
			l.returnBook(new GregorianCalendar(2008,12,31));
			fail("Book cannot retuned before the pickup date");
		} catch (IllegalLoanOperationException e) {

		}
		assertTrue(l.isLent());
		l.returnBook(new GregorianCalendar(2009,12,31));
		assertFalse(l.isLent());
		try {
			l.setPickupDate(new GregorianCalendar(2008,10,31));
			fail("pickup date cannot be set after the book was returned");
		} catch (IllegalLoanOperationException e) {
		}
	}
}
