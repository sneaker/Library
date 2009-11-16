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

import presentation.model.ActionPanelModel;
import presentation.model.ModelController;

public abstract class AbstractActionPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	protected ModelController controller;
	protected ActionPanelModel model;
	protected JPanel button_panel;
	protected JScrollPane pane;

	protected LinkedHashMap<String, ActionButton> buttons;

	private static final String ACTION_PANEL_TITLE = "Aktionen";
	
	private static final int ACTION_HEIGHT = 40;
	private static final int ACTION_WIDTH = 260;

	private GroupLayout layout;
	private Alignment leading;
	private short max;
	private int ps;

	public AbstractActionPanel(ModelController controller) {
		this.controller = controller;
		buttons = new LinkedHashMap<String, ActionButton>();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new TitledBorder(ACTION_PANEL_TITLE));

		model = controller.action_model;
		model.addObserver(this);

		button_panel = new JPanel();
		button_panel
				.setLayout(new BoxLayout(button_panel, BoxLayout.PAGE_AXIS));

		layout = new GroupLayout((JComponent) button_panel);
		button_panel.setLayout(layout);

		leading = GroupLayout.Alignment.LEADING;
		max = Short.MAX_VALUE;
		ps = GroupLayout.PREFERRED_SIZE;

		initActionButtons();

		setGroupLayout();

		JScrollPane scrollPane = new JScrollPane(button_panel);
		scrollPane.setBorder(null);
		pane = scrollPane;
		add(pane);
	}

	protected abstract void initActionButtons();

	private void setGroupLayout() {
		ParallelGroup parallelgroup = layout.createParallelGroup();
		SequentialGroup sequentalgroup = layout.createSequentialGroup();

		for (ActionButton button : buttons.values()) {
			parallelgroup.addComponent(button, leading, 0, ACTION_WIDTH, max);
			sequentalgroup.addComponent(button, ps, ACTION_HEIGHT, ps);
		}
		layout.setHorizontalGroup(parallelgroup);
		layout.setVerticalGroup(sequentalgroup);
	}
}
