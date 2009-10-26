package presentation.view;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import presentation.model.MainWindowModel;
import presentation.model.UserDetailPanelModel;

public class UserDetailPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private JPanel actionpanel;
	private UserDetailPanelModel model;
	
	public UserDetailPanel(MainWindowModel model2) {
		setLayout(new BorderLayout());
		model = new UserDetailPanelModel(model2);
		model.addObserver(this);

		initContentPane();
		initActionPane();
	}

	private void initActionPane() {
		actionpanel = new ActionPanel(model);
		add(actionpanel, BorderLayout.EAST);
	}

	private void initContentPane() {
		
	}

	public void update(Observable o, Object arg) {
		
	}
}
