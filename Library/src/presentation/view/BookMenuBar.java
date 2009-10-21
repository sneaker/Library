package presentation.view;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

/**
 * Repräsentiert und verwaltet die Menübar für die Bibliotheksapplikation.
 * Überwacht den Status der aktiven Menü-Tabs, um die Menübar entsprechend
 * anzupassen.
 */
public class BookMenuBar extends JMenuBar implements Observer {

	private static final long serialVersionUID = 1L;
	private JMenuItem reservedMenuItem;
	private JMenuItem ausgelieheneMenuItem;
	private JMenuItem damagedBooksMenuItem;
	private JMenuItem allBooksMenuItem;
	private JRadioButtonMenuItem benutzerDetailsItem;
	private JRadioButtonMenuItem buchdetailsMenuItem;
	private JRadioButtonMenuItem rechercheMenuItem;
	private AbstractAction exitAction;
	private JMenuItem exitMenuItem;
	private JMenu viewMenu;
	private JMenu searchMenu;
	private JMenu helpMenu;
	private JMenu bookMenu;
	private JMenuItem lendBokMenuItem;
	private JMenuItem editBookMenuItem;
	private JMenu userMenu;
	private JMenuItem editUserMenuItem;
	private JMenuItem resetMenuItem;
	private JSeparator separator;
	private JMenuItem createUserMenuItem;
	private AbstractButton aboutMenuItem;

	public BookMenuBar() {
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
			resetMenuItem = new JMenuItem("Neu");
			resetMenuItem.setAccelerator(KeyStroke.getKeyStroke("F4"));
			fileMenu.add(resetMenuItem);

			separator = new JSeparator();
			fileMenu.add(separator);

			exitMenuItem = new JMenuItem("Beenden");
			fileMenu.add(exitMenuItem);
			exitMenuItem.setAction(getExitAction());
		}
	}

	private void initViewMenu() {
		viewMenu = new JMenu();
		this.add(viewMenu);
		viewMenu.setText("Ansicht");
		viewMenu.setMnemonic(java.awt.event.KeyEvent.VK_A);
		{
			ButtonGroup viewGroup = new ButtonGroup();

			rechercheMenuItem = new JRadioButtonMenuItem();
			rechercheMenuItem.setText("Recherche");
			rechercheMenuItem.setAccelerator(KeyStroke.getKeyStroke("F5"));
			rechercheMenuItem.setSelected(true);
			viewGroup.add(rechercheMenuItem);
			viewMenu.add(rechercheMenuItem);

			buchdetailsMenuItem = new JRadioButtonMenuItem();
			buchdetailsMenuItem.setText("Buchdetails");
			buchdetailsMenuItem.setAccelerator(KeyStroke.getKeyStroke("F6"));
			viewGroup.add(buchdetailsMenuItem);
			viewMenu.add(buchdetailsMenuItem);

			benutzerDetailsItem = new JRadioButtonMenuItem();
			benutzerDetailsItem.setText("Benutzerdetails");
			benutzerDetailsItem.setAccelerator(KeyStroke.getKeyStroke("F7"));
			viewGroup.add(benutzerDetailsItem);
			viewMenu.add(benutzerDetailsItem);
		}
	}

	private void initSearchMenu() {
		searchMenu = new JMenu();
		this.add(searchMenu);
		searchMenu.setText("Recherche");
		searchMenu.setMnemonic(java.awt.event.KeyEvent.VK_R);

		allBooksMenuItem = new JMenuItem();
		allBooksMenuItem.setText("Alle Bücher");
		searchMenu.add(allBooksMenuItem);

		damagedBooksMenuItem = new JMenuItem();
		damagedBooksMenuItem.setText("Beschädigte Bücher");
		searchMenu.add(damagedBooksMenuItem);

		searchMenu.add(getAusgelieheneMenuItem());
		searchMenu.add(getReservedMenuItem());
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
		aboutMenuItem.setMnemonic(java.awt.event.KeyEvent.VK_B);
		helpMenu.add(aboutMenuItem);
	}

	private AbstractAction getExitAction() {
		if (exitAction == null) {
			exitAction = new AbstractAction("Beenden", null) {
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent evt) {
					System.exit(0);
				}
			};
		}
		return exitAction;
	}

	private JMenuItem getAusgelieheneMenuItem() {
		if (ausgelieheneMenuItem == null) {
			ausgelieheneMenuItem = new JMenuItem();
			ausgelieheneMenuItem.setText("Ausgeliehene Bücher");
		}
		return ausgelieheneMenuItem;
	}

	private JMenuItem getReservedMenuItem() {
		if (reservedMenuItem == null) {
			reservedMenuItem = new JMenuItem();
			reservedMenuItem.setText("Reservierte Bücher");
		}
		return reservedMenuItem;
	}

	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

		// Subscribe beim MainWindowModel
		// Hole aktiven Tab und zeige entsprechendes Menü (toggleMenu())
		// Ändere die Radio-Buttons der Ansicht, wenn Tab wechselt

	}

}
