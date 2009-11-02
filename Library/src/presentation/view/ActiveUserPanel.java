package presentation.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import presentation.model.ActiveUserPanelModel;
import presentation.model.ModelController;

public class ActiveUserPanel extends JPanel implements Observer {
	private static final String USER_IMAGE_PATH = "img/user.png";
	private static final int USER_ICON_SIZE = 32;
	private static final Insets PANEL_INSETS = new Insets(3, 10, 3, 10);
	private static final String DEFAULT_ACTIVE_USER_TEXT = "Kein aktiver Benutzer ausgewählt";
	private static final long serialVersionUID = 1L;
	private JLabel activeUserLabel;
	private JButton clearButton;
	private ModelController controller;
	private ActiveUserPanelModel model;

	public ActiveUserPanel(ModelController controller) {
		this.controller = controller;
		model = controller.activeuser_model;
		model.addObserver(this);
		setLayout(new BorderLayout());
		activeUserLabel = new JLabel(DEFAULT_ACTIVE_USER_TEXT);
		activeUserLabel.setForeground(Color.red);
		ImageIcon img = new ImageIcon(new ImageIcon(USER_IMAGE_PATH).getImage()
				.getScaledInstance(USER_ICON_SIZE, USER_ICON_SIZE,
						Image.SCALE_DEFAULT));
		activeUserLabel.setIcon(img);
		activeUserLabel.setBorder(new EmptyBorder(PANEL_INSETS));
		add(activeUserLabel, BorderLayout.WEST);
		
		clearButton = new JButton("Benutzer Deaktivieren");
		clearButton.setEnabled(false);
		clearButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				model.clearUser();
				clearButton.setEnabled(false);
			}
		});
		add(clearButton, BorderLayout.EAST);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof String) {
			if (arg == "none") {
				activeUserLabel.setText(DEFAULT_ACTIVE_USER_TEXT);
				clearButton.setEnabled(false);
			}
			else {
				activeUserLabel.setText("Aktivierter Benutzer: " + (String) arg);
				clearButton.setEnabled(true);
			}
		}
	}
}
