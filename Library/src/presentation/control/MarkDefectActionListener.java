/**
 * 
 */
package presentation.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import presentation.model.ModelController;
import presentation.view.DialogFactory;
import util.TextUtils;
import domain.Book;

/**
 * When fired, pops up a dialog asking whether the customer should pay the
 * damaged book or the book should only be marked as waste. Mostly usable at the
 * BookTab.
 */
public final class MarkDefectActionListener implements ActionListener {
	private final ModelController controller;

	public MarkDefectActionListener(ModelController controller) {
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		Book activeBook = controller.booktab_model.getActiveBook();
		boolean noLoans = controller.library.getLoansPerBook(activeBook).size() == 0;
		String tmp = activeBook.getTitle().getName();
		final String book = tmp.substring(0, Math.min(tmp.length(), 20));

		JPanel dialog = null;
		if (noLoans)
			dialog = getNoFactureDialog(book);
		else
			dialog = getFactureDialog(book, activeBook);
		controller.main_model.setActiveMessage(dialog);
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
	private JPanel getNoFactureDialog(String book) {
		final String[] buttonNames = new String[] { "Ab&brechen",
				"Nur &ausmustern" };

		Action[] buttonActions = new Action[buttonNames.length];
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
		JPanel dialog = DialogFactory.createChoiceDialog(dialogText,
				buttonNames, buttonActions, buttonKeys);
		return dialog;
	}

	private JPanel getFactureDialog(final String book, Book activeBook) {
		final String customer = controller.library.getRecentLoanOf(activeBook)
				.getCustomer().getFullName();
		final String[] buttonNames = new String[] { "Ab&brechen",
				"Nur &ausmustern", "Rechnung &Drucken" };

		Action[] buttonActions = new Action[buttonNames.length];
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
		JPanel dialog = DialogFactory.createChoiceDialog(dialogText,
				buttonNames, buttonActions, buttonKeys);
		return dialog;
	}
}