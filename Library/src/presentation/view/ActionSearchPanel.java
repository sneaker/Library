package presentation.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import presentation.model.ModelController;

public class ActionSearchPanel extends AbstractActionPanel {

	private static final long serialVersionUID = 1L;

	public ActionSearchPanel(ModelController controller) {
		super(controller);
	}

	protected void initActionButtons() {
		initSearchButton();
		initNewUserButton();
		initSpacer();
//		initShowUser();
		initShowAvailableBooks();
		initShowDefektBooks();
		initShowLentBooks();
	}
	
	private void initSpacer() {
		buttons.put("none", new ActionButton("Spezialabfragen", "", ""));
		buttons.get("none").setDummyLabel();
	}

	private void initShowAvailableBooks() {
		buttons.put("available", new ActionButton(
				"Verfügbare Bücher", "availablebooks32x32h.png", "availablebooks32x32.png"));
		buttons.get("available").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.showavailableBooks();
			}
		});
	}

	private void initShowDefektBooks() {
		buttons.put("showdefekt", new ActionButton("Defekte Bücher", "wastebooks32x32h.png",
				"wastebooks32x32.png"));
		buttons.get("showdefekt").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.showDefektBooks();
				// TODO: Test, bitte wieder entfernen [Martin]
//				controller.main_model.setActiveMessage(DialogFactory.createQuestionDialog("Dieser Dialog gehört nicht hier hin. Ist das eine Frage?"));
			}
		});
	}

	private void initSearchButton() {
		buttons.put("newsearch", new ActionButton("Neue Recherche",
				"search32x32h.png", "search32x32.png"));
		buttons.get("newsearch").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.resetSearch();
			}
		});
	}

	private void initNewUserButton() {
		buttons.put("newuser", new ActionButton("Benutzer erstellen",
				"newcustomer32x32h.png", "newcustomer32x32.png"));
		buttons.get("newuser").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.createUser();
			}
		});
	}

	private void initShowLentBooks() {
		buttons.put("showlent", new ActionButton("Ausgeliehene Bücher",
				"allloans32x32h.png", "allloans32x32.png"));
		buttons.get("showlent").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.showLentBooks();
			}
		});
	}

	public void update(Observable o, Object arg) {
		// TODO On update make the buttons enabled
	}
}
