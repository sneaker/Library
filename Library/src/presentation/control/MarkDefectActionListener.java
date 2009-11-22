/**
 * 
 */
package presentation.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.KeyStroke;

import presentation.model.ModelController;
import util.TextUtils;
import domain.Book;
import domain.Message;
import domain.Book.Condition;

/**
 * When fired, pops up a dialog asking whether the customer should pay the
 * damaged book or the book should only be marked as waste. Mostly usable at the
 * BookTab.
 */
public final class MarkDefectActionListener implements ActionListener {
	private final ModelController controller;
	private Condition oldCondition = null;

	public MarkDefectActionListener(ModelController controller) {
		this.controller = controller;
	}

	public MarkDefectActionListener(ModelController controller,
			Condition oldCondition) {
				this.controller = controller;
				this.oldCondition = oldCondition;
	}

	public void actionPerformed(ActionEvent e) {
		Book activeBook = controller.booktab_model.getActiveBook();
		boolean noLoans = controller.library.getLoansPerBook(activeBook).size() == 0;
		String tmp = activeBook.getTitle().getName();
		final String book = tmp.substring(0, Math.min(tmp.length(), 20));

		Message msg = null;
		if (controller.library.isBookLent(activeBook)) {
			System.out.println("hit");
			msg = getLentBookWarningDiaglog(book);
			controller.main_model.addActiveMessage(msg);
		}
		
		if (noLoans)
			msg = getNoFactureDialog(book);
		else
			msg = getFactureDialog(book, activeBook);
		
		controller.main_model.addActiveMessage(msg);
	}

	private Message getLentBookWarningDiaglog(String book) {
		System.out.println("here");
		final String[] buttonNames = new String[] { "&Ok" };

		Action[] buttonActions = new Action[buttonNames.length];
		buttonActions[0] = new MarkWasteAbstractAction(controller, book);
		
		KeyStroke[] buttonKeys = new KeyStroke[buttonNames.length];
		buttonKeys[0] = KeyStroke.getKeyStroke("ENTER");

		String dialogText = TextUtils
			.markupText(TextUtils
					.format("Achtung, Bitte beachten sie dass das Buch \"" + book + "\" noch ausgeliehen ist.",16));
		Message msg = new Message(dialogText, buttonNames, buttonActions, buttonKeys);
		return msg;
	}

	/**
	 * In case the book has never been lent, the dialog should not say anything
	 * about printing factures for a specific customer. Yust ask whether the
	 * user wants do mark this book as defect or not.
	 * 
	 * @param book
	 *            The book bo be marked as defect in case the user wants to.
	 * @return 
	 */
	private Message getNoFactureDialog(String book) {
		final String[] buttonNames = new String[] { "Ab&brechen",
				"Nur &ausmustern" };

		Action[] buttonActions = new Action[buttonNames.length];
		if (oldCondition != null)
			buttonActions[0] = new DisableGlassPaneListener(controller, oldCondition);
		else
			buttonActions[0] = new DisableGlassPaneListener(controller);
		buttonActions[1] = new MarkWasteAbstractAction(controller, book);

		KeyStroke[] buttonKeys = new KeyStroke[buttonNames.length];
		buttonKeys[0] = KeyStroke.getKeyStroke("ESCAPE");
		buttonKeys[1] = KeyStroke.getKeyStroke("ENTER");

		String dialogText = TextUtils
				.markupText(TextUtils
						.format(
								"Das Buch \""
										+ book
										+ "\" wird als defekt markiert und ausgemustert. Es handelt sich um ein neu eingetragenes Buch. Falls Sie es nicht ausmustern wollen, wählen Sie \"abbrechen\".",
								16));
		Message msg = new Message(dialogText, buttonNames, buttonActions, buttonKeys);
		return msg;
	}

	private Message getFactureDialog(final String book, Book activeBook) {
		final String customer = controller.library.getRecentLoanOf(activeBook)
				.getCustomer().getFullName();
		final String[] buttonNames = new String[] { "Ab&brechen",
				"Nur &ausmustern", "Rechnung &Drucken" };

		Action[] buttonActions = new Action[buttonNames.length];
		if (oldCondition != null)
			buttonActions[0] = new DisableGlassPaneListener(controller, oldCondition);
		else
			buttonActions[0] = new DisableGlassPaneListener(controller);
		buttonActions[1] = new MarkWasteAbstractAction(controller, book);
		buttonActions[2] = new MarkWastePrintFactureAbstractAction(controller,
				customer);

		KeyStroke[] buttonKeys = new KeyStroke[buttonNames.length];
		buttonKeys[0] = KeyStroke.getKeyStroke("ESCAPE");
		buttonKeys[2] = KeyStroke.getKeyStroke("ENTER");

		String dialogText = TextUtils
				.markupText(TextUtils
						.format(
								"Das Buch \""
										+ book
										+ "\" wird ausgemustert. Für letzte(n) Ausleiher(in) "
										+ TextUtils.format("\"" + customer + "\"", 20)
										+ " wird eine Rechnung gedruckt. Legen Sie ein Rechnungspapier in den Drucker ein und klicken Sie auf \"Rechnung drucken\".",
								16));
		Message msg = new Message (dialogText, buttonNames, buttonActions, buttonKeys);
		return msg;
	}
}