package presentation.view;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import presentation.model.BookDetailPanelModel;
import presentation.model.MainWindowModel;

public class TabBookPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 8944213438554698313L;
	private BookDetailPanelModel model;
	private JPanel actionpanel;
	
	public TabBookPanel(MainWindowModel model2) {
		setLayout(new BorderLayout());
		model = new BookDetailPanelModel(model2);
		model.addObserver(this);

		initContentPane();
		initActionPane();
	}

	private void initContentPane() {
		
	}

	private void initActionPane() {
		actionpanel = new ActionPanel(model);
		add(actionpanel, BorderLayout.EAST);
	}

	public void update(Observable o, Object arg) {
		
	}
}
