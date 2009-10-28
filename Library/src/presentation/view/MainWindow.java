package presentation.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import domain.Library;

import presentation.model.MainWindowModel;

/**
 * Represents the overall main window of the library application. Initializes
 * its properties and elements with their controllers.
 */
public class MainWindow extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	public MainWindowModel model;
	private LibraryMenuBar menubar;
	public FindAsYouTypeGlassPane findAsYouTypeGlassPane;

	public MainWindow(Library library) {
		model = new MainWindowModel(library);
		model.addObserver(this);

		setTitle(model.getWindowTitle());
		setPreferredSize(new java.awt.Dimension(800, 400));
		setMinimumSize(new Dimension(460, 355));
		setSize(750, 450);

		initGUI();
	}

	private void initGUI() {
		menubar = new LibraryMenuBar(model);
		setJMenuBar(menubar);
		findAsYouTypeGlassPane = new FindAsYouTypeGlassPane(this);
		add(new ActiveUserPanel(), BorderLayout.NORTH);
		add(model.getTabs(), BorderLayout.CENTER);

		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				model.getTabs().getSearchPanel().requestFocus();
			}
		});

		addGlobalKeyListener();
		setGlassPaneClosesOnEscape();
	}

	private void setGlassPaneClosesOnEscape() {
		KeyStroke escapeKey = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		getRootPane().registerKeyboardAction(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				getGlassPane().setVisible(false);
			}
		}, escapeKey, JComponent.WHEN_IN_FOCUSED_WINDOW);
	}

	private void addGlobalKeyListener() {
		Toolkit.getDefaultToolkit().getSystemEventQueue().push(
				new GlobalKeyListenerEventQueue(this));
	}

	public void update(Observable o, Object arg) {
		menubar.setActiveViewIndex(model.getActiveTabIndex());
		model.getTabs().setSelectedIndex(model.getActiveTabIndex());
		setTitle(model.getWindowTitle());

		// update set active user
		// update status bar
	}

}
