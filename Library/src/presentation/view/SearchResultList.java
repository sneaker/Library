package presentation.view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import presentation.control.ListItemMouseListener;
import presentation.model.ModelController;
import presentation.model.SearchResultListModel;
import domain.Book;
import domain.Customer;

/**
 * Displays search results for books and users combined with specific actions
 * for each item.
 */
public class SearchResultList extends JList implements ListDataListener  {

	private static final long serialVersionUID = 1L;
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
		setModel(controller.resultlist_model);
		cellRenderer = new SearchResultCellRenderer(controller);
	
		setCellRenderer(cellRenderer);
		setDoubleBuffered(false);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				int index = eventToListIndex(e);
				model.setIndex(index);
				if (index < 0)
					return;
				setSelectedIndex(locationToIndex(new Point(e.getX(), e.getY())));
			}
		});

		addMouseListener(new ListItemMouseListener(controller));
		
		InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(KeyStroke.getKeyStroke("ENTER"), "go");
		ActionMap actionMap = getActionMap();
		setActionMap(actionMap);
		actionMap.put("go", new AbstractAction() {
			private static final long serialVersionUID = 8518026045384869462L;
			public void actionPerformed(ActionEvent e) {
				SearchResultList s = (SearchResultList)(e.getSource());
				Object selectedValue = null;
				if (controller.resultlist_model.getSize() == 1) {
					selectedValue = controller.resultlist_model.getElementAt(0);
				} else if (s.getSelectedIndex() >= controller.resultlist_model.getSize()) {
					return;
				} else {
					selectedValue = s.getSelectedValue();
				}
				if (selectedValue instanceof Book) {
					controller.setActiveBook((Book)selectedValue);
					controller.setBookTabActive();
				} else if (selectedValue instanceof Customer) {
					controller.setActiveCustomer((Customer)selectedValue);
					controller.setUserTabActive();
				}
			}
		});
	}

	private int eventToListIndex(MouseEvent e) {
		int index = locationToIndex(new Point(e.getX(), e.getY()));
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
		repaint();
	}

	public void intervalAdded(ListDataEvent e) {
		
	}

	public void intervalRemoved(ListDataEvent e) {
	}
}
