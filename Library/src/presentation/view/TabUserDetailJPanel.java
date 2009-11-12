package presentation.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import presentation.model.ModelController;
import domain.Customer;

public class TabUserDetailJPanel extends JPanel implements Observer {
	private static final long serialVersionUID = 8165031301707363641L;
	private final Font DETAIL_LABEL_FONT = new Font("SansSerif", Font.BOLD, 16);
	private final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 18);
	private static final String NO_USER_ACTIVE_TEXT = "Kein Benutzer ausgewählt. \n\nBitte unter Recherche einen Benutzer suchen und auswählen, um seine Details hier anzuzeigen.";
	private JTextArea titleText;
	private DetailTextField addressText;
	private ModelController controller;
	private DetailTextField placeText;
	private JLabel addressLabel;
	private JLabel statusLabel;
	private DetailTextField statusText;
	private DetailTextField titleTextEditable;

	public TabUserDetailJPanel(ModelController controller) {
		this.controller = controller;
		setLayout(new GridBagLayout());
		setBorder(new TitledBorder("Benutzerinformationen"));
		initTitle();
		initAdress();
		initPlace();
		initStatus();
	}

	private void initTitle() {
		titleText = new JTextArea();
		titleText.setText(NO_USER_ACTIVE_TEXT);
		titleText.setBackground(this.getBackground());
		titleText.setEditable(false);
		titleText.setFont(TITLE_FONT);
		titleText.setLineWrap(true);
		titleText.addMouseListener(new CopyPasteTextFieldListener(
				"Benutzernamen kopieren", titleText, controller));
		add(titleText, getTitleGridBagConstraints());
	}

	private GridBagConstraints getTitleGridBagConstraints() {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1;
		c.weighty = 0.00001;
		return c;
	}

	private void initAdress() {
		addressLabel = new JLabel("Adresse: ");
		addressLabel.setVisible(false);
		addressLabel.setFont(DETAIL_LABEL_FONT);
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_END;
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.00001;
		c.weighty = 0.00001;
		add(addressLabel, c);

		addressText = new DetailTextField();
		addressText.setVisible(false);
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.00001;
		add(addressText, c);
	}

	private void initPlace() {
		placeText = new DetailTextField();
		placeText.setVisible(false);
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.00001;
		add(placeText, c);
	}

	private void initStatus() {
		statusLabel = new JLabel("Status: ");
		statusLabel.setVisible(false);
		statusLabel.setFont(DETAIL_LABEL_FONT);
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_END;
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.00001;
		c.weighty = 1.0;
		add(statusLabel, c);

		statusText = new DetailTextField();
		statusText.setVisible(false);
		statusText.setDisplayOnly(true);
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(statusText, c);
	}

	private void setDetailsVisibility(boolean isUserActive) {
		addressLabel.setVisible(isUserActive);
		addressText.setVisible(isUserActive);
		placeText.setVisible(isUserActive);
		statusLabel.setVisible(isUserActive);
		statusText.setVisible(isUserActive);
	}

	private void updateDetails() {
		Customer c = controller.activeuser_model.getCustomer();
		addressText.setText(c.getStreet());
		placeText.setText(c.getZip() + " " + c.getCity());
		statusText.setText(getCustomerStatus(c));
	}

	private String getCustomerStatus(Customer c) {
		String result = "";
		boolean userActive = controller.library.getCustomerStatus(c);
		if (userActive)
			result += (userActive ? "Aktiv" : "Gesperrt");
		result += " (";
		result += getLoanCountText(c) + ", ";
		result += getMahnungCountText(c);
		result += ")";
		return result;
	}

	private String getLoanCountText(Customer c) {
		int count = controller.library.getCustomerActiveLoans(c).size();
		return (count == 0 ? "keine Ausleihen" : (count == 1 ? "eine Ausleihe"
				: count + " Ausleihen"));
	}

	private String getMahnungCountText(Customer c) {
		int count = controller.library.getCustomerMahnungen(c).size();
		return (count == 0 ? "keine Mahnungen" : (count == 1 ? "eine Mahnung"
				: count + " Mahnungen"));
	}

	public void setEditable(boolean editable) {
		if (editable) {
			if (!isAncestorOf(titleText))
				return;
			remove(titleText);
			titleTextEditable = new DetailTextField();
			add(titleTextEditable, getTitleGridBagConstraints());
			titleTextEditable.setText(controller.activeuser_model.getCustomer()
					.getFullName());
			titleTextEditable.setEditable(true);
			titleTextEditable.setFont(TITLE_FONT);
			titleTextEditable.addCaretListener(new CaretListener() {
				public void caretUpdate(CaretEvent e) {
					String newName = titleTextEditable.getText();
					String tmp[] = newName.split(", ");
					if (tmp.length < 2) {
						titleTextEditable.setError(true);
						controller.usertab_model.setError(true);
						return;
					} else {
						controller.usertab_model.setError(false);
						titleTextEditable.setError(false);
					}
					controller.activeuser_model.getCustomer().setName(tmp[0]);
					controller.activeuser_model.getCustomer().setSurname(tmp[1]);
					controller.activeuser_model.fireDataChanged();
					
				}
			});
			titleTextEditable.requestFocus();
		} else {
			if (!isAncestorOf(titleTextEditable))
				return;
			remove(titleTextEditable);
			add(titleText, getTitleGridBagConstraints());
		}
		addressText.setEditable(editable);
		placeText.setEditable(editable);
		getParent().validate();
	}

	public void update(Observable o, Object arg) {
		boolean isUserActive = (controller.activeuser_model.getCustomer() != null);
		setEditable(isUserActive && controller.usertab_model.isEditing());
		titleText.setText((isUserActive ? controller.activeuser_model
				.getCustomer().getFullName() : NO_USER_ACTIVE_TEXT));
		setDetailsVisibility(isUserActive);

		if (isUserActive)
			updateDetails();
	}
}