package presentation.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import presentation.model.ModelController;
import presentation.model.TabBookModel;
import domain.Book;
import domain.Loan;

/**
 * Displays formatted loan details of a book such as the customer who has lent
 * the book or the duration of the last loan.
 */
public class TabBookLoanJPanel extends JPanel implements Observer {
	private static final String NEWLINE = "<br />";
	private static final long serialVersionUID = -415819277477368555L;
	private final Font DETAIL_LABEL_FONT = new Font("SansSerif", Font.BOLD, 16);
	private TabBookModel bmodel;
	private JLabel lastLoan;
	private final ModelController controller;

	public TabBookLoanJPanel(ModelController controller) {
		this.controller = controller;
		bmodel = controller.booktab_model;
		initGUI();
	}

	private void initGUI() {
		setVisible(false);
		setBorder(new TitledBorder("Ausleihdetails"));
		setLayout(new BorderLayout());
		lastLoan = new JLabel();
		lastLoan.setFont(DETAIL_LABEL_FONT);
		add(lastLoan);
	}

	public void update(Observable o, Object arg) {
		boolean isBookActive = bmodel.getActiveBook() != null;
		setVisible(isBookActive
				&& bmodel.getActiveBook().getCondition() != Book.Condition.WASTE);

		if (isBookActive)
			updateLoanStatusText();
	}

	private void updateLoanStatusText() {
		Book book = bmodel.getActiveBook();
		List<Loan> loanList = controller.library.getLoansPerBook(book);
		Loan recentLoan = controller.library.getRecentLoanOf(book);

		String loanText = "<html>";
		loanText += getLoanStatusText(loanList, recentLoan);

		lastLoan.setText(loanText);
	}

	private String getLoanStatusText(List<Loan> loanList, Loan tmpLoan) {
		String result = boldText("Ausleihezustand: ");
		if (tmpLoan == null || tmpLoan.getCustomer() == null) {
			return result + "Verfügbar" + NEWLINE;
		}
		result += (tmpLoan.isLent() ? "Ausgeliehen" : "Verfügbar") + NEWLINE;
		result += boldText("Letzter Ausleiher: ")
				+ tmpLoan.getCustomer().getFullName() + NEWLINE;

		if (tmpLoan.isLent()) {
			result += getAusleihedauerText(tmpLoan);
			result += getOverdueText(tmpLoan);
			result += NEWLINE;
		} else {
			result += getLoanDurationText(tmpLoan);
		}
		result += getLoanCountText(loanList);

		return result;
	}

	private String getLoanCountText(List<Loan> loanList) {
		return "Bisher total " + loanList.size() + " Mal ausgeliehen" + NEWLINE;
	}

	private String getLoanDurationText(Loan tmpLoan) {
		return boldText("Letzte Ausleihedauer: ")
				+ tmpLoan.getDaysOfLoanDuration()
				+ (tmpLoan.getDaysOfLoanDuration() == 1 ? " Tag" : " Tage")
				+ NEWLINE;
	}
	
	private String getAusleihedauerText(Loan tmpLoan) {
		int loanDuration = tmpLoan.getDaysSincePickup();
		return boldText("Ausleihedauer: ")
		+ (loanDuration == 0 ? "heute ausgeliehen" : loanDuration) + "";
	}

	private String getOverdueText(Loan tmpLoan) {
		int overdueDays = tmpLoan.getDaysSincePickup()
				- controller.library.getMaxLendDays();
		if (overdueDays <= 0)
			return "";
		String howLong = (overdueDays == 1 ? "gestern" : overdueDays + " Tagen");
		return " (" + emphasizeText("überfällig") + " seit " + howLong + ")";

	}

	private String emphasizeText(String string) {
		return "<font color=red>" + string + "</font>";
	}

	private String boldText(String text) {
		return "<b>" + text + "</b>";
	}
}