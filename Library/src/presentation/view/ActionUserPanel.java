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
		initNewSearchButton();
		initEditButton();
		initClearUserButton();
		initAddUserButton();
	}

	private void initNewSearchButton() {
		buttons.put("search", new ActionButton("Benutzer suchen",
				"img/search32x32h.png", "img/search32x32.png"));
		buttons.get("search").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.changetoSearch();
			}
		});
	}

	private void initEditButton() {
		buttons.put("edituser", new ActionButton("Personalien editieren",
				"img/edit32x32h.png", "img/edit32x32.png"));
		buttons.get("edituser").setVisible(false);
		buttons.get("edituser").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.editUserSettings();
			}
		});
	}

	private void initAddUserButton() {
		buttons.put("adduser", new ActionButton("Benutzer erfassen",
				"img/newcustomer32x32h.png", "img/newcustomer32x32.png"));
		buttons.get("adduser").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.createUser();
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
		boolean active = controller.activeuser_model.isCustomerActive();
		buttons.get("edituser").setVisible(active);
		buttons.get("search").requestFocus();
	}
}
