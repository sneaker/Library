package presentation.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import presentation.model.ModelController;

public class ActionUserPanel extends AbstractActionPanel {

	private static final long serialVersionUID = 1L;

	public ActionUserPanel(ModelController controller) {
		super(controller);
	}

	protected void initActionButtons() {
		initAddUserButton();
		initEditButton();
		initNewSearchButton();
	}

	private void initAddUserButton() {
		// TODO: insert images
		buttons.put("adduser", new ActionButton("Neuen Benutzer Erfassen", "",
				""));
		buttons.get("adduser").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.createUser();
			}
		});
	}

	private void initEditButton() {
		// TODO: insert images
		buttons.put("edituser", new ActionButton("Personalien Editieren", "",
				""));
		buttons.get("edituser").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.editUserSettings();
			}
		});
	}

	private void initNewSearchButton() {
		// TODO: insert images
		buttons.put("newsearch", new ActionButton("Neue Recherche", "", ""));
		buttons.get("newsearch").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.changetoSearch();
			}
		});
	}

	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
	}
}
