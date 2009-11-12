package presentation.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
		controller.usertab_model.addObserver(this);
		controller.activeuser_model.addObserver(this);

		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout());
		initContentPane();
		initActionPanel();
	}

	private void initContentPane() {
		contentPanel = new JPanel();
		contentPanel.setLayout(new GridBagLayout());
		initDetailPanel();
		initLoanPanel();
		add(contentPanel, BorderLayout.CENTER);
	}

	private void initDetailPanel() {
		detailPanel = new TabUserDetailJPanel(controller);
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.weighty = 0.0001;
		contentPanel.add(detailPanel, c);
	}

	private void initLoanPanel() {
		loanPanel = new TabUserLoanJPanel(controller);
		loanPanel.setVisible(false);
		GridBagConstraints c;
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1.0;
		c.weighty = 1;
		contentPanel.add(loanPanel, c);
	}

	private void initActionPanel() {
		action_user_panel = new ActionUserPanel(controller);
		add(action_user_panel, BorderLayout.EAST);
	}
	
	public void update(Observable o, Object arg) {
		detailPanel.update(null, null);
		loanPanel.update(null, null);
		action_user_panel.update(null, null);
	}
}
