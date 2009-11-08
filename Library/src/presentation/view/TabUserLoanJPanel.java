package presentation.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import presentation.model.ModelController;
import util.TextUtils;
import domain.Loan;

public class TabUserLoanJPanel extends JPanel implements Observer {
	
	// Duplicated Code, sorry :-)
	public class TabUserLoanListCellRenderer implements ListCellRenderer {
		private final Color SHINY_BLUE = new Color(0xADD8E6);
		private static final String IMG_SYMBOL = "img/book64x64.png";
		private static final String TITLE_FORMAT = "<html><p style='font-size:14pt; padding-left: 1.25cm; text-indent: -1cm;'>";

		public Component getListCellRendererComponent(JList list, Object value,
				int cellIndex, boolean isSelected, boolean cellHasFocus) {

			int index = cellIndex;
			if (index == -1) {
				if (list.isSelectionEmpty())
					return new JPanel();
				index = list.getSelectedIndex();
			}

			return getRenderedCell(value, isSelected);
		}

		private JPanel getRenderedCell(Object value, boolean isSelected) {
			JPanel cell = new JPanel();
			cell.setLayout(new BorderLayout());
			Loan active;
			try {
				active = (Loan) value;
			} catch (ClassCastException e) {
				return cell;
			}

			cell.setBackground(Color.WHITE);
			if (isSelected)
				cell.setBackground(SHINY_BLUE);

			JLabel la = new JLabel(getFormattedTitle(active));
			la.setIcon(new ImageIcon(IMG_SYMBOL));
			la.setFont(new Font(null, Font.PLAIN, 14));
			cell.add(la, BorderLayout.WEST);

			return cell;
		}

		private String getFormattedTitle(Loan selected) {
			String text = TITLE_FORMAT
					+ fat(TextUtils.cutText(selected.getBook().getTitle()
							.getName(), loanList.getWidth() - 80, TITLE_FORMAT + "<b>"));
			text += "<br />Ausleihdauer: ";
			text += selected.getDaysOfLoanDurationTillToday() + "Tag(e)<br />";
			return text;
		}

		private String fat(String toBeFormattedFatText) {
			return "<b>" + toBeFormattedFatText + "</b>";
		}
	}

	private static final long serialVersionUID = 1311911720974043461L;
	private JList loanList;
	private JScrollPane loanScroll;
	private DefaultListModel loanModel;
	private final ModelController controller;

	public TabUserLoanJPanel(ModelController controller) {
		this.controller = controller;
		setLayout(new GridBagLayout());
		setBorder(new TitledBorder("Ausleihen"));

		loanModel = new DefaultListModel();
		loanList = new JList(loanModel);
		loanList.setCellRenderer(new TabUserLoanListCellRenderer());
		loanList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		loanScroll = new JScrollPane(loanList);
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(loanScroll, c);
	}

	public void update(Observable o, Object arg) {
		loanModel.removeAllElements();
		for (Loan l : controller.library
				.getCustomerActiveLoans(controller.activeuser_model
						.getCustomer()))
			loanModel.addElement(l);
	}
}
