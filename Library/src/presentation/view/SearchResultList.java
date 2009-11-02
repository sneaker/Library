package presentation.view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import presentation.model.LibraryTabbedPaneModel;
import presentation.model.ModelController;
import presentation.model.SearchResultListModel;
import domain.Book;
import domain.Customer;
import domain.Library;
import domain.Loan;

/**
 * Displays search results for books and users combined with specific actions
 * for each item.
 */
public class SearchResultList extends JList {

	private static final long serialVersionUID = 1L;
	private JList resultList;
	private final LibraryTabbedPaneModel tabModel;
	private final Library library;
	private SearchResultCellRenderer cellRenderer;
	private ModelController controller;

	public SearchResultList(ModelController controller) {
		this.controller = controller;
		this.tabModel = controller.tabbed_model;
		this.library = controller.library;
		setLayout(new BorderLayout());
		initResultList();
	}

	private void initResultList() {
		resultList = new JList();
		resultList.setModel(controller.resultlist_model);
		cellRenderer = new SearchResultCellRenderer(library);
		resultList.setCellRenderer(cellRenderer);
		resultList.setDoubleBuffered(false);
		resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		resultList.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				int index = eventToListIndex(e);
				if (index < 0)
					return;
				resultList.setSelectedIndex(resultList
						.locationToIndex(new Point(e.getX(), e.getY())));
			}
		});

		resultList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int index = eventToListIndex(e);
				if (index < 0)
					return;

				// TODO: Refactoring, das ist eine Knacknuss... [Martin]
				Object o = resultList.getModel().getElementAt(index);
				if (o instanceof Book) {
					Book selected = (Book) o;
					handleBookClick(e, index, selected);
					return;
				}
				
				if(o instanceof Customer) {
					Customer selected = (Customer) o;
					tabModel.setActiveCustomer(selected);
					tabModel.setActiveTab(LibraryTabbedPaneModel.USER_TAB);
					//TODO: not the right place for this
					tabModel.activeUser(selected);
				}
			}

			private void handleBookClick(MouseEvent e, int index, Book selected) {
				if (isFirstIconHit(e, index)) {
					// System.out.println("Erste Buch-Aktion! (Buch zurückgeben)");
					for (Loan l: library.getLoansPerTitle(selected.getTitle())) {
						if (l.getBook().getInventoryNumber() == (selected.getInventoryNumber())) {
							library.getLoans().remove(l);
							library.getAvailableBooks().add(selected);
							// TODO: library.getCustomerLoans(customer);
							System.out.println("Buch " + selected.getInventoryNumber() + " zurückgegeben");
							// TODO: Nur einen Clip repainten
							repaint();
						}
					}
					return;
				} else if (isSecondIconHit(e, index)) {
					if (library.isBookLent(selected)) {
						System.out.println("Reservieren");
					}
					System.out.println("Zweite Buch-Aktion! (Buch ausleihen / reservieren)");
					return;
				}
				tabModel.setActiveBook(selected);
				tabModel.setActiveTab(LibraryTabbedPaneModel.BOOK_TAB);
			}
			
			private boolean isFirstIconHit(MouseEvent e, int index) {
				return e.getX() > getWidth() - 120
				&& e.getX() < getWidth() - 88
				&& e.getY() > 30 + getCellHeight() * index
				&& e.getY() < 62 + getCellHeight() * index;
			}

			private boolean isSecondIconHit(MouseEvent e, int index) {
				return e.getX() > getWidth() - 120 + 40
						&& e.getX() < getWidth() - 88 + 40
						&& e.getY() > 30 + getCellHeight() * index
						&& e.getY() < 62 + getCellHeight() * index;
			}

			private int getCellHeight() {
				// Hardcoded for laziness reasons. List returns effective
				// height, not maximum height.
				return 64;
			}

			/**
			 * 
			 private boolean isSecondIconHit(MouseEvent e) { return e.getX() >
			 * getWidth() - 120 + 40 && e.getX() < getWidth() - 88 +40 &&
			 * e.getY() > 98 && e.getY() < 130; }
			 * 
			 * private boolean isFirstIconHit(MouseEvent e) { return e.getX() >
			 * getWidth() - 120 && e.getX() < getWidth() - 88 && e.getY() > 98
			 * && e.getY() < 130; }
			 * 
			 */
		});

		add(new JScrollPane(resultList));
	}

	private int eventToListIndex(MouseEvent e) {
		int index = resultList.locationToIndex(new Point(e.getX(), e.getY()));
		return index;
	}

	/**
	 * Dynamically adapt title length to size of list by telling the cell
	 * renderer its size.
	 */
	public void paint(Graphics g) {
		// TODO: Wert anders ermittlen, damit horizontal scrollbar? [Martin]
		cellRenderer.setPreferredWidth(this.getWidth());
		super.paint(g);
	}
}
