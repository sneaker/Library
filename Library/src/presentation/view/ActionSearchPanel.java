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
		initShowAvailableBooks();
	}
	
	private void initShowAvailableBooks() {
		//TODO: insert images
		buttons.put("available", new ActionButton("Alle verfügbaren Bücher anzeigen", "", ""));
		buttons.get("available").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.showavailableBooks();
			}
		});
	}

	private void initSearchButton() {
		buttons.put("newsearch", new ActionButton("Neue Recherche", "img/search32x32h.png", "img/search32x32.png"));
		buttons.get("newsearch").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.changetoSearch();
			}
		});
	}

	private void initNewUserButton() {
		//TODO: insert images
		buttons.put("newuser", new ActionButton("Neuer Benutzer Anlegen", "", ""));
		buttons.get("newuser").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.createNewUser();
			}
		});
	}

	public void update(Observable o, Object arg) {
		// TODO On update make the buttons enabled
	}
}
