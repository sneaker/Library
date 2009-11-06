package presentation.view;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import presentation.model.ModelController;

public class TabBookPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private TabBookDetailJPanel detailPanel;
	private JPanel contentPanel;
	ModelController controller;
	private ActionBookPanel bookpanel;
	private TabBookLoanJPanel loanPanel;

	public TabBookPanel(ModelController controller) {
		setLayout(new BorderLayout());
		this.controller = controller;
		controller.booktab_model.addObserver(this);

		initContentPanel();
		bookpanel = new ActionBookPanel(controller);
		add(bookpanel, BorderLayout.EAST);
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

	public void update(Observable o, Object arg) {
		// No separate models for those two components for simplicity.
		detailPanel.update(null, null);
		loanPanel.update(null, null);
	}
}
