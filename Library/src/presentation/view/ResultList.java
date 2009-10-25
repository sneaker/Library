package presentation.view;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import presentation.model.MainWindowModel;
import domain.Book;

public class ResultList extends JList {

	private static final long serialVersionUID = 1L;
	private JList resultList;
	private MainWindowModel mainWindowModel;
	
	public ResultList(MainWindowModel mainWindowModel) {
		this.mainWindowModel = mainWindowModel;
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
				Book selectedBook = (Book)resultList.getModel()
						.getElementAt(
								resultList.locationToIndex(new Point(e.getX(),
										e.getY())));
				mainWindowModel.setActiveBook(selectedBook);
				mainWindowModel.setActiveTab(presentation.model.MainWindowModel.BOOK_TAB);
			}
		});

		JScrollPane resultScroller = new JScrollPane(resultList);
		resultList.setVisibleRowCount(3);

		add(resultScroller);
	}

}
