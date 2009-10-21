package application;

import java.io.File;
import java.io.IOException;
import java.util.GregorianCalendar;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import presentation.view.MainWindow;

import domain.Book;
import domain.Customer;
import domain.IllegalLoanOperationException;
import domain.Library;
import domain.Loan;
import domain.Title;

public class LibraryApp {
	public static void main(String[] args) throws Exception {
		Library library = new Library();
		initLibrary(library);
		
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				MainWindow frame = new MainWindow();
			    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			    frame.pack();
			    frame.setVisible(true);
			}
			
		});
	}

	private static void initLibrary(Library library)
			throws ParserConfigurationException, SAXException, IOException,
			IllegalLoanOperationException {
		
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

		loadCustomersFromXml(library, builder,new File("data/customers.xml"));
		
		loadTitlesFromXml(library, builder,new File("data/titles.xml"));
		
		// create pseudo random books and loans
		createBooksAndLoans(library);
	
		System.out.println("Initialaition of the library was successful!\n");
		System.out.println("Titles in library: " + library.getTitles().size());
		System.out.println("Customers: " + library.getCustomers().size() + "\n");
		System.out.println("Books in library: " + library.getBooks().size());
		System.out.println("Books currently on loan: " + library.getLentBooks().size());
		int lentBooksPercentage = (int)(((double)library.getLentBooks().size()) / library.getBooks().size() * 100);
		System.out.println("Percent books on loan: " + lentBooksPercentage + "%");
	}

	private static void createBooksAndLoans(Library library)
			throws IllegalLoanOperationException {
		for(int i = 0; i < library.getTitles().size(); i++) {
			switch(i%3) {
			case 0:
				Book b1 = library.createAndAddBook(library.getTitles().get(i));
				b1.setCondition(Book.Condition.GOOD);
				createLoansForBook(library,b1,i,5);
				Book b2 = library.createAndAddBook(library.getTitles().get(i));
				b2.setCondition(Book.Condition.DAMAGED);
				createLoansForBook(library,b2,i,2);
				Book b3 = library.createAndAddBook(library.getTitles().get(i));
				b3.setCondition(Book.Condition.WASTE);
				break;
			case 1:
				Book b4 = library.createAndAddBook(library.getTitles().get(i));
				createLoansForBook(library,b4,i,4);
				library.createAndAddBook(library.getTitles().get(i));
				break;
			case 2:
				Book b5 = library.createAndAddBook(library.getTitles().get(i));
				createLoansForBook(library,b5,i,2);
				break;
			}
		}
	}

	private static void loadTitlesFromXml(Library library,
			DocumentBuilder builder, File file) throws SAXException, IOException {
		Document doc2 = builder.parse(file);
		NodeList titles = doc2.getElementsByTagName("title");
		for(int i = 0; i < titles.getLength(); i++) {
			Node title = titles.item(i);
			Title t = library.createAndAddTitle(getTextContentOf(title, "name"));
			t.setAuthor(getTextContentOf(title, "author"));
			t.setPublisher(getTextContentOf(title, "publisher"));
		}
	}

	private static void loadCustomersFromXml(Library library,
			DocumentBuilder builder, File file) throws SAXException, IOException {
		Document doc = builder.parse(file);
		NodeList customers = doc.getElementsByTagName("customer");
		for(int i = 0; i < customers.getLength(); i++) {
			Node customer = customers.item(i);
			
			Customer c = library.createAndAddCustomer(getTextContentOf(customer,"name"),getTextContentOf(customer,"surname"));
			c.setAdress(getTextContentOf(customer,"street"), Integer.parseInt(getTextContentOf(customer,"zip")), getTextContentOf(customer,"city"));
		}
	}

	private static void createLoansForBook(Library library, Book book, int position,
			int count) throws IllegalLoanOperationException {
		// Create Loans in the past
		for(int i = count; i > 1; i--) {
			Loan l = library.createAndAddLoan(getCustomer(library,position + i), book);
			GregorianCalendar pickup = l.getPickupDate();
			pickup.add(GregorianCalendar.MONTH, -i);
			pickup.add(GregorianCalendar.DAY_OF_MONTH, position%10);
			l.setPickupDate(pickup);
			GregorianCalendar ret = (GregorianCalendar) pickup.clone();
			ret.add(GregorianCalendar.DAY_OF_YEAR, position%10+i*2);
			l.returnBook(ret);
		}
		// Create actual open loans
		if(position%2 == 0) {
			Loan l = library.createAndAddLoan(getCustomer(library,position), book);
			GregorianCalendar pickup = l.getPickupDate();
			pickup.add(GregorianCalendar.DAY_OF_MONTH, -position%10);
			l.setPickupDate(pickup);
		}
	}

	private static Customer getCustomer(Library library, int position) {
		return library.getCustomers().get(position % library.getCustomers().size());
	}

	private static String getTextContentOf(Node element, String name) {
		NodeList attributes = element.getChildNodes();		
		for(int r = 0; r < attributes.getLength(); r++) {
			if(attributes.item(r).getNodeName().equals(name)) {
				return attributes.item(r).getTextContent();
			}
		}
		return "";
	}
}
