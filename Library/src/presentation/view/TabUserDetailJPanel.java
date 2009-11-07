package presentation.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import presentation.model.ModelController;
import domain.Customer;

public class TabUserDetailJPanel extends JPanel implements Observer {
	private static final long serialVersionUID = 8165031301707363641L;
	private final Font DETAIL_LABEL_FONT = new Font("SansSerif",
			Font.BOLD, 16);
	private final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 18);
	private static final String NO_USER_ACTIVE_TEXT = "Kein Benutzer ausgewählt. \n\nBitte unter Recherche einen Benutzer suchen und auswählen, um seine Details hier anzuzeigen.";
	private JTextArea titleText;
	private DetailTextField addressText;
	private ModelController controller;
	private DetailTextField placeText;
	private JLabel addressLabel;

	public TabUserDetailJPanel(ModelController controller) {
		this.controller = controller;
		setLayout(new GridBagLayout());
		initTitle();
		initAdress();
		initPlace();
	}

	private void initTitle() {
		titleText = new JTextArea();
		titleText.setText(NO_USER_ACTIVE_TEXT);
		titleText.setBackground(this.getBackground());
		titleText.setLineWrap(true);
		titleText.setFont(TITLE_FONT);
		titleText.addMouseListener(new CopyPasteTextFieldListener(
				"Benutzernamen kopieren", titleText, controller));
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.weightx = 1.0;
		c.weighty = 0.00001;
		add(titleText, c);
	}

	private void initAdress() {
		addressLabel = new JLabel("Adresse: ");
		addressLabel.setVisible(false);
		addressLabel.setFont(DETAIL_LABEL_FONT);
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.weightx = 0.1;
		c.weighty = 0.1;
		add(addressLabel, c);
		
		addressText = new DetailTextField();
		addressText.setVisible(false);
		addressText.setFont(DETAIL_LABEL_FONT);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.weightx = 0.1;
		c.weighty = 0.1;
		add(addressText, c);
	}

	private void initPlace() {
		placeText = new DetailTextField();
		placeText.setVisible(false);
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 2;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(placeText, c);
	}
	
	public void update(Observable o, Object arg) {
		boolean isUserActive = (controller.usertab_model
				.getActiveCustomer() != null);
		titleText.setText((isUserActive ? controller.usertab_model.getActiveCustomer().getFullName() : NO_USER_ACTIVE_TEXT));
		addressText.setVisible(isUserActive);
		placeText.setVisible(isUserActive);
		
		if (!isUserActive)
			return;
		
		Customer c = controller.usertab_model.getActiveCustomer();
		addressText.setText(c.getStreet());
		placeText.setText(c.getZip() + " " + c.getCity());
	}
}