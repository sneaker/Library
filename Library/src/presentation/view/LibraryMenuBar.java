package presentation.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import presentation.control.BookCreateActionListener;
import presentation.control.FileExitAction;
import presentation.control.HelpAboutDialogActionListener;
import presentation.control.NewSearchText;
import presentation.control.ShowAvailableBooksAction;
import presentation.control.ShowDefectBooks;
import presentation.control.UserCreateActionListener;
import presentation.model.ModelController;
import util.ResManager;

/**
 * Repräsentiert und verwaltet die Menübar für die Bibliotheksapplikation.
 * Überwacht den Status der aktiven Menü-Tabs, um die Menübar entsprechend
 * anzupassen.
 */
public class LibraryMenuBar extends JMenuBar implements Observer {
	private static final long serialVersionUID = 1L;
	private JMenuItem damagedBooksMenuItem;
	private JMenuItem allBooksMenuItem;
	private JRadioButtonMenuItem searchMenuItem;
	private JRadioButtonMenuItem bookMenuItem;
	private JRadioButtonMenuItem userMenuItem;
	private JMenuItem exitMenuItem;
	private JMenu viewMenu;
	private JMenu searchMenu;
	private JMenu bookMenu;
	private JMenu userMenu;
	private JMenu helpMenu;
	private JMenuItem lendBokMenuItem;
	private JMenuItem editBookMenuItem;
	private JMenuItem editUserMenuItem;
	private JMenuItem resetMenuItem;
	private JSeparator separator;
	private JMenuItem createUserMenuItem;
	private AbstractButton aboutMenuItem;
	private ButtonGroup viewGroup;
	private ModelController controller;
	private JMenuItem createUserSearchMenuItem;
	private JMenuItem createBookSearchMenuItem;
	private JMenuItem availableBooksMenuItem;
	private JMenuItem lentBooksMenuItem;

	public LibraryMenuBar(ModelController controller) {
		this.controller = controller;
		initFileMenu();
		initViewMenu();

		initSearchMenu();
		initBookMenu();
		initUserMenu();

		initHelpMenu();
		controller.tabbed_model.addObserver(this);
		controller.library.addObserver(this);
	}

	private void initFileMenu() {
		JMenu fileMenu = new JMenu();
		this.add(fileMenu);
		fileMenu.setText("Datei");
		fileMenu.setMnemonic(java.awt.event.KeyEvent.VK_D);
		{
			resetMenuItem = new JMenuItem("Aufräumen");
			resetMenuItem.setAccelerator(KeyStroke.getKeyStroke("F4"));
			resetMenuItem.setMnemonic('n');
			resetMenuItem.setRolloverEnabled(true);
			resetMenuItem.setIcon(ResManager.getImage("reset16x16h.png"));
			resetMenuItem
					.setRolloverIcon(ResManager.getImage("reset16x16.png"));
			resetMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controller.setUserTabActive();
					controller.resetSearchText();
					controller.setSearchTabActive();
					controller.booktab_model.clearBook();
					controller.resetActiveCustomer();
					controller.status_model.setTempStatus("Neustart");
				}
			});
			fileMenu.add(resetMenuItem);

			separator = new JSeparator();
			fileMenu.add(separator);

			createUserSearchMenuItem = new JMenuItem();
			createUserSearchMenuItem.setText("Neuer Benutzer");
			createUserSearchMenuItem.setIcon(ResManager
					.getImage("newcustomer16x16h.png"));
			createUserSearchMenuItem.setRolloverIcon(ResManager
					.getImage("newcustomer16x16.png"));
			createUserSearchMenuItem
					.addActionListener(new UserCreateActionListener(controller));
			createUserSearchMenuItem.setMnemonic('e');
			fileMenu.add(createUserSearchMenuItem);

			createBookSearchMenuItem = new JMenuItem();
			createBookSearchMenuItem.setText("Neues Buch");
			createBookSearchMenuItem.setIcon(ResManager
					.getImage("newbook16x16h.png"));
			createBookSearchMenuItem.setRolloverIcon(ResManager
					.getImage("newbook16x16.png"));
			createBookSearchMenuItem
					.addActionListener(new BookCreateActionListener(controller));
			createBookSearchMenuItem.setMnemonic('e');
			fileMenu.add(createBookSearchMenuItem);

			separator = new JSeparator();
			fileMenu.add(separator);

			exitMenuItem = new JMenuItem("Beenden");
			exitMenuItem.addActionListener(new FileExitAction());
			exitMenuItem.setMnemonic('b');
			exitMenuItem.setIcon(ResManager.getImage("exit16x16h.png"));
			exitMenuItem.setRolloverIcon(ResManager.getImage("exit16x16.png"));
			fileMenu.add(exitMenuItem);
		}
	}

	private void initViewMenu() {
		viewMenu = new JMenu();
		this.add(viewMenu);
		viewMenu.setText("Ansicht");
		viewMenu.setMnemonic(java.awt.event.KeyEvent.VK_A);
		{
			viewGroup = new ButtonGroup();

			searchMenuItem = new JRadioButtonMenuItem();
			searchMenuItem.setText("Recherche");
			searchMenuItem.setAccelerator(KeyStroke.getKeyStroke("F5"));
			searchMenuItem.setMnemonic('c');
			searchMenuItem.setSelected(true);
			searchMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controller.setSearchTabActive();
				}
			});
			viewGroup.add(searchMenuItem);
			viewMenu.add(searchMenuItem);

			bookMenuItem = new JRadioButtonMenuItem();
			bookMenuItem.setText("Buchdetails");
			bookMenuItem.setAccelerator(KeyStroke.getKeyStroke("F6"));
			bookMenuItem.setMnemonic('b');
			bookMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controller.setBookTabActive();
				}
			});
			viewGroup.add(bookMenuItem);
			viewMenu.add(bookMenuItem);

			userMenuItem = new JRadioButtonMenuItem();
			userMenuItem.setText("Benutzerdetails");
			userMenuItem.setAccelerator(KeyStroke.getKeyStroke("F7"));
			userMenuItem.setMnemonic('u');
			userMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controller.setUserTabActive();
				}
			});
			viewGroup.add(userMenuItem);
			viewMenu.add(userMenuItem);
		}
	}

	private void initSearchMenu() {
		searchMenu = new JMenu();
		this.add(searchMenu);
		searchMenu.setText("Recherche");
		searchMenu.setMnemonic(java.awt.event.KeyEvent.VK_R);

		allBooksMenuItem = new JMenuItem();
		allBooksMenuItem.setText("Neue Suche");
		allBooksMenuItem.setIcon(ResManager.getImage("search16x16h.png"));
		allBooksMenuItem
				.setRolloverIcon(ResManager.getImage("search16x16.png"));
		allBooksMenuItem.addActionListener(new NewSearchText(controller));
		allBooksMenuItem.setMnemonic('n');
		searchMenu.add(allBooksMenuItem);

		searchMenu.add(new JSeparator());

		availableBooksMenuItem = new JMenuItem();
		availableBooksMenuItem.setText("Verfügbare Bücher");
		availableBooksMenuItem.setMnemonic('V');
		availableBooksMenuItem.setIcon(ResManager
				.getImage("availablebooks16x16h.png"));
		availableBooksMenuItem.setRolloverIcon(ResManager
				.getImage("availablebooks16x16.png"));
		availableBooksMenuItem.addActionListener(new ShowAvailableBooksAction(
				controller));
		searchMenu.add(availableBooksMenuItem);

		damagedBooksMenuItem = new JMenuItem();
		damagedBooksMenuItem.setText("Beschädigte Bücher");
		damagedBooksMenuItem.setMnemonic('d');
		damagedBooksMenuItem.setIcon(ResManager
				.getImage("wastebooks16x16h.png"));
		damagedBooksMenuItem.setRolloverIcon(ResManager
				.getImage("wastebooks16x16.png"));
		damagedBooksMenuItem.addActionListener(new ShowDefectBooks(controller));
		searchMenu.add(damagedBooksMenuItem);

		lentBooksMenuItem = new JMenuItem();
		lentBooksMenuItem.setText("Ausgeliehene Bücher");
		lentBooksMenuItem.setMnemonic('l');
		lentBooksMenuItem.setIcon(ResManager.getImage("allloans16x16h.png"));
		lentBooksMenuItem.setRolloverIcon(ResManager
				.getImage("allloans16x16.png"));
		searchMenu.add(lentBooksMenuItem);
	}

	private void initBookMenu() {
		bookMenu = new JMenu();
		this.add(bookMenu);
		bookMenu.setText("Buch");
		bookMenu.setMnemonic(java.awt.event.KeyEvent.VK_B);

		lendBokMenuItem = new JMenuItem();
		lendBokMenuItem.setText("Dieses Buch Ausleihen");
		bookMenu.add(lendBokMenuItem);

		editBookMenuItem = new JMenuItem();
		editBookMenuItem.setText("Katalogdaten editieren");
		bookMenu.add(editBookMenuItem);

		bookMenu.setVisible(false);
	}

	private void initUserMenu() {
		userMenu = new JMenu();
		this.add(userMenu);
		userMenu.setText("Benutzer");
		userMenu.setMnemonic(java.awt.event.KeyEvent.VK_B);

		editUserMenuItem = new JMenuItem();
		editUserMenuItem.setText("Personalien editieren");
		userMenu.add(editUserMenuItem);

		createUserMenuItem = new JMenuItem();
		createUserMenuItem.setText("Neuen Benutzer erstellen");
		userMenu.add(createUserMenuItem);

		userMenu.setVisible(false);
	}

	private void initHelpMenu() {
		helpMenu = new JMenu();
		this.add(helpMenu);
		helpMenu.setText("Hilfe");
		helpMenu.setMnemonic(java.awt.event.KeyEvent.VK_H);

		aboutMenuItem = new JMenuItem();
		aboutMenuItem.setText("Über");
		aboutMenuItem.setIcon(ResManager.getImage("info16x16h.png"));
		aboutMenuItem.setRolloverIcon(ResManager.getImage("info16x16.png"));
		aboutMenuItem.setMnemonic(java.awt.event.KeyEvent.VK_B);
		aboutMenuItem.addActionListener(new HelpAboutDialogActionListener(
				controller));
		helpMenu.add(aboutMenuItem);
	}

	/**
	 * When switching to another menu tab, this changes the menu bar to display
	 * the corresponding actions.
	 * 
	 * @param activeTab
	 *            The new menu which should be made visible
	 */
	public void setActiveViewIndex(int activeTab) {
		searchMenu.setVisible(activeTab == 0);
		bookMenu.setVisible(activeTab == 1);
		userMenu.setVisible(activeTab == 2);

		searchMenuItem.setSelected(activeTab == 0);
		bookMenuItem.setSelected(activeTab == 1);
		userMenuItem.setSelected(activeTab == 2);
	}

	public void update(Observable o, Object arg) {
		setActiveViewIndex(controller.tabbed_model.getActiveTab());
	}

}
