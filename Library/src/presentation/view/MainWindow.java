package presentation.view;

import javax.swing.JFrame;

/**
 * Represents the overall main window of the library application. Initializes
 * its properties and elements with their controllers.
 */
public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	public MainWindow() {
		initGUI();
	}

	private void initGUI() {
		this.setTitle("Recherche - BücherBox");
		this.setPreferredSize(new java.awt.Dimension(800, 400));

		setJMenuBar(new BookMenuBar());

		this.setSize(400, 300);
	}

}
