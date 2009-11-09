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
		buttons.get("none").setEnabled(false);
	}

	private void initShowAvailableBooks() {
		buttons.put("available", new ActionButton(
				"Verfügbare Bücher", "img/availablebooks32x32h.png", "img/availablebooks32x32.png"));
		buttons.get("available").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.showavailableBooks();
			}
		});
	}

	private void initShowDefektBooks() {
		buttons.put("showdefekt", new ActionButton("Defekte Bücher", "img/wastebooks32x32h.png",
				"img/wastebooks32x32.png"));
		buttons.get("showdefekt").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.showDefektBooks();
			}
		});
	}

	private void initSearchButton() {
		buttons.put("newsearch", new ActionButton("Neue Recherche",
				"img/search32x32h.png", "img/search32x32.png"));
		buttons.get("newsearch").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.resetSearch();
			}
		});
	}

	private void initNewUserButton() {
		buttons.put("newuser", new ActionButton("Benutzer erstellen",
				"img/newcustomer32x32h.png", "img/newcustomer32x32.png"));
		buttons.get("newuser").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.createUser();
			}
		});
	}

	private void initShowLentBooks() {
		buttons.put("showlent", new ActionButton("Ausgeliehene Bücher",
				"img/allloans32x32h.png", "img/allloans32x32.png"));
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
