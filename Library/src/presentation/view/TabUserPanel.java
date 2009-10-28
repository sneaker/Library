package presentation.view;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import domain.Library;

import presentation.model.ActionPanelModel;
import presentation.model.LibTabPaneModel;
import presentation.model.TabBookPanelModel;

public class TabUserPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private TabBookPanelModel model;
	public TabUserPanel(LibTabPaneModel tabModel, Library library, ActionPanelModel action_panel_model) {
		setLayout(new BorderLayout());
		model = new TabBookPanelModel();
		model.addObserver(this);
		
		initContentPane();
		add(new ActionUserPanel(action_panel_model), BorderLayout.EAST);
	}

	private void initContentPane() {
		
	}

	public void update(Observable o, Object arg) {
		
	}
}
