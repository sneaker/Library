package presentation.view;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import presentation.model.ModelController;

public class TabUserPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	ModelController controller;
	private ActionUserPanel action_user_panel;
	private TabUserDetailJPanel contentPanel;

	public TabUserPanel(ModelController controller) {
		this.controller = controller;
		setLayout(new BorderLayout());
		controller.usertab_model.addObserver(this);

		initContentPane();
		initActionPanel();
	}

	private void initContentPane() {
		contentPanel = new TabUserDetailJPanel(controller);
		contentPanel.setBorder(new TitledBorder("Benutzerinformationen"));
		add(contentPanel, BorderLayout.CENTER);
	}

	private void initActionPanel() {
		action_user_panel = new ActionUserPanel(controller);
		add(action_user_panel, BorderLayout.EAST);
	}

	public void update(Observable o, Object arg) {
		contentPanel.update(null, null);
	}
}
