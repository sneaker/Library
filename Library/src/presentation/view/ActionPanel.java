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

public class ActionPanel extends JPanel implements Observer, ActionListener {

	private static final long serialVersionUID = 1L;
	private static final String ACTION_PANEL_TITLE = "Aktionen";
	private JPanel buttonpanel = new JPanel();
	private JButton search = new JButton("Neue Suche");
	private ActionPanelModel model;

	public ActionPanel(ActionPanelModel action_panel_model) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(new TitledBorder(ACTION_PANEL_TITLE));
		
		model = action_panel_model;
		model.addObserver(this);
		
		buttonpanel.add(search);
		search.addActionListener(this);
		JScrollPane pane = new JScrollPane(buttonpanel);
		
		this.add(pane);
	}

	public void update(Observable o, Object arg) {
		
	}
	
	public void actionPerformed(ActionEvent e) {
        if(e.getSource() == search){
            model.changetoSearch();
        }
    } 
}
