package presentation.model;

import java.util.Observable;

public class UserDetailPanelModel extends Observable {

	public MainWindowModel model;
	
	public UserDetailPanelModel(MainWindowModel mainmodel) {
		model = mainmodel;
	}
	
	public MainWindowModel getMainWindowModel() {
		return model;
	}

}
