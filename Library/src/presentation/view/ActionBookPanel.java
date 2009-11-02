package presentation.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import presentation.model.ActionPanelModel;
import presentation.model.ModelController;

public class ActionBookPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private static final String ACTION_PANEL_TITLE = "Aktionen";
	private ActionPanelModel model; 
	private JPanel button_panel;
	private JScrollPane pane;
	private JButton lend;
	private JButton defekt;
	private ModelController controller;
	
	public ActionBookPanel(ModelController controller) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new TitledBorder(ACTION_PANEL_TITLE));
		
		this.controller = controller;
		model = controller.action_model;
		model.addObserver(this);
		
		button_panel = new JPanel();
		button_panel.setLayout(new BoxLayout(button_panel, BoxLayout.PAGE_AXIS));
		initActionButtons();
		pane = new JScrollPane(button_panel);
		add(pane);
	}

	private void initActionButtons() {
		initLendButton();
		initDefektButton();
	}

	private void initDefektButton() {
		defekt = new JButton("Als defekt markieren");
		defekt.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				model.markDefekt();
			}
		});
		button_panel.add(defekt);
		button_panel.add(Box.createRigidArea(new Dimension(0,5)));
	}

	private void initLendButton() {
		lend = new JButton("Buch ausleihen");
		lend.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				model.lendBook();
			}
		});
		button_panel.add(lend);
		button_panel.add(Box.createRigidArea(new Dimension(0,5)));
	}

	public void update(Observable o, Object arg) {
		
	}
}
