package presentation.view;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class ActiveUserPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JPanel topPanel;

	public ActiveUserPanel() {
		topPanel = new JPanel();
		add(topPanel, BorderLayout.NORTH);
		topPanel.setBorder(BorderFactory
				.createTitledBorder("Gewählter Benutzer"));
	}
}
