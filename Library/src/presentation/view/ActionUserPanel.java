package presentation.view;

import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import presentation.model.ActionPanelModel;

public class ActionUserPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private static final String ACTION_PANEL_TITLE = "Aktionen";
	private ActionPanelModel model;
	private JPanel button_panel;
	private JScrollPane pane;
	private JButton adduser;
	private JButton edituser;
	
	public ActionUserPanel(ActionPanelModel actionPanelModel) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new TitledBorder(ACTION_PANEL_TITLE));
		model = actionPanelModel;
		model.addObserver(this);
		
		button_panel = new JPanel();
		button_panel.setLayout(new BoxLayout(button_panel, BoxLayout.PAGE_AXIS));
		initActionButton();
		pane = new JScrollPane(button_panel);
		add(pane);
	}

	private void initActionButton() {
		initEditButton();
		initAddUserButton();
	}

	private void initAddUserButton() {
		//TODO: Zeilenumbruch ??
		adduser = new JButton("Neuen Benutzer Erfassen");
		adduser.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				model.createUser();
			}
		});
		button_panel.add(adduser);
	}

	private void initEditButton() {
		edituser = new JButton("Personalien Editieren");
		edituser.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				model.editUserSettings();
			}
		});
		button_panel.add(edituser);
	}

	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
	}

}
