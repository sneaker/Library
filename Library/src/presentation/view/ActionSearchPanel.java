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
	}
	
	private void initSearchButton() {
		//TODO: insert images
		buttons.put("newsearch", new ActionButton("Neue Recherche", "", ""));
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
