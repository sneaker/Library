package presentation.view;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import presentation.model.ActionPanelModel;

public class TabBookPanel extends TabAbstractPanel {

	private static final long serialVersionUID = 1L;
	private TabBookPanelModel model;

	public TabBookPanel(ActionPanelModel action_panel_model) {
		super(action_panel_model);
		setLayout(new BorderLayout());
		model = new TabBookPanelModel();
		initContentPane();
	}

	private void initContentPane() {
		// TODO:
		add(new JLabel("jflkskfklsfl"), BorderLayout.WEST);
	}

	public void update(Observable o, Object arg) {
		// TODO:
		System.out.println("jfklsdlf");
		add(new JLabel("Hurra, ein neues Buch: "), BorderLayout.EAST);
	}

	public TabBookPanelModel getModel() {
		return model;
	}
}
