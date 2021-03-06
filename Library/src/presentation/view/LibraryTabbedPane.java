package presentation.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import presentation.model.LibraryTabbedPaneModel;
import presentation.model.ModelController;
import domain.Book;

/**
 * Represents the tabbed pane on which the (three) main panes are placed.
 */
public class LibraryTabbedPane extends JTabbedPane implements Observer {

	private static final long serialVersionUID = 1L;
	private JPanel[] tabPanel;
	public LibraryTabbedPaneModel model;
	private ModelController controller;
	
	public LibraryTabbedPane(ModelController controller) {
		model = controller.tabbed_model;
		model.addObserver(this);

		this.controller = controller;
		
		initGUI();
		initChangeListener();
	}

	private void initGUI() {
		String[][] tabinformation = model.getTabInformation();
		tabPanel = new JPanel[tabinformation.length];

		tabPanel[0] = new TabSearchPanel(controller);
		tabPanel[1] = new TabBookPanel(controller);
		tabPanel[2] = new TabUserPanel(controller);

		for (int i = 0; i < model.getTabInformation().length; i++) {
			addTab(null, null, tabPanel[i], tabinformation[i][2]);
			ImageIcon image = new ImageIcon(tabinformation[i][1]);
			JLabel paneTitle = new JLabel(tabinformation[i][0], image,
					JLabel.TRAILING);
			paneTitle.setVerticalTextPosition(JLabel.BOTTOM);
			paneTitle.setHorizontalTextPosition(JLabel.CENTER);
			setTabComponentAt(i, paneTitle);
		}

		setTabPlacement(JTabbedPane.LEFT);
	}

	private void initChangeListener() {
		final JTabbedPane t = this;
		addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				model.setActiveTab(t.getSelectedIndex());
			}
		});
	}

	/**
	 * Dient dem ActionListener der JMenuBar oder einem anderen Controller, den
	 * Tab zu wechseln.
	 * 
	 * @param newTabIndex
	 *            TabsPanel.SEARCH, TabsPanel.BOOK oder TabsPanel.USER
	 */
	public void switchTo(int newTabIndex) {
		if (newTabIndex >= getTabCount())
			return;
		setSelectedIndex(newTabIndex);
	}

	public TabSearchPanel getSearchPanel() {
		return (TabSearchPanel) (tabPanel[LibraryTabbedPaneModel.SEARCH_TAB]);
	}

	public TabBookPanel getBookPanel() {
		return (TabBookPanel) (tabPanel[LibraryTabbedPaneModel.BOOK_TAB]);
	}
	
	public void update(Observable o, Object arg) {
		if (arg instanceof Book) {
			Book newBook = (Book)arg;
			controller.booktab_model.setActiveBook(newBook);
		}
		switchTo(model.getActiveTab());
	}

	public LibraryTabbedPane getTabs() {
		return this;
	}

}
