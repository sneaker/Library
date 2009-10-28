package presentation.view;

import java.awt.BorderLayout;
import java.util.Observer;

import javax.swing.JPanel;

import presentation.model.ActionPanelModel;

public abstract class TabAbstractPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private ActionPanel action_panel;
;

	public TabAbstractPanel(ActionPanelModel action_panel_model) {
		action_panel = new ActionPanel(action_panel_model);
		setLayout(new BorderLayout());
		add(action_panel, BorderLayout.EAST);
	}

}
