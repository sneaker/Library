package presentation.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class FindAsYouTypeGlassPane extends JPanel {

	private static final long serialVersionUID = 1L;
	private final JPanel glass;
	private JButton cancelButton;
	private JPanel msgBox;
	private JTextField text;

	public FindAsYouTypeGlassPane(JFrame mainWindow) {
		glass = (JPanel) mainWindow.getGlassPane();
		glass.setLayout(new GridBagLayout());

		addMessageBox();
		msgBox.add(new JLabel("         "), BorderLayout.EAST);
		msgBox.add(new JLabel("         "), BorderLayout.WEST);
		text = new JTextField("                     ");
		text.setBackground(msgBox.getBackground());
		text.setBorder(new EmptyBorder(0, 0, 0, 0));
		text.setFont(new Font("Dialog", Font.BOLD, 20));
		text.setEnabled(false);
		text.setDisabledTextColor(Color.BLACK);
		JLabel titel = new JLabel("         Schnellsuche            ");
		titel.setFont(new Font("Dialog", Font.PLAIN, 20));
		msgBox.add(titel, BorderLayout.NORTH);
		msgBox.add(text, BorderLayout.CENTER);
		msgBox.setSize(300, 300);
		addCancelButton();

		setHideOnOuterClick();
		setImmuneToClicks(msgBox);
		setCancelButtonListener();
	}
	
	public JPanel getGlassPane() {
		return glass;
	}
	
	public void setText(String text) {
		this.text.setText(text);
		glass.repaint();
	}
	
	private void addMessageBox() {
		msgBox = new JPanel();
		msgBox.setLayout(new BorderLayout(20,20));
		msgBox.setBackground(new Color(0xADD8E6));
		msgBox.setBorder(new LineBorder(new Color(0xADD8E6), 5, true));
		glass.add(msgBox);
	}

	private void addCancelButton() {
		cancelButton = new JButton("Abbrechen");
		cancelButton.setMnemonic('b');
		msgBox.add(cancelButton, BorderLayout.SOUTH);
	}

	private void setHideOnOuterClick() {
		glass.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				glass.setVisible(false);
				repaint();
			}
		});
	}

	private void setCancelButtonListener() {
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				glass.setVisible(false);
				repaint();
			}
		});
	}

	private void setImmuneToClicks(JPanel sec) {
		sec.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				e.consume();
			}
		});
	}
}
