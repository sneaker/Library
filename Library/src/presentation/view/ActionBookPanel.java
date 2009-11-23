package presentation.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import presentation.control.BookCreateActionListener;
import presentation.control.MarkDefectActionListener;
import presentation.model.ModelController;

public class ActionBookPanel extends AbstractActionPanel {

	private static final long serialVersionUID = 1L;

	public ActionBookPanel(ModelController controller) {
		super(controller);
		controller.booktab_model.addObserver(this);
		controller.library.addObserver(this);
	}

	protected void initActionButtons() {
		initSearchButton();
		initLendButton();
		initReturnButton();
		initEditButton();
		initEditOkButton();
		initEditCancelButton();
		initDefektButton();
		initCreateButton();
	}

	private void initDefektButton() {
		buttons.put("defekt", new ActionButton("Als defekt markieren",
				"delete32x32h.png", "delete32x32.png"));
		buttons.get("defekt").setMnemonic('m');
		buttons.get("defekt").addActionListener(new MarkDefectActionListener(controller));
	}

	private void initReturnButton() {
		buttons.put("return", new ActionButton("Buch zurückgeben",
				"return32x32h.png", "return32x32.png"));
		buttons.get("return").setMnemonic('z');
		buttons.get("return").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttons.get("search").requestFocus();
				controller.action_model.returnBook();
			}
		});
	}

	private void initLendButton() {
		buttons.put("lend", new ActionButton("Buch ausleihen",
				"add32x32h.png", "add32x32.png"));
		buttons.get("lend").setVisible(false);
		buttons.get("lend").setMnemonic('l');
		buttons.get("lend").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttons.get("lend").requestFocus();
				controller.action_model.lendBook();
			}
		});
	}

	private void initCreateButton() {
		buttons.put("create", new ActionButton("Buch erstellen",
				"newbook32x32h.png", "newbook32x32.png"));
		buttons.get("create").setMnemonic('n');
		buttons.get("create").addActionListener(new BookCreateActionListener(controller));
	}

	private void initEditButton() {
		buttons.put("edit", new ActionButton("Buchdetails editieren",
				"edit32x32h.png", "edit32x32.png"));
		buttons.get("edit").setVisible(false);
		buttons.get("edit").setMnemonic('e');
		buttons.get("edit").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.booktab_model.setEditing(true);
				controller.booktab_model.backupBookContent();
				controller.status_model.setTempStatus("Buchdetails editieren");
			}
		});
	}

	private void initEditOkButton() {
		buttons.put("editok", new ActionButton("Editieren abschliessen",
				"editdone32x32h.png", "editdone32x32.png"));
		buttons.get("editok").setVisible(false);
		buttons.get("editok").setMnemonic('t');
		buttons.get("editok").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.booktab_model.setEditing(false);
				controller.status_model.setTempStatus("Änderungen am Buchtitel wurden gesichert");
			}
		});
	}
	
	private void initEditCancelButton() {
		buttons.put("editbookcancel", new ActionButton("Editieren rückgängig",
				"editrevert32x32h.png", "editrevert32x32.png"));
		buttons.get("editbookcancel").setVisible(false);
		buttons.get("editbookcancel").setMnemonic('g');
		buttons.get("editbookcancel").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (controller.booktab_model.restoreBookContent())
					controller.status_model.setTempStatus("Änderungen am Buchtitel wurden zurückgesetzt");
				else
					controller.status_model.setTempStatus("Fehler: Änderungen am Buchtitel konnten nicht zurückgesetzt werden");
				controller.booktab_model.setEditing(false);
			}
		});
	}
	
	private void initSearchButton() {
		buttons.put("search", new ActionButton("Buch suchen",
				"search32x32h.png", "search32x32.png"));
		buttons.get("search").setMnemonic('s');
		buttons.get("search").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setSearchTabActive();
			}
		});
	}

	public void update(Observable o, Object arg) {
		buttons.get("lend").setVisible(
				controller.booktab_model.isActiveBookLendable()
						&& !controller.library
								.isCustomerLocked(controller.getActiveCustomer()));
		buttons.get("return").setVisible(
				controller.booktab_model.isActiveBookReturnable());
		buttons.get("defekt").setVisible(
				controller.booktab_model.isActiveBookNoWaste());
		buttons.get("edit").setVisible(controller.booktab_model.isBookActive() && !controller.booktab_model.isEditing());
		buttons.get("editok").setVisible(controller.booktab_model.isEditing() && !controller.booktab_model.isError());
		buttons.get("editbookcancel").setVisible(controller.booktab_model.isEditing());
	}
}
