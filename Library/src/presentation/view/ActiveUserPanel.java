package presentation.view;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ActiveUserPanel extends JPanel {
	private static final String USER_IMAGE_PATH = "img/user.png";
	private static final int USER_ICON_SIZE = 32;
	private static final Insets PANEL_INSETS = new Insets(3, 10, 3, 10);
	private static final String DEFAULT_ACTIVE_USER_TEXT = "Kein aktiver Benutzer ausgewählt";
	private static final long serialVersionUID = 1L;
	private JLabel activeUserLabel;

	public ActiveUserPanel() {
		setLayout(new BorderLayout());
		activeUserLabel = new JLabel(DEFAULT_ACTIVE_USER_TEXT);
		ImageIcon img = new ImageIcon(new ImageIcon(USER_IMAGE_PATH).getImage()
				.getScaledInstance(USER_ICON_SIZE, USER_ICON_SIZE,
						Image.SCALE_DEFAULT));
		activeUserLabel.setIcon(img);
		activeUserLabel.setBorder(new EmptyBorder(PANEL_INSETS));
		add(activeUserLabel, BorderLayout.WEST);
	}
}
