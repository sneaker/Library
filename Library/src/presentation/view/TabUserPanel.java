package presentation.view;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import presentation.model.ModelController;
import presentation.model.TabBookPanelModel;

public class TabUserPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private TabBookPanelModel model;
	private ModelController controller;
	private ActiveUserPanel action_user_panel;

	public TabUserPanel(ModelController controller) {
		setLayout(new BorderLayout());
		model = new TabBookPanelModel();
		model.addObserver(this);

		this.controller = controller;
		controller.booktab_model = model;

		initContentPane();
		action_user_panel = new ActiveUserPanel(controller);
		add(action_user_panel, BorderLayout.EAST);
	}

	private void initContentPane() {

	}

	@Override
	public void update(Observable o, Object arg) {
	
	}
}
