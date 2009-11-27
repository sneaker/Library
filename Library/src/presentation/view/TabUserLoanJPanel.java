package presentation.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import presentation.control.ListItemMouseListener;
import presentation.model.ControllerFacade;
import domain.Customer;
import domain.Loan;

public class TabUserLoanJPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1311911720974043461L;
	private JList loanList;
	private JScrollPane loanScroll;
	private final ControllerFacade controller;
	private List<Loan> lastLoans;

	public TabUserLoanJPanel(final ControllerFacade controller) {
		this.controller = controller;
		controller.activeuser_model.addObserver(this);
		controller.library.addObserver(this);
		initGui();
	}

	private void initGui() {
		setLayout(new GridBagLayout());
		setBorder(new TitledBorder("Ausleihen"));
		loanList = new JList(controller.loanModel);
		loanList.setCellRenderer(new SearchResultCellRenderer(controller));
		loanList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		addListSelectionListener();
		addMouseListener();
		addFocusListener();
		loanScroll = new JScrollPane(loanList);
		GridBagConstraints c = setGridLayout();
		add(loanScroll, c);
	}

	private GridBagConstraints setGridLayout() {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		return c;
	}

	private void addListSelectionListener() {
		loanList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (loanList.getSelectedIndex() >= 0) {
					controller.usertab_model
							.setActiveLoan((Loan) (controller.loanModel
									.getElementAt(loanList.getSelectedIndex())));
				} else {
					controller.usertab_model.setActiveLoan(null);
				}
				getParent().validate();
			}
		});
	}

	private void addFocusListener() {
		loanList.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
			}

			public void focusGained(FocusEvent e) {
				if (loanList.getSelectedIndex() == -1
						&& loanList.getModel().getSize() > 0)
					loanList.setSelectedIndex(0);
			}
		});
	}

	private void addMouseListener() {
		loanList.addMouseListener(new ListItemMouseListener(controller) {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.loanClicked(e, loanList.locationToIndex(e.getPoint()));
				getParent().validate();
			}
		});
	}

	private void updateCustomerLoans(Customer customer) {
		List<Loan> newLoans = controller.library
				.getCustomerActiveLoans(customer);
		if (lastLoans == null || !lastLoans.equals(newLoans)) {
			controller.loanModel.clear();
			for (Loan l : newLoans)
				controller.loanModel.addElement(l);
		}
		lastLoans = newLoans;
	}

	public void update(Observable o, Object arg) {
		Customer customer = controller.getActiveCustomer();
		setVisible(customer != null);
		updateCustomerLoans(customer);
	}
}
