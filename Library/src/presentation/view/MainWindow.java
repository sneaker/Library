package presentation.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import presentation.model.MainWindowModel;

/**
 * Represents the overall main window of the library application. Initializes
 * its properties and elements with their controllers.
 */
public class MainWindow extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	private MainWindowModel model;
	private LibraryMenuBar menubar;

	public MainWindow() {
		setTitle("Recherche - BücherBox");
		setPreferredSize(new java.awt.Dimension(800, 400));
		setMinimumSize(new Dimension(460, 355));
		setSize(750, 450);
		model = new MainWindowModel();
		model.addObserver(this);

		initGUI();
	}

	private void initGUI() {
		menubar = new LibraryMenuBar(model);
		setJMenuBar(menubar);

		add(new ActiveUserPanel(), BorderLayout.NORTH);

		add(model.getTabs(), BorderLayout.CENTER);
	}

	public void update(Observable o, Object arg) {
		menubar.setActiveViewIndex(model.getActiveTabIndex());
		model.getTabs().setSelectedIndex(model.getActiveTabIndex());

		// update set active user
		// update status bar
	}

}
