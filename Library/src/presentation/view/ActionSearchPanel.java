package presentation.view;

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

public class ActionSearchPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private static final String ACTION_PANEL_TITLE = "Aktionen";
	private ActionPanelModel model;
	private JPanel button_panel = new JPanel();
	private JScrollPane pane;
	private JButton search;
	private JButton newuser;

	public ActionSearchPanel(ActionPanelModel actionPanelModel) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new TitledBorder(ACTION_PANEL_TITLE));
		model = actionPanelModel;
		model.addObserver(this);
		
		button_panel.setLayout(new BoxLayout(button_panel, BoxLayout.PAGE_AXIS));
		InitActionButtons();
		pane = new JScrollPane(button_panel);
		add(pane);
	}

	private void InitActionButtons() {
		initSearchButton();
		initNewUserButton();
	}

	private void initSearchButton() {
		search = new JButton("Neue Suche");
		button_panel.add(search);
		search.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				model.changetoSearch();
			}
		});
	}

	private void initNewUserButton() {
		newuser = new JButton("Neuer Benutzer");
		button_panel.add(newuser);
		newuser.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				model.createNewUser();
			}
		});
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO On update make the buttons enabled
	}
}
