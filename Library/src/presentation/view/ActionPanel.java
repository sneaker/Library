package presentation.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class ActionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final String ACTION_PANEL_TITLE = "Aktionen";

	public ActionPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(new TitledBorder(ACTION_PANEL_TITLE));
	
		JScrollPane pane = new JScrollPane();
		pane.setPreferredSize(new Dimension(100,100));
		
		this.add(pane);
		
	}
}
