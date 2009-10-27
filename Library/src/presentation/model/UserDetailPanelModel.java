package presentation.model;

import java.util.Observable;

public class UserDetailPanelModel extends Observable implements LibraryModel {

	public MainWindowModel model;
	
	public UserDetailPanelModel(MainWindowModel mainmodel) {
		model = mainmodel;
	}
	
	public MainWindowModel getMainWindowModel() {
		return model;
	}

}
