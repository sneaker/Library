package presentation.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
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

	public static final String ACTIVE_USER_COLOR = "<font color=#228b22>";
	public static final String DISABLED_USER_COLOR = "<font color=#cc0000>";

	private ActionButton clearButton;
	private ActiveUserPanelModel model;
	private final ModelController controller;

	public ActiveUserPanel(ModelController controller) {
		this.controller = controller;
		model = controller.activeuser_model;
		model.addObserver(this);

		setLayout(new GridBagLayout());
		initUserLabel();

		initDisableButton();
		disableUser();
	}

	private void initDisableButton() {
		GridBagConstraints c = new GridBagConstraints();
		// TODO: insert better icons
		clearButton = new ActionButton("Benutzer deaktivieren",
				"img/delete32x32h.png", "img/delete32x32.png");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.clearUser();
				clearButton.setEnabled(false);
			}
		});

		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.0;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;

		add(clearButton, c);
	}

	private void initUserLabel() {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;

		activeUserLabel = new JLabel(DEFAULT_ACTIVE_USER_TEXT);
		activeUserLabel.setForeground(Color.red);
		activeUserLabel.setIcon(new ImageIcon(USER_IMAGE_PATH));
		activeUserLabel.setBorder(new EmptyBorder(PANEL_INSETS));

		add(activeUserLabel, c);
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
		activeUserLabel.setText("<html>" + ACTIVE_USER_COLOR
				+ "Aktiver Benutzer: " + customer.getSurname() + " "
				+ customer.getName() + getOptionalLockDetail());
		clearButton.setEnabled(true);
	}

	private String getOptionalLockDetail() {
		if (controller.library.isCustomerLocked(model.getCustomer())) {
			int mahnungen = controller.library.getCustomerMahnungen(
					model.getCustomer()).size();
			return " (</font><font color=red>gesperrt, " + mahnungen
					+ (mahnungen == 1 ? " Mahnung" : " Mahnungen")
					+ ACTIVE_USER_COLOR + ")";
		}
		return "";
	}

	private void disableUser() {
		activeUserLabel.setText("<html>" + DISABLED_USER_COLOR
				+ DEFAULT_ACTIVE_USER_TEXT);
		clearButton.setEnabled(false);
		model.switchtoSearch();
	}
}
