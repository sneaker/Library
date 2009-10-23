package presentation.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * Represents the overall main window of the library application. Initializes
 * its properties and elements with their controllers.
 */
public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private TabsPanel tabs;

	public MainWindow() {
		initGUI();
	}

	private void initGUI() {
		setTitle("Recherche - BücherBox");
		setPreferredSize(new java.awt.Dimension(800, 400));
		setMinimumSize(new Dimension(300, 320));
		setSize(750, 450);

		setJMenuBar(new BookMenuBar());

		add(new ActiveUserPanel());

		tabs = new TabsPanel();
		add(tabs, BorderLayout.CENTER);
	}

}
