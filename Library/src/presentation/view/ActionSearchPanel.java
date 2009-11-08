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
		initShowUser();
		initShowAvailableBooks();
		initShowDefektBooks();
		initShowLentBooks();
	}
	
	private void initSpacer() {
		buttons.put("none", new ActionButton("Spezialfragen", "", ""));
		buttons.get("none").setEnabled(false);
	}

	private void initShowAvailableBooks() {
		// TODO: insert images
		buttons.put("available", new ActionButton(
				"Alle verfügbaren Bücher anzeigen", "", ""));
		buttons.get("available").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.showavailableBooks();
			}
		});
	}

	private void initShowDefektBooks() {
		// TODO: insert images
		buttons.put("showdefekt", new ActionButton("Alle defekten Bücher", "",
				""));
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
		buttons.put("newuser", new ActionButton("Neuen Benutzer anlegen",
				"img/newcustomer32x32h.png", "img/newcustomer32x32.png"));
		buttons.get("newuser").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.createUser();
			}
		});
	}

	private void initShowLentBooks() {
		// TODO: insert images
		buttons.put("showlent", new ActionButton("Alle ausgeliehenen Bücher",
				"", ""));
		buttons.get("showlent").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.showLentBooks();
			}
		});
	}

	private void initShowUser() {
		// TODO: create overlay image
		buttons.put("showuser", new ActionButton("Nur Benutzer zeigen",
				"img/user32x32.png", "img/user32x32.png"));
		buttons.get("showuser").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.showUser();
			}
		});
	}

	public void update(Observable o, Object arg) {
		// TODO On update make the buttons enabled
	}
}
