package presentation.view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import presentation.model.ModelController;
import presentation.model.SearchResultListModel;

/**
 * Displays search results for books and users combined with specific actions
 * for each item.
 */
public class SearchResultList extends JList implements ListDataListener  {

	private static final long serialVersionUID = 1L;
	private JList resultList;
	private SearchResultCellRenderer cellRenderer;
	private ModelController controller;
	SearchResultListModel model;

	public SearchResultList(ModelController controller) {
		this.controller = controller;
		model = controller.resultlist_model;
		model.addListDataListener(this);
		setLayout(new BorderLayout());
		initResultList();
	}

	private void initResultList() {
		resultList = new JList();
		
		resultList.setModel(controller.resultlist_model);
		cellRenderer = new SearchResultCellRenderer(controller);
	
		resultList.setCellRenderer(cellRenderer);
		resultList.setDoubleBuffered(false);
		resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		resultList.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				int index = eventToListIndex(e);
				model.setIndex(index);
				if (index < 0)
					return;
				resultList.setSelectedIndex(resultList
						.locationToIndex(new Point(e.getX(), e.getY())));
			}
		});

		resultList.addMouseListener(new presentation.control.ListItemMouseListener(this, controller));

		JScrollPane scrollList = new JScrollPane(resultList);
		scrollList.setBorder(null);
		add(scrollList);
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
		cellRenderer.setPreferredWidth(this.getWidth());
		super.paint(g);
		model.setCellWidth(getWidth());
		model.setCellHeight(64);
	}

	public void contentsChanged(ListDataEvent e) {
		resultList.repaint();
	}

	public void intervalAdded(ListDataEvent e) {
		
	}

	public void intervalRemoved(ListDataEvent e) {
	}
}
