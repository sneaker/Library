package presentation.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import presentation.view.LibTabPaneModel;

import domain.Library;

public class ActionPanelModel extends Observable implements ListModel, Observer {

	// TODO: cell renderer
	List<String> available_actions = new ArrayList<String>();
	private Library library;
	private LibTabPaneModel tabPane;
	
	//TODO: Needs application as argument to do the actions
	public ActionPanelModel(Library library, LibTabPaneModel tabPane) {
		this.library = library;
		this.tabPane = tabPane;
	}
	
	public void addListDataListener(ListDataListener l) {
	}

	public Object getElementAt(int index) {
		return available_actions.get(index);
	}

	public int getSize() {
		return available_actions.size();
	}

	public void removeListDataListener(ListDataListener l) {
		
	}

	public void update(Observable o, Object arg) {
		//TODO: Does not update the list on the view
		setChanged();
		notifyObservers();
	}

	public void changetoSearch() {
		tabPane.setActiveTab(MainWindowModel.SEARCH_TAB);
		setChanged();
		notifyObservers();
	}
}
