package presentation.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import domain.Book;
import domain.Library;

import presentation.model.ActionPanelModel;
import presentation.model.LibTabPaneModel;
import presentation.model.MainWindowModel;

/**
 * Represents the tabbed pane on which the (three) main panes are placed.
 */
public class LibTabPane extends JTabbedPane implements Observer {

	private static final long serialVersionUID = 1L;
	private JPanel[] tabPanel;
	private MainWindowModel model;
	private String[][] tabInformation = {
			{ "Recherche", "img/search.png",
					"Suchen nach Benutzern oder Büchern" },
			{ "Buch", "img/book.png", "Details eines Buches anzeigen" },
			{ "Benutzer", "img/user.png",
					"Personalien und Ausleihen eines Benutzers anzeigen" } };
	private LibTabPaneModel tabModel;
	private final Library library;

	public LibTabPane(MainWindowModel model, Library library) {
		this.model = model;
		this.library = library;
		tabModel = new LibTabPaneModel();
		tabModel.addObserver(this);
		initGUI();
		initChangeListener();
	}

	private void initGUI() {
		tabPanel = new JPanel[tabInformation.length];
		ActionPanelModel action_panel_model = new ActionPanelModel(library, tabModel);

		tabPanel[0] = new TabSearchPanel(tabModel, library, action_panel_model);
		tabPanel[1] = new TabBookPanel(tabModel, library, action_panel_model);
		tabPanel[2] = new TabUserPanel(tabModel, library, action_panel_model);

		for (int i = 0; i < tabInformation.length; i++) {
			this.addTab(null, null, tabPanel[i], tabInformation[i][2]);
			ImageIcon image = new ImageIcon(tabInformation[i][1]);
			JLabel paneTitle = new JLabel(tabInformation[i][0], image,
					JLabel.TRAILING);
			paneTitle.setVerticalTextPosition(JLabel.BOTTOM);
			paneTitle.setHorizontalTextPosition(JLabel.CENTER);
			this.setTabComponentAt(i, paneTitle);
		}

		this.setTabPlacement(JTabbedPane.LEFT);
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
		return (TabSearchPanel) (tabPanel[MainWindowModel.SEARCH_TAB]);
	}

	public TabBookPanel getBookPanel() {
		return (TabBookPanel) (tabPanel[MainWindowModel.BOOK_TAB]);
	}
	
	public String getActiveTabTitle() {
		if (getSelectedIndex() > tabInformation.length)
			return "";
		return tabInformation[getSelectedIndex()][0];
	}

	public void update(Observable o, Object arg) {
		if (arg instanceof Book) {
			Book newBook = (Book)arg;
			model.setActiveBook(newBook);
			getBookPanel().getModel().setActiveBook(newBook);
		}
		switchTo(tabModel.getActiveTab());
	}

}
