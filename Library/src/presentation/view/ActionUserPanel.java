package presentation.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import presentation.model.ModelController;

public class ActionUserPanel extends AbstractActionPanel {

	private static final long serialVersionUID = 1L;

	public ActionUserPanel(ModelController controller) {
		super(controller);
		controller.booktab_model.addObserver(this);
	}

	protected void initActionButtons() {
		initNewSearchButton();
		initReturnButton();
		initShowBookButton();
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
	
	private void initReturnButton() {
		buttons.put("return", new ActionButton("Buch zurückgeben",
				"img/return32x32h.png", "img/return32x32.png"));
		buttons.get("return").setVisible(false);
		buttons.get("return").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.library.returnBook(controller.booktab_model.getActiveBook());
				controller.usertab_model.fireDataChanged();
			}
		});
	}

	private void initShowBookButton() {
		buttons.put("showbook", new ActionButton("Buchdetails anzeigen",
				"img/bookdetails32x32h.png", "img/bookdetails32x32.png"));
		buttons.get("showbook").setVisible(false);
		buttons.get("showbook").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.tabbed_model.setBookTabActive();
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
				controller.usertab_model.setEditing(true);
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
		buttons.put("clearuser", new ActionButton("Benutzer deaktivieren", "img/disablecustomer32x32h.png",
				"img/disablecustomer32x32.png"));
		buttons.get("clearuser").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.clearuser();
			}
		});
	}

	public void update(Observable o, Object arg) {
		boolean active = controller.activeuser_model.isCustomerActive();
		buttons.get("return").setVisible(active && controller.usertab_model.isLoanSelected());
		buttons.get("showbook").setVisible(active && controller.usertab_model.isLoanSelected());
		buttons.get("edituser").setVisible(active);
	}
}
