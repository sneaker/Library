package presentation.view;

import java.awt.event.ActionEvent;
import java.util.Observable;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import presentation.control.NewSearchAction;
import presentation.control.UserCreateActionListener;
import presentation.control.UserReturnLoanAction;
import presentation.control.UserShowLoanBookAction;
import presentation.model.ModelController;

public class ActionUserPanel extends AbstractActionPanel {

	private static final long serialVersionUID = 1L;

	public ActionUserPanel(ModelController controller) {
		super(controller);
		controller.activeuser_model.addObserver(this);
		controller.usertab_model.addObserver(this);
		controller.booktab_model.addObserver(this);
	}

	protected void initActionButtons() {
		initEditOkButton();
		initEditCancelButton();
		initNewSearchButton();
		initReturnButton();
		initShowBookButton();
		initEditButton();
		initAddUserButton();
	}

	private void initNewSearchButton() {
		buttons.put("search", new ActionButton("Benutzer suchen",
				"search32x32h.png", "search32x32.png"));
		buttons.get("search").setMnemonic('s');
		buttons.get("search").addActionListener(new NewSearchAction(controller));
	}
	
	private void initReturnButton() {
		buttons.put("return", new ActionButton("Buch zurückgeben",
				"return32x32h.png", "return32x32.png"));
		buttons.get("return").setVisible(false);
		buttons.get("return").setMnemonic('z');
		buttons.get("return").addActionListener(new UserReturnLoanAction(controller));
	}

	private void initShowBookButton() {
		buttons.put("showbook", new ActionButton("Buchdetails anzeigen",
				"bookdetails32x32h.png", "bookdetails32x32.png"));
		buttons.get("showbook").setVisible(false);
		buttons.get("showbook").setMnemonic('a');
		buttons.get("showbook").addActionListener(new UserShowLoanBookAction(controller));
	}
	
	private void initEditButton() {
		buttons.put("edituser", new ActionButton("Personalien editieren",
				"edit32x32h.png", "edit32x32.png"));
		buttons.get("edituser").setVisible(false);
		buttons.get("edituser").setMnemonic('e');
		buttons.get("edituser").addActionListener(new CustomerEditAction(controller));
	}
	
	private void initEditOkButton() {
		buttons.put("edituserok", new ActionButton("Editieren abschliessen",
				"editdone32x32h.png", "editdone32x32.png"));
		buttons.get("edituserok").setVisible(false);
		buttons.get("edituserok").setMnemonic('t');
		AbstractAction action = new CustomerEditCancelAction(controller);
		addSpecialKeyForEditOk(action);
		buttons.get("edituserok").addActionListener(action);
	}

	private void addSpecialKeyForEditOk(AbstractAction action) {
		InputMap inputMap = buttons.get("edituserok")
			.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(KeyStroke.getKeyStroke("ENTER"), "okay");
		inputMap.put(KeyStroke.getKeyStroke("control S"), "okay");
		ActionMap actionMap = buttons.get("edituserok").getActionMap();
		buttons.get("edituserok").setActionMap(actionMap);
		actionMap.put("okay", action);
	}

	private void initEditCancelButton() {
		buttons.put("editusercancel", new ActionButton("Editieren rückgängig",
				"editrevert32x32h.png", "editrevert32x32.png"));
		buttons.get("editusercancel").setVisible(false);
		buttons.get("editusercancel").setMnemonic('g');
		AbstractAction action = new AbstractAction() {
			private static final long serialVersionUID = 2687724652972916779L;
			public void actionPerformed(ActionEvent e) {
				controller.action_model.editUserSettingsCancel();
			}
		};
		addSpecialKeyForEditCancel(action);
		buttons.get("editusercancel").addActionListener(action);
	}
	
	private void addSpecialKeyForEditCancel(AbstractAction action) {
		InputMap inputMap = buttons.get("editusercancel")
			.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "cancel");
		ActionMap actionMap = buttons.get("editusercancel").getActionMap();
		buttons.get("editusercancel").setActionMap(actionMap);
		actionMap.put("cancel", action);
	}

	private void initAddUserButton() {
		buttons.put("adduser", new ActionButton("Benutzer erfassen",
				"newcustomer32x32h.png", "newcustomer32x32.png"));
		buttons.get("adduser").setMnemonic('n');
		buttons.get("adduser").addActionListener(new UserCreateActionListener(controller));
	}

	public void update(Observable o, Object arg) {
		boolean active = controller.isCustomerActive();
		buttons.get("return").setVisible(active && controller.usertab_model.isLoanSelected());
		buttons.get("showbook").setVisible(active && controller.usertab_model.isLoanSelected());
		buttons.get("edituser").setVisible(active && !controller.usertab_model.isEditing());
		buttons.get("edituserok").setVisible(active && controller.usertab_model.isEditing() && !controller.usertab_model.isError());
		buttons.get("editusercancel").setVisible(active && controller.usertab_model.isEditing());
	}
}
