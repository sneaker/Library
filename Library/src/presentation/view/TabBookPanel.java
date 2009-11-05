package presentation.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;

import presentation.model.ModelController;
import presentation.model.TabBookModel;
import domain.Book;
import domain.Loan;

public class TabBookPanel extends JPanel implements Observer {

	private static final Font DETAIL_LABEL_FONT = new Font("SansSerif",
			Font.BOLD, 16);
	private static final String NO_BOOK_ACTIVE_TEXT = "Kein Buch ausgewählt, bitte unter Recherche ein Buch suchen und auswählen, um seine Details hier anzuzeigen.";
	private static final long serialVersionUID = 1L;
	private TabBookModel model;
	private JPanel detailPanel;
	private JPanel contentPanel;
	private ModelController controller;
	private ActionBookPanel bookpanel;
	private JPanel loanPanel;
	private JTextArea titleText;
	private JLabel authorLabel;
	private JLabel publishLabel;
	private JTextField authorText;
	private JTextField publishText;
	private JLabel filler;
	private JLabel lastLoan;
	private JLabel conditionLabel;
	private DetailTextField conditionText;

	public TabBookPanel(ModelController controller) {
		setLayout(new BorderLayout());
		this.controller = controller;

		model = controller.booktab_model;
		model.addObserver(this);

		this.controller.booktab_model = model;

		initContentPanel();
		bookpanel = new ActionBookPanel(controller);
		add(bookpanel, BorderLayout.EAST);
	}

	private void initContentPanel() {
		contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());

		initDetailPanel();
		initLoanPanel();

		contentPanel.add(detailPanel, BorderLayout.CENTER);
		contentPanel.add(loanPanel, BorderLayout.SOUTH);

		add(contentPanel, BorderLayout.CENTER);
	}

	private void initLoanPanel() {
		loanPanel = new JPanel();
		loanPanel.setVisible(false);
		loanPanel.setBorder(new TitledBorder("Ausleihdetails"));
		loanPanel.setLayout(new BorderLayout());
		lastLoan = new JLabel();
		lastLoan.setFont(new Font("SansSerif", Font.PLAIN, 16));
		loanPanel.add(lastLoan);
	}

	private void initDetailPanel() {
		GridBagConstraints c = new GridBagConstraints();
		detailPanel = new JPanel();
		detailPanel.setLayout(new GridBagLayout());
		detailPanel.setBorder(new TitledBorder("Katalogdaten"));
		titleText = new JTextArea(1, 20);
		titleText.setText(NO_BOOK_ACTIVE_TEXT);
		titleText.setEditable(false);
		titleText.setBackground(this.getBackground());
		titleText.setLineWrap(true);
		titleText.setFont(new Font("SansSerif", Font.BOLD, 18));
		titleText.addMouseListener(new ClipboardListener("Copy title",
				titleText));
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		detailPanel.add(titleText, c);

		authorLabel = new JLabel("Autor: ");
		authorLabel.setVisible(false);
		authorLabel.setFont(DETAIL_LABEL_FONT);
		authorText = new DetailTextField();
		authorText.setVisible(false);
		authorText.setEditable(false);
		authorText.setBorder(null);
		publishLabel = new JLabel("Verlag: ");
		publishLabel.setVisible(false);
		publishLabel.setFont(DETAIL_LABEL_FONT);
		publishText = new DetailTextField();
		publishText.setVisible(false);
		publishText.setEditable(false);
		publishText.setBorder(null);
		conditionLabel = new JLabel("Zustand: ");
		conditionLabel.setVisible(false);
		conditionLabel.setFont(DETAIL_LABEL_FONT);
		conditionText = new DetailTextField();
		conditionText.setVisible(false);
		conditionText.setEditable(false);
		conditionText.setBorder(null);
		
		filler = new JLabel();
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		detailPanel.add(authorLabel, c);
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		detailPanel.add(authorText, c);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		detailPanel.add(publishLabel, c);
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		detailPanel.add(publishText, c);
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0;
		detailPanel.add(conditionLabel, c);
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		detailPanel.add(conditionText, c);
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		detailPanel.add(filler, c);

		detailPanel.add(new JLabel());
	}

	public TabBookModel getModel() {
		return model;
	}

	public void update(Observable o, Object arg) {
		boolean isBookActive = model.getActiveBook() != null;
		loanPanel.setVisible(isBookActive && model.getActiveBook().getCondition() != Book.Condition.WASTE);
		titleText.setText(isBookActive ? NO_BOOK_ACTIVE_TEXT : "");
		authorText.setVisible(isBookActive);
		authorLabel.setVisible(isBookActive);
		publishLabel.setVisible(isBookActive);
		publishText.setVisible(isBookActive);
		conditionText.setVisible(isBookActive);
		conditionLabel.setVisible(isBookActive);
		if (!isBookActive)
			return;

		conditionText.setText(model.getActiveBook().getConditionString());
		titleText.setText(model.getActiveBook().getTitle().getName());
		authorText.setText(model.getActiveBook().getTitle().getAuthor());
		publishText.setText(model.getActiveBook().getTitle().getPublisher());

		List<Loan> loanList = controller.library.getLoansPerBook(model.getActiveBook());
		Loan tmpLoan = controller.library.getRecentLoanOf(model.getActiveBook());

		String lastCustomer = "<html><b>Ausleihezustand</b>: ";
		if (tmpLoan == null || tmpLoan.getCustomer() == null) {
			lastCustomer += "Verfügbar. Buch wurde noch nie ausgeliehen.<br />";
		} else {
			lastCustomer += (tmpLoan.isLent() ? "Ausgeliehen" : "Verfügbar") + "<br />";
			lastCustomer += "<b>Letzter Ausleiher</b>: " + tmpLoan.getCustomer().getFullName() + "<br />";

			if (!tmpLoan.isLent()) {
				lastCustomer += "<b>Letzte Ausleihedauer</b>: " + tmpLoan.getDaysOfLoanDuration() + (tmpLoan.getDaysOfLoanDuration() == 1 ? " Tag" : " Tage") + "<br />";
			} else {
				lastCustomer += "<b>Ausleihedauer</b>: " + (tmpLoan.getDaysOfLoanDurationTillToday() == 0 ? "heute ausgeliehen" : tmpLoan.getDaysOfLoanDurationTillToday()) + "";
				if (tmpLoan.getDaysOfLoanDurationTillToday() > 14)
					lastCustomer += " (<font color=red>überfällig</font> seit " + (tmpLoan.getDaysOfLoanDurationTillToday() - 14 == 1 ? "heute" : (tmpLoan.getDaysOfLoanDurationTillToday() - 14) + " Tagen") +")";
				lastCustomer += "<br />";
			}
			lastCustomer += "Bisher total " + loanList.size() + " Mal ausgeliehen<br />";
		}
		lastLoan.setText (lastCustomer);
	}

	private final class ClipboardListener extends MouseAdapter {
		private final String title;
		private final JTextComponent what;

		public ClipboardListener(String title, JTextComponent what) {
			this.title = title;
			this.what = what;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			JPopupMenu pop = new JPopupMenu();
			JMenuItem menuItem = new JMenuItem(title);
			menuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Clipboard clipboard;
					clipboard = Toolkit.getDefaultToolkit()
							.getSystemClipboard();
					StringSelection newcontents = new StringSelection(what
							.getText());
					clipboard.setContents(newcontents, controller.main_model);
				}
			});
			pop.add(menuItem);
			pop.show(titleText, e.getX(), e.getY());
			pop.setVisible(true);
		}
	}

	private class DetailTextField extends JTextField {
		private static final long serialVersionUID = 1674245771658079673L;

		public DetailTextField() {
			super();
			setEditable(false);
			setBorder(null);
			setFont(new Font("SansSerif", Font.PLAIN, 16));
		}
	}
}
