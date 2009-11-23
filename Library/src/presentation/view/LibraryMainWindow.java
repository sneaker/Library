package presentation.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
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
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import presentation.model.ModelController;
import domain.Message;

/**
 * Represents the overall main window of the library application. Initializes
 * its properties and elements with their controllers.
 */
public class LibraryMainWindow extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	private static final Dimension SIZE = new Dimension(750, 450);
	private static final Dimension MINIMUM_SIZE = new Dimension(645, 390);
	private static final Dimension PREFERRED_SIZE = new Dimension(900, 500);
	private static final String SYSTEM_ICON = "book16x16.png";
	private LibraryMenuBar menubar;
	private ActiveUserPanel userpanel;
	private LibraryTabbedPane tabbedpane;
	private ModelController controller;

	public LibraryMainWindow(ModelController controller) {
		this.controller = controller;
		this.controller.main_model.addObserver(this);
		initGUI();
	}

	private void initGUI() {
		setTitle(controller.main_model.getTitle());
		setIconImage(Toolkit.getDefaultToolkit().getImage(SYSTEM_ICON));
		setPreferredSize(PREFERRED_SIZE);
		setMinimumSize(MINIMUM_SIZE);
		setSize(SIZE);

		initMenubar();
		initStatusPanel();

		initActiveUserPanel();
		initTabbedPane();
		requestFocusOnSearchField();
		setGlassPaneClosesOnEscape();
	}

	private void setActiveGlassPane(JPanel dialog) {
		final JPanel glass = (JPanel) getGlassPane();
		glass.removeAll();
		glass.setVisible(true);
		glass.setLayout(new GridBagLayout());
		glass.add(dialog);
	}

	private void initStatusPanel() {
		StatusPanel statusPanel = new StatusPanel(controller);
		add(statusPanel, BorderLayout.SOUTH);
	}

	private void initTabbedPane() {
		tabbedpane = new LibraryTabbedPane(controller);
		add(tabbedpane, BorderLayout.CENTER);
	}

	private void initActiveUserPanel() {
		userpanel = new ActiveUserPanel(controller);
		add(userpanel, BorderLayout.NORTH);
	}

	private void initMenubar() {
		menubar = new LibraryMenuBar(controller);
		setJMenuBar(menubar);
	}

	private void requestFocusOnSearchField() {
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				controller.searchtab_model.setRequestFocus();
			}
		});
	}

	private void setGlassPaneClosesOnEscape() {
		KeyStroke escapeKey = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		getRootPane().registerKeyboardAction(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				getGlassPane().setVisible(false);
			}
		}, escapeKey, JComponent.WHEN_IN_FOCUSED_WINDOW);
	}

	private void updateGlassPane() {
		Message msg = controller.main_model.getActiveMessage();

		if (msg != null)
			setActiveGlassPane(new DialogChoice(msg.getDialogText(), msg
					.getButtonNames(), msg.getButtonActions(), msg
					.getButtonKeys()));
		else
			getGlassPane().setVisible(false);
	}

	public void update(Observable o, Object arg) {
		setTitle(controller.main_model.getTitle());
		updateGlassPane();
	}
}
