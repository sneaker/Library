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
import presentation.control.BookDefectAction;
import presentation.control.BookEditAction;
import presentation.control.BookEditCancelAction;
import presentation.control.BookEditSaveAction;
import presentation.control.BookLendAction;
import presentation.control.BookReturnAction;
import presentation.control.FileExitAction;
import presentation.control.HelpAboutDialogActionListener;
import presentation.control.NewSearchAction;
import presentation.control.ShowAvailableBooksAction;
import presentation.control.ShowDefectBooks;
import presentation.control.UserCreateActionListener;
import presentation.control.UserReturnLoanAction;
import presentation.control.UserShowLoanBookAction;
import presentation.model.ModelController;
import util.ResManager;
import domain.Book.Condition;

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
	private JMenuItem lendBookMenuItem;
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
	private JMenuItem searchBookMenuItem;
	private JMenuItem returnBookMenuItem;
	private JMenuItem defectBookMenuItem;
	private JMenuItem newBookMenuItem;
	private JMenuItem searchCustomerMenuItem;
	private JMenuItem editOkUserMenuItem;
	private JMenuItem editCancelUserMenuItem;
	private JMenuItem returnBookUserMenuItem;
	private JMenuItem showBookDetailsUserMenuItem;
	private JMenuItem editSaveBookMenuItem;
	private JMenuItem editCancelBookMenuItem;

	public LibraryMenuBar(ModelController controller) {
		this.controller = controller;
		controller.tabbed_model.addObserver(this);
		controller.library.addObserver(this);
		controller.booktab_model.addObserver(this);
		controller.usertab_model.addObserver(this);
		initGui();
	}

	private void initGui() {
		initFileMenu();
		initViewMenu();
		initSearchMenu();
		initBookMenu();
		initUserMenu();
		initHelpMenu();
	}

	private void initFileMenu() {
		JMenu fileMenu = new JMenu();
		this.add(fileMenu);
		fileMenu.setText("Datei");
		fileMenu.setMnemonic(java.awt.event.KeyEvent.VK_D);
		{
			initResetMenuItem(fileMenu);
			separator = new JSeparator();
			fileMenu.add(separator);
			initCreateUserSearchMenuItem(fileMenu);
			initCreateBookSearchMenuItem(fileMenu);
			separator = new JSeparator();
			fileMenu.add(separator);
			initExitMenuItem(fileMenu);
		}
	}

	private void initExitMenuItem(JMenu fileMenu) {
		exitMenuItem = new JMenuItem("Beenden");
		exitMenuItem.addActionListener(new FileExitAction());
		exitMenuItem.setMnemonic('b');
		exitMenuItem.setIcon(ResManager.getImage("exit16x16h.png"));
		exitMenuItem.setRolloverIcon(ResManager.getImage("exit16x16.png"));
		fileMenu.add(exitMenuItem);
	}

	private void initCreateBookSearchMenuItem(JMenu fileMenu) {
		createBookSearchMenuItem = new JMenuItem();
		createBookSearchMenuItem.setText("Neues Buch");
		createBookSearchMenuItem.setIcon(ResManager
				.getImage("newbook16x16h.png"));
		createBookSearchMenuItem.setRolloverIcon(ResManager
				.getImage("newbook16x16.png"));
		createBookSearchMenuItem
				.addActionListener(new BookCreateActionListener(controller));
		createBookSearchMenuItem.setMnemonic('u');
		fileMenu.add(createBookSearchMenuItem);
	}

	private void initCreateUserSearchMenuItem(JMenu fileMenu) {
		createUserSearchMenuItem = new JMenuItem();
		createUserSearchMenuItem.setText("Neuer Benutzer");
		createUserSearchMenuItem.setIcon(ResManager
				.getImage("newcustomer16x16h.png"));
		createUserSearchMenuItem.setRolloverIcon(ResManager
				.getImage("newcustomer16x16.png"));
		createUserSearchMenuItem
				.addActionListener(new UserCreateActionListener(controller));
		createUserSearchMenuItem.setMnemonic('z');
		fileMenu.add(createUserSearchMenuItem);
	}

	private void initResetMenuItem(JMenu fileMenu) {
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
	}

	private void initViewMenu() {
		viewMenu = new JMenu();
		this.add(viewMenu);
		viewMenu.setText("Ansicht");
		viewMenu.setMnemonic(java.awt.event.KeyEvent.VK_A);
		{
			viewGroup = new ButtonGroup();

			initSearchMenuItem();

			initBookMenuItem();

			initMenuItem();
		}
	}

	private void initMenuItem() {
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

	private void initBookMenuItem() {
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
	}

	private void initSearchMenuItem() {
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
	}

	private void initSearchMenu() {
		searchMenu = new JMenu();
		this.add(searchMenu);
		searchMenu.setText("Recherche");
		searchMenu.setMnemonic(java.awt.event.KeyEvent.VK_R);

		initAllBooksMenuItem();
		searchMenu.add(new JSeparator());
		initAvailableBooksMenuItem();
		initDamagedBooksMenuItem();
		initLentBooksMenuItem();
	}

	private void initLentBooksMenuItem() {
		lentBooksMenuItem = new JMenuItem();
		lentBooksMenuItem.setText("Ausgeliehene Bücher");
		lentBooksMenuItem.setMnemonic('l');
		lentBooksMenuItem.setIcon(ResManager.getImage("allloans16x16h.png"));
		lentBooksMenuItem.setRolloverIcon(ResManager
				.getImage("allloans16x16.png"));
		searchMenu.add(lentBooksMenuItem);
	}

	private void initDamagedBooksMenuItem() {
		damagedBooksMenuItem = new JMenuItem();
		damagedBooksMenuItem.setText("Beschädigte Bücher");
		damagedBooksMenuItem.setMnemonic('d');
		damagedBooksMenuItem.setIcon(ResManager
				.getImage("wastebooks16x16h.png"));
		damagedBooksMenuItem.setRolloverIcon(ResManager
				.getImage("wastebooks16x16.png"));
		damagedBooksMenuItem.addActionListener(new ShowDefectBooks(controller));
		searchMenu.add(damagedBooksMenuItem);
	}

	private void initAvailableBooksMenuItem() {
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
	}

	private void initAllBooksMenuItem() {
		allBooksMenuItem = new JMenuItem();
		allBooksMenuItem.setText("Neue Suche");
		allBooksMenuItem.setIcon(ResManager.getImage("search16x16h.png"));
		allBooksMenuItem
				.setRolloverIcon(ResManager.getImage("search16x16.png"));
		allBooksMenuItem.addActionListener(new NewSearchAction(controller));
		allBooksMenuItem.setMnemonic('n');
		searchMenu.add(allBooksMenuItem);
	}

	private void initBookMenu() {
		bookMenu = new JMenu("Buch");
		this.add(bookMenu);
		bookMenu.setMnemonic(java.awt.event.KeyEvent.VK_B);
		initBookMenuItems();
		bookMenu.setVisible(false);
	}

	private void initBookMenuItems() {
		initNewSearchBookMenuItem();
		initReturnBookMenuItem();
		initLendBookMenuItem();
		initEditBookMenuItem();
		initEditSaveBookMenuItem();
		initEditCancelBookMenuItem();
		initDefectMenuItem();
		initNewBookMenuItem();
	}

	private void initNewBookMenuItem() {
		newBookMenuItem = new JMenuItem();
		newBookMenuItem.setText("neues Buch erstellen");
		newBookMenuItem.addActionListener(new BookCreateActionListener(controller));
		bookMenu.add(newBookMenuItem);
	}

	private void initDefectMenuItem() {
		defectBookMenuItem = new JMenuItem();
		defectBookMenuItem.setText("als defekt markieren / ausmustern");
		defectBookMenuItem.addActionListener(new BookDefectAction(controller));
		bookMenu.add(defectBookMenuItem);
	}

	private void initEditBookMenuItem() {
		editBookMenuItem = new JMenuItem();
		editBookMenuItem.setText("Katalogdaten editieren");
		editBookMenuItem.addActionListener(new BookEditAction(controller));
		editBookMenuItem.setMnemonic('e');
		bookMenu.add(editBookMenuItem);
	}
	
	private void initEditSaveBookMenuItem() {
		editSaveBookMenuItem = new JMenuItem();
		editSaveBookMenuItem.setText("Änderungen sichern");
		editSaveBookMenuItem.addActionListener(new BookEditSaveAction(controller));
		editSaveBookMenuItem.setMnemonic('e');
		bookMenu.add(editSaveBookMenuItem);
	}

	private void initEditCancelBookMenuItem() {
		editCancelBookMenuItem = new JMenuItem();
		editCancelBookMenuItem.setText("Änderungen verwerfen");
		editCancelBookMenuItem.addActionListener(new BookEditCancelAction(controller));
		editCancelBookMenuItem.setMnemonic('e');
		bookMenu.add(editCancelBookMenuItem);
	}
	
	private void initLendBookMenuItem() {
		lendBookMenuItem = new JMenuItem();
		lendBookMenuItem.setText("ausleihen");
		lendBookMenuItem.addActionListener(new BookLendAction(controller));
		lendBookMenuItem.setMnemonic('l');
		bookMenu.add(lendBookMenuItem);
	}

	private void initReturnBookMenuItem() {
		returnBookMenuItem = new JMenuItem();
		returnBookMenuItem.setText("zurückgeben");
		returnBookMenuItem.addActionListener(new BookReturnAction(controller));
		returnBookMenuItem.setMnemonic('z');
		bookMenu.add(returnBookMenuItem);
	}

	private void initNewSearchBookMenuItem() {
		searchBookMenuItem = new JMenuItem();
		searchBookMenuItem.setText("Buch suchen");
		searchBookMenuItem.setIcon(ResManager.getImage("search16x16h.png"));
		searchBookMenuItem
				.setRolloverIcon(ResManager.getImage("search16x16.png"));
		searchBookMenuItem.addActionListener(new NewSearchAction(controller));
		searchBookMenuItem.setMnemonic('n');
		bookMenu.add(searchBookMenuItem);
	}

	private void initUserMenu() {
		userMenu = new JMenu();
		this.add(userMenu);
		userMenu.setText("Benutzer");
		userMenu.setMnemonic(java.awt.event.KeyEvent.VK_B);

		initSearchCustomerMenuItem();
		initCustomerEditMenuItem();
		initCustomerEditOkMenuItem();
		initCustomerEditCancelMenuItem();
		initCustomerReturnLoanMenuItem();
		initCustomerLoanDetailMenuItem();
		initCustomerCreateMenuItem();

		userMenu.setVisible(false);
	}

	private void initCustomerCreateMenuItem() {
		createUserMenuItem = new JMenuItem();
		createUserMenuItem.setText("Neuen Benutzer erstellen");
		createUserMenuItem.addActionListener(new UserCreateActionListener(controller));
		createBookSearchMenuItem.setMnemonic('r');
		userMenu.add(createUserMenuItem);
	}

	private void initCustomerLoanDetailMenuItem() {
		showBookDetailsUserMenuItem = new JMenuItem();
		showBookDetailsUserMenuItem.setText("Buchdetails anzeigen");
		showBookDetailsUserMenuItem.addActionListener(new UserShowLoanBookAction(controller));
		showBookDetailsUserMenuItem.setMnemonic('g');
		userMenu.add(showBookDetailsUserMenuItem);
	}

	private void initCustomerReturnLoanMenuItem() {
		returnBookUserMenuItem = new JMenuItem();
		returnBookUserMenuItem.setText("gewähltes Buch zurückgeben");
		returnBookUserMenuItem.addActionListener(new UserReturnLoanAction(controller));
		returnBookUserMenuItem.setMnemonic('z');
		userMenu.add(returnBookUserMenuItem);
	}

	private void initCustomerEditCancelMenuItem() {
		editCancelUserMenuItem = new JMenuItem();
		editCancelUserMenuItem.setText("Änderungen verwerfen");
		editCancelUserMenuItem.addActionListener(new CustomerEditCancelAction(controller));
		editCancelBookMenuItem.setMnemonic('v');
		userMenu.add(editCancelUserMenuItem);
	}

	private void initCustomerEditOkMenuItem() {
		editOkUserMenuItem = new JMenuItem();
		editOkUserMenuItem.setText("Änderungen sichern");
		editOkUserMenuItem.addActionListener(new CustomerEditCancelAction(controller));
		editOkUserMenuItem.setMnemonic('s');
		userMenu.add(editOkUserMenuItem);
	}

	private void initCustomerEditMenuItem() {
		editUserMenuItem = new JMenuItem();
		editUserMenuItem.setText("Personalien editieren");
		editUserMenuItem.addActionListener(new CustomerEditAction(controller));
		editUserMenuItem.setMnemonic('e');
		userMenu.add(editUserMenuItem);
	}

	private void initSearchCustomerMenuItem() {
		searchCustomerMenuItem = new JMenuItem();
		searchCustomerMenuItem.setText("Benutzer suchen");
		searchCustomerMenuItem.setIcon(ResManager.getImage("search16x16h.png"));
		searchCustomerMenuItem
				.setRolloverIcon(ResManager.getImage("search16x16.png"));
		searchCustomerMenuItem.addActionListener(new NewSearchAction(controller));
		searchCustomerMenuItem.setMnemonic('n');
		userMenu.add(searchCustomerMenuItem);
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
	private void setActiveViewIndex(int activeTab) {
		updateToplevelMenu(activeTab);
		if (activeTab == 1)
			handleBookMenuChanged();
		if (activeTab == 2)
			handleCustomerMenuChanged();
	}
	
	private void updateToplevelMenu(int activeTab) {
		searchMenu.setVisible(activeTab == 0);
		bookMenu.setVisible(activeTab == 1);
		userMenu.setVisible(activeTab == 2);
		
		searchMenuItem.setSelected(activeTab == 0);
		bookMenuItem.setSelected(activeTab == 1);
		userMenuItem.setSelected(activeTab == 2);
	}

	private void handleBookMenuChanged() {
		boolean isBookActive = controller.getActiveBook() != null;
		boolean isBookLent = isBookActive && controller.library.isBookLent(controller.getActiveBook());
		boolean isBookLendable = isBookActive && !isBookLent && controller.getActiveBook().getCondition() != Condition.WASTE && controller.getActiveCustomer() != null && controller.library.isCustomerLocked(controller.getActiveCustomer());
		boolean isMarkableAsWaste = isBookActive && controller.getActiveBook().getCondition() != Condition.WASTE;

		returnBookMenuItem.setVisible(isBookLent);
		lendBookMenuItem.setVisible(isBookLendable);
		editBookMenuItem.setVisible(isBookActive && !controller.booktab_model.isEditing());
		editSaveBookMenuItem.setVisible(isBookActive && controller.booktab_model.isEditing() && !controller.booktab_model.isError());
		editCancelBookMenuItem.setVisible(isBookActive && controller.booktab_model.isEditing());
		defectBookMenuItem.setVisible(isMarkableAsWaste);
	}
	
	private void handleCustomerMenuChanged() {
		boolean isCustomerActive = controller.getActiveCustomer() != null;
		editUserMenuItem.setVisible(isCustomerActive && !controller.usertab_model.isEditing());
		editOkUserMenuItem.setVisible(isCustomerActive && controller.usertab_model.isEditing() && !controller.usertab_model.isError());
		editCancelUserMenuItem.setVisible(isCustomerActive && controller.usertab_model.isEditing());
		returnBookUserMenuItem.setVisible(isCustomerActive && controller.usertab_model.getActiveLoan() != null);
		showBookDetailsUserMenuItem.setVisible(isCustomerActive && controller.usertab_model.getActiveLoan() != null);
	}

	public void update(Observable o, Object arg) {
		setActiveViewIndex(controller.tabbed_model.getActiveTab());
	}
}
