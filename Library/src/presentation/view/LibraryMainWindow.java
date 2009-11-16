package presentation.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;

import presentation.model.MainWindowModel;
import presentation.model.ModelController;
import domain.Library;

/**
 * Represents the overall main window of the library application. Initializes
 * its properties and elements with their controllers.
 */
public class LibraryMainWindow extends JFrame implements Observer {

	private static final int DIALOG_SIZE_MULTIPLICATOR = 25;
	private static final int ASPECT_RATIO_Y = 9;
	private static final int ASPECT_RATIO_X = 16;
	private static final long serialVersionUID = 1L;
	private static final Dimension SIZE = new Dimension(750, 450);
	private static final Dimension MINIMUM_SIZE = new Dimension(645, 390);
	private static final Dimension PREFERRED_SIZE = new Dimension(900, 500);
	private static final String SYSTEM_ICON = "book16x16.png";
	private static final Color SHINY_BLUE = new Color(0xADD8E6);;
	private MainWindowModel model;
	private LibraryMenuBar menubar;
	private ActiveUserPanel userpanel;
	private LibraryTabbedPane tabbedpane;
	private ModelController controller;
	public FindAsYouTypeGlassPane findAsYouTypeGlassPane;

	public LibraryMainWindow(Library library) {
		controller = new ModelController(library);

		model = controller.main_model;
		model.addObserver(this);

		controller.library = library;

		setTitle(model.getTitle());
		setIconImage(Toolkit.getDefaultToolkit().getImage(SYSTEM_ICON));
		setPreferredSize(PREFERRED_SIZE);
		setMinimumSize(MINIMUM_SIZE);
		setSize(SIZE);

		initGUI();
	}

	public MainWindowModel getModel() {
		return model;
	}

	private void initGUI() {
		initMenubar();
		initStatusPanel();
//		findAsYouTypeGlassPane = new FindAsYouTypeGlassPane(this);
		
	    final JPanel glass = (JPanel)getGlassPane();
	    glass.setVisible(true);
		glass.setLayout(new GridBagLayout());

	    JPanel bg = new JPanel();
	    bg.setLayout(new GridBagLayout());
	    bg.setBorder(new LineBorder(Color.BLACK));
	    bg.setBackground(SHINY_BLUE);
	    bg.setPreferredSize(new Dimension((int)(ASPECT_RATIO_X * DIALOG_SIZE_MULTIPLICATOR), (int)(ASPECT_RATIO_Y * DIALOG_SIZE_MULTIPLICATOR)));
	    
	    GridBagConstraints c = new GridBagConstraints();
	    c.gridx = 0;
	    c.gridy = 1;
	    c.anchor = GridBagConstraints.LAST_LINE_END;
	    c.weightx = 1.0;
	    c.weighty = 1.0;
	    c.insets = new Insets(10, 10, 10, 10);
	    JButton glassButton = new JButton("Hide");
	    bg.add(glassButton, c);
		
	    JLabel l = new JLabel("Wollen sie wirklich, wahrhaftig und ganz sicher?");
	    c.gridx = 0;
	    c.gridy = 0;
	    bg.add(l, c);
	    
	    glass.add(bg);

//	    initActiveUserPanel();
//		initTabbedPane();
//		requestFocusOnSearchField();
////		addGlobalKeyListener();
		setGlassPaneClosesOnEscape();
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

	private void addGlobalKeyListener() {
		Toolkit.getDefaultToolkit().getSystemEventQueue().push(
				new GlobalKeyListenerEventQueue(controller, this));
	}

	public void update(Observable o, Object arg) {
		setTitle(model.getTitle());
	}
}
