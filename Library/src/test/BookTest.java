package test;

import junit.framework.TestCase;
import domain.Book;
import domain.Title;

public class BookTest extends TestCase {

	public void testBook() {
		Title t = new Title("Design Pattern");
		Book b1 = new Book(t);
		assertEquals(Book.nextInvertoryNumber -1, b1.getInventoryNumber());
		Book b2 = new Book(t);
		assertEquals(Book.nextInvertoryNumber -1, b2.getInventoryNumber());
		assertEquals(Book.Condition.NEW, b2.getCondition());
		
		b1.setCondition(Book.Condition.DAMAGED);
		
		assertEquals(Book.Condition.DAMAGED, b1.getCondition());
	}


}
