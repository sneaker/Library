package presentation.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import presentation.model.ModelController;

public class TabBookPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private TabBookDetailJPanel detailPanel;
	private JPanel contentPanel;
	ModelController controller;
	private ActionBookPanel actionPanel;
	private TabBookLoanJPanel loanPanel;

	public TabBookPanel(ModelController controller) {
		this.controller = controller;
		setLayout(new BorderLayout());

		initContentPanel();
		initActionPanel();
	}

	private void initContentPanel() {
		contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());

		detailPanel = new TabBookDetailJPanel(controller);
		loanPanel = new TabBookLoanJPanel(controller);

		contentPanel.add(detailPanel, BorderLayout.CENTER);
		contentPanel.add(loanPanel, BorderLayout.SOUTH);

		add(contentPanel, BorderLayout.CENTER);		
	}

	private void initActionPanel() {
		actionPanel = new ActionBookPanel(controller);
		add(actionPanel, BorderLayout.EAST);
	}
}
