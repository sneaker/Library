package presentation.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import presentation.model.ModelController;
import domain.Loan;

public class TabUserLoanJPanel extends JPanel implements Observer {
	
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
		loanList.setCellRenderer(new SearchResultCellRenderer(controller));
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
