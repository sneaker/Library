package presentation.view;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import domain.Book;

/**
 * Displays search results for books and users combined with specific actions
 * for each item.
 */
public class ResultList extends JList {

	private static final long serialVersionUID = 1L;
	private JList resultList;
	private final LibTabPaneModel tabModel;

	public ResultList(LibTabPaneModel tabModel) {
		this.tabModel = tabModel;
		setLayout(new BorderLayout());
		initResultList();
	}

	private void initResultList() {
		resultList = new JList();
		resultList.setModel(new SearchResultListModel());
		resultList.setCellRenderer(new MyCellRenderer());
		resultList.setDoubleBuffered(false);
		resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		resultList.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				resultList.setSelectedIndex(resultList
						.locationToIndex(new Point(e.getX(), e.getY())));
			}
		});

		resultList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Book selectedBook = (Book) resultList.getModel()
						.getElementAt(
								resultList.locationToIndex(new Point(e.getX(),
										e.getY())));
				tabModel.setActiveBook(selectedBook);
				// TODO: Move constant to LibTabPane [Martin]
				tabModel
						.setActiveTab(presentation.model.MainWindowModel.BOOK_TAB);
			}
		});

		JScrollPane resultScroller = new JScrollPane(resultList);
		resultList.setVisibleRowCount(3);

		add(resultScroller);
	}

}
