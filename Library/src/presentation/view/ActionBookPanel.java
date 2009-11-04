package presentation.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.TitledBorder;

import presentation.model.ActionPanelModel;
import presentation.model.ModelController;

public class ActionBookPanel extends JPanel implements Observer {

	private static final Alignment LEADING = GroupLayout.Alignment.LEADING;
	private static final short MAX = Short.MAX_VALUE;
	private static final long serialVersionUID = 1L;
	private static final String ACTION_PANEL_TITLE = "Aktionen";
	private ActionPanelModel model;
	private JPanel button_panel;
	private JScrollPane pane;
	private JButton lend;
	private JButton defekt;
	private final ModelController controller;
	private JButton ret;
	private JButton create;

	public ActionBookPanel(ModelController controller) {
		this.controller = controller;
		setLayout(new BorderLayout());
		setBorder(new TitledBorder(ACTION_PANEL_TITLE));

		model = controller.action_model;
		model.addObserver(this);

		button_panel = new JPanel();
		putActionButtons();

		pane = new JScrollPane(button_panel);
		add(pane, BorderLayout.NORTH);
		update(null, null);
	}

	private void putActionButtons() {
		GroupLayout thisLayout = new GroupLayout((JComponent) button_panel);
		button_panel.setLayout(thisLayout);
		initActionButtons();
		int PS = GroupLayout.PREFERRED_SIZE;
		thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addComponent(lend, PS, PS, PS) //
				// .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(defekt, PS, PS, PS) //
				.addComponent(ret, PS, PS, PS) //
				.addComponent(create, PS, PS, PS));
		thisLayout.setHorizontalGroup(thisLayout.createParallelGroup()
				.addComponent(lend, LEADING, 0, 180, MAX) // 
				.addComponent(defekt, LEADING, 0, 180, MAX) //
				.addComponent(ret, LEADING, 0, 180, MAX) //
				.addComponent(create, LEADING, 0, 180, MAX));
	}

	private void initActionButtons() {
		initLendButton();
		initDefektButton();
		initReturnButton();
		initCreateButton();
	}

	private void initDefektButton() {
		defekt = new JButton("Als defekt markieren");
		defekt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.markDefekt();
			}
		});
	}

	private void initReturnButton() {
		ret = new JButton("Buch zurückgeben");
		ret.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.returnBook();
			}
		});
	}

	private void initLendButton() {
		lend = new JButton("Buch ausleihen");
		lend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.lendBook();
			}
		});
	}

	private void initCreateButton() {
		create = new JButton("Neues Buch erstellen");
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.creaNewBook();
			}
		});
	}

	public void update(Observable o, Object arg) {
		lend.setVisible (controller.booktab_model.isActiveBookLendable());
		ret.setVisible(controller.booktab_model.isActiveBookReturnable());
		defekt.setVisible(controller.booktab_model.isActiveBookMarkableAsWaste());
	}
}
