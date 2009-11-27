package presentation.view;

import java.util.LinkedHashMap;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.border.TitledBorder;

import presentation.model.ControllerFacade;

/**
 * Holds common behavior of the action button storing panel and initializes its
 * layout. When inheriting, the to-be-shown buttons have to be specified and an
 * update method has to be set up changing the button states appropriately.
 */
public abstract class AbstractActionPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private static final String ACTION_PANEL_TITLE = "Aktionen";
	private static final Alignment leading = GroupLayout.Alignment.LEADING;
	private static final int MIN = 0;
	private static final int PS = GroupLayout.PREFERRED_SIZE;
	private static final short MAX = Short.MAX_VALUE;
	private static final int ACTION_HEIGHT = 40;
	private static final int ACTION_WIDTH = 260;

	protected ControllerFacade controller;
	protected JPanel button_panel;
	protected JScrollPane pane;
	protected LinkedHashMap<String, ActionButton> buttons;

	private GroupLayout layout;

	public AbstractActionPanel(ControllerFacade controller) {
		this.controller = controller;
		controller.action_model.addObserver(this);
		buttons = new LinkedHashMap<String, ActionButton>();

		initGui();
	}

	private void initGui() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new TitledBorder(ACTION_PANEL_TITLE));

		initButtonPanel();
		initActionButtons();
		setGroupLayout();
		wrapButtonPanelIntoScrollPane();
	}

	private void wrapButtonPanelIntoScrollPane() {
		JScrollPane scrollPane = new JScrollPane(button_panel);
		scrollPane.setBorder(null);
		pane = scrollPane;
		add(pane);
	}

	private void initButtonPanel() {
		button_panel = new JPanel();
		button_panel
				.setLayout(new BoxLayout(button_panel, BoxLayout.PAGE_AXIS));

		layout = new GroupLayout((JComponent) button_panel);
		button_panel.setLayout(layout);
	}

	/**
	 * All needed action buttons should be filled into the buttons-Hash-Map. Use
	 * the following guideline: <br />
	 * 
	 * buttons.put("a1", new ActionButton("title", "img-high", "img"));
	 * buttons.get("a1").addActionListener(new YourActionListener(controller));
	 */
	protected abstract void initActionButtons();

	private void setGroupLayout() {
		ParallelGroup parallelgroup = layout.createParallelGroup();
		SequentialGroup sequentalgroup = layout.createSequentialGroup();

		for (ActionButton button : buttons.values()) {
			parallelgroup.addComponent(button, leading, MIN, ACTION_WIDTH, MAX);
			sequentalgroup.addComponent(button, PS, ACTION_HEIGHT, PS);
		}
		layout.setHorizontalGroup(parallelgroup);
		layout.setVerticalGroup(sequentalgroup);
	}
}
