package presentation.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
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
	private static final String NO_USER_ACTIVE_TEXT = "Kein Benutzer ausgewählt. \n\n1) Klicke auf \"Recherche\".\n2) Suche einen Benutzer.\n3) Klicke auf den gewünschten Benutzer.\n\nDie Persönlichen Daten des Benutzers und dessen Ausleihen werden dann hier angezeigt.";
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
		controller.activeuser_model.addObserver(this);
		controller.usertab_model.addObserver(this);
		initGUI();
	}

	private void initGUI() {
		setLayout(new GridBagLayout());
		setBorder(new TitledBorder("Benutzerinformationen"));
		initTitle();
		initAdress();
		initPlace();
		initStatus();
	}

	private void initTitle() {
		titleText = new JTextArea();
		titleTextEditable = new DetailTextField();
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
		addressText.setEditable(false);
		addAddressValidator();
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

	private void addAddressValidator() {
		addressText.addKeyListener(new KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent e) {
				String newAddress = addressText.getText();
				boolean ok = newAddress.length() != 0;
				addressText.setError(!ok);
				updateValidation();
				controller.activeuser_model.getCustomer().setAdress(newAddress,
						controller.activeuser_model.getCustomer().getZip(),
						controller.activeuser_model.getCustomer().getCity());
				controller.activeuser_model.fireDataChanged();
			}
		});
	}

	protected void updateValidation() {
		boolean hasErrors = titleTextEditable.isError()
				|| addressText.isError() || placeText.isError();
		controller.usertab_model.setError(hasErrors);
	}

	private void initPlace() {
		placeText = new DetailTextField();
		placeText.setVisible(false);
		addPlaceValidator();
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

	private void addPlaceValidator() {
		// TODO: Fertig machen [Martin]
		placeText.addKeyListener(new KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent e) {
				// Dear god of Holy Regex - help me
				super.keyTyped(e);
				String split[] = placeText.getText().split(" ");
				if (split.length < 2) {
					placeText.setError(true);
					return;
				}
				boolean okZip = false;
				boolean okCity = false;
				String zipString = split[0];
				int newZip = 0;
				try {
					newZip = Integer.parseInt(zipString);
				} catch (NumberFormatException ex) {
				}
				if (newZip != 0) {
					controller.activeuser_model.getCustomer()
							.setAdress(
									controller.activeuser_model.getCustomer()
											.getStreet(),
									newZip,
									controller.activeuser_model.getCustomer()
											.getCity());
					okZip = true;
				}

				String newCity = split[1];
				if (newCity.length() > 0) {
					controller.activeuser_model.getCustomer().setAdress(
							controller.activeuser_model.getCustomer()
									.getStreet(),
							controller.activeuser_model.getCustomer().getZip(),
							newCity);
					okCity = true;
				}
				controller.usertab_model.setError(!okZip || !okCity);
				placeText.setError(!okZip || !okCity);
			}
		});
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

	/**
	 * Precondition: controller.activeuser_mode.getCustomer() != null
	 */
	private void updateDetails() {
		Customer c = controller.activeuser_model.getCustomer();
		addressText.setText(c.getStreet());
		placeText.setText(c.getZip() + " " + c.getCity());
		statusText.setText(getCustomerStatus(c));
	}

	private String getCustomerStatus(Customer c) {
		String result = "";
		boolean userActive = controller.library.isCustomerLocked(c);
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
		setTitleTextEditable(editable);
		addressText.setEditable(editable);
		placeText.setEditable(editable);
		getParent().validate();
	}

	private void setTitleTextEditable(boolean editable) {
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
			addTitleValidator();
			titleTextEditable.requestFocus();
		} else {
			if (!isAncestorOf(titleTextEditable))
				return;
			remove(titleTextEditable);
			add(titleText, getTitleGridBagConstraints());
		}
	}

	private void addTitleValidator() {
		titleTextEditable.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				String newName = titleTextEditable.getText();
				String splitName[] = newName.split(", ");
				if (splitName.length < 2)
					splitName = newName.split(",");
				if (splitName.length < 2)
					splitName = newName.split(" ");
				boolean ok = splitName.length == 2;
				titleTextEditable.setError(!ok);
				updateValidation();
				if (splitName.length < 2)
					return;
				controller.activeuser_model.getCustomer().setName(splitName[0]);
				controller.activeuser_model.getCustomer().setSurname(
						splitName[1]);
				controller.activeuser_model.fireDataChanged();
			}
		});
	}
	
	private void updateTitle(boolean isUserActive) {
		if (isUserActive)
			titleText.setText(controller.activeuser_model.getCustomer().getFullName());
		else
			titleText.setText(NO_USER_ACTIVE_TEXT);
	}

	public void update(Observable o, Object arg) {
		boolean isUserActive = (controller.activeuser_model.getCustomer() != null);
		setEditable(isUserActive && controller.usertab_model.isEditing());
		updateTitle(isUserActive);
		setDetailsVisibility(isUserActive);

		if (isUserActive)
			updateDetails();
	}
}
