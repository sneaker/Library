package test;

import junit.framework.TestCase;
import domain.Title;

public class TitleTest extends TestCase {

	public void testTitleCreation() {
		Title t = new Title("The Definitive ANTLR Reference");
		t.setName("The Definitive ANTLR Reference");
		t.setAuthor("Terence Parr");
		t.setPublisher("The Pragmatic Programmers");
		
		assertEquals("The Definitive ANTLR Reference", t.getName());
		t.setName("NewName");
		assertEquals("NewName", t.getName());
		assertEquals("Terence Parr", t.getAuthor());
		assertEquals("The Pragmatic Programmers", t.getPublisher());

	}
}
