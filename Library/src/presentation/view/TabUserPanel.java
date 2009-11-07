package presentation.view;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import presentation.model.ModelController;

public class TabUserPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	ModelController controller;
	private ActionUserPanel action_user_panel;
	private JPanel contentPanel;
	private TabUserDetailJPanel detailPanel;
	private TabUserLoanJPanel loanPanel;

	public TabUserPanel(ModelController controller) {
		this.controller = controller;
		setLayout(new BorderLayout());
		controller.usertab_model.addObserver(this);

		initContentPane();
		initActionPanel();
	}

	private void initContentPane() {
		contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		detailPanel = new TabUserDetailJPanel(controller);
		loanPanel = new TabUserLoanJPanel(controller);

		contentPanel.add(detailPanel, BorderLayout.CENTER);
		contentPanel.add(loanPanel, BorderLayout.SOUTH);

		add(contentPanel, BorderLayout.CENTER);
	}

	private void initActionPanel() {
		action_user_panel = new ActionUserPanel(controller);
		add(action_user_panel, BorderLayout.EAST);
	}

	public void update(Observable o, Object arg) {
		detailPanel.update(null, null);
		loanPanel.update(null, null);
	}
}
