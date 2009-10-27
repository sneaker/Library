package presentation.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import presentation.model.ActionPanelModel;
import presentation.model.LibraryModel;

public class ActionPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private static final String ACTION_PANEL_TITLE = "Aktionen";

	public ActionPanel(LibraryModel basemodel) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(new TitledBorder(ACTION_PANEL_TITLE));

		JList list = new JList();
		list.setModel(new ActionPanelModel(basemodel));
		JScrollPane pane = new JScrollPane(list);
		
		this.add(pane);
	}

	public void update(Observable o, Object arg) {
		
	}
}
