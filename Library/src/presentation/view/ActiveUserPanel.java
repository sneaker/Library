package presentation.view;

import java.awt.BorderLayout;
import java.awt.Color;
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
import domain.Customer;

public class ActiveUserPanel extends JPanel implements Observer {
	private static final String USER_IMAGE_PATH = "img/user32x32.png";
	private static final Insets PANEL_INSETS = new Insets(3, 10, 3, 10);
	private static final String DEFAULT_ACTIVE_USER_TEXT = "Kein aktiver Benutzer ausgewählt";
	private static final long serialVersionUID = 1L;
	private JLabel activeUserLabel;

	private JButton clearButton;
	private ActiveUserPanelModel model;

	public ActiveUserPanel(ModelController controller) {
		model = controller.activeuser_model;
		model.addObserver(this);

		setLayout(new BorderLayout());
		activeUserLabel = new JLabel(DEFAULT_ACTIVE_USER_TEXT);
		activeUserLabel.setForeground(Color.red);
		activeUserLabel.setIcon(new ImageIcon(USER_IMAGE_PATH));
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

	public void update(Observable o, Object arg) {
		Customer customer = model.getCustomer();
		if (customer != null) {
			enableUser(customer);
		} else {
			disableUser();
		}
	}

	private void enableUser(Customer customer) {
		activeUserLabel.setText("Aktivierter Benutzer: "
				+ customer.getSurname() + " " + customer.getName());
		activeUserLabel.setForeground(Color.green);
		clearButton.setEnabled(true);
	}

	private void disableUser() {
		activeUserLabel.setText(DEFAULT_ACTIVE_USER_TEXT);
		activeUserLabel.setForeground(Color.red);
		clearButton.setEnabled(false);
		model.switchtoSearch();
	}
}
