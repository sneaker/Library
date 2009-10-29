package presentation.view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.plaf.ListUI;

import presentation.model.LibTabPaneModel;
import presentation.model.MainWindowModel;
import presentation.model.SearchResultListModel;
import domain.Book;
import domain.Customer;
import domain.Library;
import domain.Loan;

/**
 * Displays search results for books and users combined with specific actions
 * for each item.
 */
public class ResultList extends JList {

	private static final long serialVersionUID = 1L;
	private JList resultList;
	private final LibTabPaneModel tabModel;
	private final Library library;
	private ResultCellRenderer cellRenderer;

	public ResultList(LibTabPaneModel tabModel, Library library) {
		this.tabModel = tabModel;
		this.library = library;
		setLayout(new BorderLayout());
		initResultList();
	}

	private void initResultList() {
		resultList = new JList();
		resultList.setModel(new SearchResultListModel(library));
		cellRenderer = new ResultCellRenderer(library);
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
					Book selected = (Book)o;
					handleBookClick(e, index, selected);
					return;
				}
				
				if(o instanceof Customer) {
					Customer selected = (Customer)o;
					tabModel.setActiveCustomer(selected);
					tabModel.setActiveTab(MainWindowModel.USER_TAB);
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
				tabModel.setActiveTab(MainWindowModel.BOOK_TAB);
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
