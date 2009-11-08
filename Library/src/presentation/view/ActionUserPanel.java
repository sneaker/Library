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
		initClearUserButton();
	}

	private void initAddUserButton() {
		// TODO: insert images
		buttons.put("adduser", new ActionButton("Neuen Benutzer Erfassen",
				"", ""));
		buttons.get("adduser").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.createUser();
			}
		});
	}

	private void initEditButton() {
		buttons.put("edituser", new ActionButton("Personalien Editieren",
				"img/edit32x32h.png", "img/edit32x32.png"));
		buttons.get("edituser").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.editUserSettings();
			}
		});
	}

	private void initNewSearchButton() {
		// TODO: insert images
		buttons.put("newsearch", new ActionButton("Neue Recherche",
				"img/search32x32h.png", "img/search32x32.png"));
		buttons.get("newsearch").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.changetoSearch();
			}
		});
	}

	private void initClearUserButton() {
		buttons.put("clearuser", new ActionButton("Benutzer deaktivieren", "",
				""));
		buttons.get("clearuser").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.clearuser();
			}
		});
	}

	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
	}
}
