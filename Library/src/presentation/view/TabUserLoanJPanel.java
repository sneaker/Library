package presentation.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import presentation.model.ModelController;
import domain.Customer;
import domain.Loan;

public class TabUserLoanJPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1311911720974043461L;
	private JList loanList;
	private JScrollPane loanScroll;
	private DefaultListModel loanModel;
//	private Customer lastCustomer = null;
	private final ModelController controller;

	public TabUserLoanJPanel(final ModelController controller) {
		this.controller = controller;
		setLayout(new GridBagLayout());
		setBorder(new TitledBorder("Ausleihen"));

		loanModel = new DefaultListModel();
		loanList = new JList(loanModel);
		loanList.setCellRenderer(new SearchResultCellRenderer(controller));
		loanList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		loanList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (loanList.getSelectedIndex() >= 0)
					controller.usertab_model.setLoanSelected(true);
			}
		});
		loanList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = loanList.locationToIndex(e.getPoint());
				if (index < 0)
					return;
				Object element = loanModel.getElementAt(index);
				if (!(element instanceof Loan))
					return;
				controller.booktab_model.setActiveBook(((Loan) element)
						.getBook());
			}
		});

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
		Customer customer = controller.activeuser_model.getCustomer();
		setVisible(customer != null);
//		requestFocusOnCustomerChange(customer);
		updateCustomerLoans(customer);
//		lastCustomer = customer;
	}

	private void updateCustomerLoans(Customer customer) {
		loanModel.removeAllElements();
		for (Loan l : controller.library.getCustomerActiveLoans(customer))
			loanModel.addElement(l);
	}

//	private void requestFocusOnCustomerChange(Customer customer) {
//		loanList.setFocusCycleRoot(true);
//		// TODO: Doesn't work [Martin]
//		if (hasCustomerChanged(customer))
//			loanScroll.requestFocusInWindow();
//	}
//
//	private boolean hasCustomerChanged(Customer customer) {
//		return customer != null && !customer.equals(lastCustomer);
//	}
}
